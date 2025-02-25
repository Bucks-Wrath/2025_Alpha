package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.algae.ReverseAlgaeIntake;
import frc.robot.commands.algae.RunAlgaeIntake;
import frc.robot.commands.algae.StopAlgaeIntake;
import frc.robot.commands.auto.AutoHome;
import frc.robot.commands.auto.L4AutoScore;
import frc.robot.commands.auto.L3AutoScore;
import frc.robot.commands.auto.L3SetHeight;
import frc.robot.commands.auto.L4SetHeight;
import frc.robot.commands.auto.SetAqua;
import frc.robot.commands.CANdle.WatchClock;
import frc.robot.commands.climber.JoystickClimber;
import frc.robot.commands.climber.SetClimberPosition;
import frc.robot.commands.coral.ReverseCoralIntake;
import frc.robot.commands.coral.RunCoralIntake;
import frc.robot.commands.coral.ShootCoralIntake;
import frc.robot.commands.coral.ShootCoralIntakeTrough;
import frc.robot.commands.coral.StopCoralIntake;
import frc.robot.commands.elevator.JoystickElevator;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.commands.ramp.JoystickRamp;
import frc.robot.commands.ramp.SetRampPosition;
import frc.robot.commands.wrist.JoystickWrist;
import frc.robot.commands.wrist.SetWristPosition;
import frc.robot.dashboard.AutoScoreLeftDashboard;
import frc.robot.dashboard.AutoScoreRightDashboard;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralIntake;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Ramp;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.AlgaeIntake;
import frc.robot.subsystems.CANdleSubsystem;
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
            .withDeadband(MaxSpeed * 0.01).withRotationalDeadband(MaxAngularRate * 0.03) // Add a 10% deadband
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
    public static CANdleSubsystem candleSubsystem = new CANdleSubsystem();


    /* Path follower */
    private SendableChooser<Command> autoChooser = new SendableChooser<>();

    public RobotContainer() {
        registerNamedCommands();

        ShuffleboardTab autoTab = Shuffleboard.getTab("Auto settings");
        autoChooser.addOption("Drive Three Feet", new PathPlannerAuto("Drive Three Feet"));
        autoChooser.addOption("Turn 90 Degrees", new PathPlannerAuto("Turn 90 Degrees"));
        autoChooser.addOption("Processor Three L4", new PathPlannerAuto("Processor Three L4"));
        autoChooser.addOption("Non-Processor Three L4", new PathPlannerAuto("Non-Processor Three L4"));
        autoChooser.addOption("Non-Processor Center", new PathPlannerAuto("Non-Processor Center"));



        autoTab.add("Mode", autoChooser);
        candleSubsystem.setDefaultCommand(new WatchClock());
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

        AutoScoreLeftDashboard.AddDashboard();
        AutoScoreRightDashboard.AddDashboard();
    }

    private void configureBindings() { 
        // Driver Buttons
        driverController.rightTrigger().onTrue(new RunCoralIntake());
        driverController.rightBumper().onTrue(new ShootCoralIntake().withTimeout(0.375).andThen(new SetWristPosition(0).alongWith(new ShootCoralIntake()).withTimeout(0.375))); 
        driverController.x().onTrue(new SetElevatorPosition(6).alongWith(new SetWristPosition(-9)).andThen(new ShootCoralIntakeTrough().withTimeout(1.5)));
        driverController.leftTrigger().whileTrue(new RunAlgaeIntake().alongWith(new SetWristPosition(-35.2).alongWith(new SetAqua())));
        driverController.leftTrigger().onFalse(new StopAlgaeIntake().alongWith(new SetWristPosition(0)));
        driverController.leftBumper().whileTrue(new SetWristPosition(-10.3));
        driverController.leftBumper().onFalse(new ReverseAlgaeIntake().withTimeout(0.5).andThen(new SetWristPosition(0)));
        driverController.a().whileTrue(new AutoScoreLeft(drivetrain, visionDrive));
        driverController.b().whileTrue(new AutoScoreRight(drivetrain, visionDrive));
        driverController.start().onTrue(new SetClimberPosition(215).alongWith(new StopAlgaeIntake()).withTimeout(1.5).andThen(new SetRampPosition(1.63)));

        // Operator Buttons
        operatorController.a().onTrue(new SetWristPosition(0).alongWith(new SetElevatorPosition(0).andThen(new RunCoralIntake())));
        operatorController.b().onTrue(new SetElevatorPosition(12.2).alongWith(new SetWristPosition(-9))); // 11.2
        operatorController.x().onTrue(new SetElevatorPosition(27.3).alongWith(new SetWristPosition(-9))); // 26.3
        operatorController.y().onTrue(new SetElevatorPosition(50.5).alongWith(new StopCoralIntake()).withTimeout(0.45).andThen(new SetWristPosition(-16.4)));  //49.5 and -15.4
        operatorController.rightTrigger().onTrue(new SetElevatorPosition(5).alongWith(new SetWristPosition(-9)));
        operatorController.leftBumper().onTrue(new SetRampPosition(0));
        operatorController.rightBumper().onTrue(new SetRampPosition(1.63));
        operatorController.back().whileTrue(new SetElevatorPosition(32.7).andThen(new RunAlgaeIntake().alongWith(new SetWristPosition(-21.4))));
        operatorController.back().onFalse(new SetWristPosition(0).deadlineFor(new SetAqua()));
        operatorController.start().whileTrue(new SetElevatorPosition(15.6).andThen(new RunAlgaeIntake().alongWith(new SetWristPosition(-15.8))));
        operatorController.start().onFalse(new SetWristPosition(0).deadlineFor(new SetAqua()));
        
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.

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
        return autoChooser.getSelected();
    }

    public void registerNamedCommands() {
        /* Command registration for PathPlanner */     
        NamedCommands.registerCommand("AutoScoreLeft", new AutoScoreLeft(drivetrain, visionDrive).withTimeout(1));
        NamedCommands.registerCommand("AutoScoreRight", new AutoScoreRight(drivetrain, visionDrive).withTimeout(1));
        NamedCommands.registerCommand("L3AutoScore", new L3AutoScore());
        //NamedCommands.registerCommand("L2", getAutonomousCommand());
        NamedCommands.registerCommand("L4AutoScore", new L4AutoScore());
        NamedCommands.registerCommand("AutoHome", new AutoHome());
        NamedCommands.registerCommand("L3SetHeight", new L3SetHeight().withTimeout(2));
        NamedCommands.registerCommand("L4SetHeight", new L4SetHeight().withTimeout(2));
        NamedCommands.registerCommand("RunCoralIntake", new RunCoralIntake());
        NamedCommands.registerCommand("StopCoralIntake", new StopCoralIntake());
    }
}
