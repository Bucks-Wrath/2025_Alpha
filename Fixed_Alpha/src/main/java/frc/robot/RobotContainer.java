package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.algae.ReverseAlgaeIntake;
import frc.robot.commands.algae.RunAlgaeIntake;
import frc.robot.commands.algae.StopAlgaeIntake;
import frc.robot.commands.climber.JoystickClimber;
import frc.robot.commands.climber.SetClimberPosition;
import frc.robot.commands.coral.ReverseCoralIntake;
import frc.robot.commands.coral.RunCoralIntake;
import frc.robot.commands.coral.ShootCoralIntake;
import frc.robot.commands.coral.StopCoralIntake;
import frc.robot.commands.elevator.JoystickElevator;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.commands.ramp.JoystickRamp;
import frc.robot.commands.ramp.SetRampPosition;
import frc.robot.commands.wrist.JoystickWrist;
import frc.robot.commands.wrist.SetWristPosition;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralIntake;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Ramp;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.AlgaeIntake;
import frc.robot.subsystems.Climber;
import frc.robot.commands.swerve.AutoScoreLeft;
import frc.robot.commands.swerve.AutoScoreRight;
import frc.robot.commands.swerve.TeleopDrive;
import frc.robot.subsystems.LeftLimelight;
import frc.robot.subsystems.RightLimelight;

public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

    private final SwerveRequest.RobotCentric visionDrive = new SwerveRequest.RobotCentric()
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    //private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    //private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandXboxController driverController = new CommandXboxController(0);
    private final CommandXboxController operatorController = new CommandXboxController(1);


    public static CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
    public static LeftLimelight leftLimelight = new LeftLimelight();
    public static RightLimelight rightLimelight = new RightLimelight();

    /* Subsystems */
    public static AlgaeIntake algaeIntake = new AlgaeIntake(); 
    public static CoralIntake coralIntake = new CoralIntake();
    public static Wrist wrist = new Wrist();
    public static Elevator elevator = new Elevator();
    public static Ramp ramp = new Ramp();
    public static Climber climber = new Climber();

    public RobotContainer() {
        coralIntake.setDefaultCommand(new StopCoralIntake());
        algaeIntake.setDefaultCommand(new StopAlgaeIntake());
        drivetrain.setDefaultCommand(
            new TeleopDrive(
                drivetrain, 
                drive,
                () -> -driverController.getRawAxis(translationAxis), 
                () -> -driverController.getRawAxis(strafeAxis), 
                () -> -driverController.getRawAxis(rotationAxis) 
            )
        );
        // Only Used For Testing
        // wrist.setDefaultCommand(new JoystickWrist());
        // elevator.setDefaultCommand(new JoystickElevator());
        // ramp.setDefaultCommand(new JoystickRamp());
        climber.setDefaultCommand(new JoystickClimber());
        // Always Last
        configureBindings();
    }

    private void configureBindings() { 
        // Driver Buttons
        driverController.rightTrigger().onTrue(new RunCoralIntake());
        driverController.rightBumper().whileTrue(new ShootCoralIntake().withTimeout(0.2).andThen(new SetWristPosition(0).alongWith(new ShootCoralIntake())));
        driverController.leftTrigger().whileTrue(new RunAlgaeIntake().alongWith(new SetWristPosition(-35.2)));
        driverController.leftTrigger().onFalse(new StopAlgaeIntake().alongWith(new SetWristPosition(0)));
        driverController.leftBumper().whileTrue(new SetWristPosition(-10.3));
        driverController.leftBumper().onFalse(new ReverseAlgaeIntake().withTimeout(0.5).andThen(new SetWristPosition(0)));
        driverController.a().whileTrue(new AutoScoreLeft(drivetrain, visionDrive));
        driverController.b().whileTrue(new AutoScoreRight(drivetrain, visionDrive));


        // Operator Buttons
        operatorController.a().onTrue(new SetWristPosition(0).alongWith(new StopCoralIntake().withTimeout(0.1).andThen(new SetElevatorPosition(0))));
        operatorController.b().onTrue(new SetElevatorPosition(10.7).alongWith(new SetWristPosition(-9)));
        operatorController.x().onTrue(new SetElevatorPosition(26.3).alongWith(new SetWristPosition(-9)));
        operatorController.y().onTrue(new SetElevatorPosition(48.5).alongWith(new StopCoralIntake()).withTimeout(0.45).andThen(new SetWristPosition(-15.4)));
        operatorController.leftBumper().onTrue(new SetRampPosition(0));
        operatorController.rightBumper().onTrue(new SetRampPosition(50));
        operatorController.back().whileTrue(new SetElevatorPosition(32.7).andThen(new RunAlgaeIntake().alongWith(new SetWristPosition(-21.4))));
        operatorController.back().onFalse(new SetWristPosition(0));
        operatorController.start().whileTrue(new SetElevatorPosition(15.6).andThen(new RunAlgaeIntake().alongWith(new SetWristPosition(-15.8))));
        operatorController.start().onFalse(new SetWristPosition(0));
        
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.

        // this is working but we dont want to use it
        //drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
        //    drivetrain.applyRequest(() ->
        //        drive.withVelocityX(-driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
        //            .withVelocityY(-driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
        //           .withRotationalRate(-driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
        //    )
        //);

        //driverController.a().whileTrue(drivetrain.applyRequest(() -> brake));
        //driverController.b().whileTrue(drivetrain.applyRequest(() ->
        //    point.withModuleDirection(new Rotation2d(-driverController.getLeftY(), -driverController.getLeftX()))
        //));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        //driverController.back().and(driverController.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        //driverController.back().and(driverController.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        //driverController.start().and(driverController.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        //driverController.start().and(driverController.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // reset the field-centric heading on left bumper press
        driverController.back().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    /* Sets Joystick Deadband */
    public static double stickDeadband(double value, double deadband, double center) {
        return (value < (center + deadband) && value > (center - deadband)) ? center : value;
    }

    /* Passes Along Joystick Values for Elevator and Wrist */
    public double getOperatorLeftStickY() {
       return stickDeadband(this.operatorController.getRawAxis(1), 0.05, 0.0);
    }

    public double getOperatorRightStickY() {
        return stickDeadband(this.operatorController.getRawAxis(5), 0.05, 0.0);
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
