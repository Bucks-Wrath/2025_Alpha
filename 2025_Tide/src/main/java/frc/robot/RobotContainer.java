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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.algae.ShootAlgaeForProcessor;
import frc.robot.commands.algae.IntakeAlgaeForProcessor;
import frc.robot.commands.algae.PullAlgae;
import frc.robot.commands.algae.ShootAlgaeForBarge;
import frc.robot.commands.algae.HoldAlgae;
import frc.robot.commands.algae.IntakeAlgaeForBarge;
import frc.robot.commands.auto.AutoHome;
import frc.robot.commands.auto.DoNothing;
import frc.robot.commands.auto.L4AutoScore;
import frc.robot.commands.auto.L3AutoScore;
import frc.robot.commands.auto.L3SetHeight;
import frc.robot.commands.auto.L2AutoScore;
import frc.robot.commands.auto.L2SetHeight;
import frc.robot.commands.auto.L4SetHeight;
import frc.robot.commands.auto.TeleAutoScore;
import frc.robot.commands.CANdle.SetRainbow;
import frc.robot.commands.CANdle.WatchClock;
import frc.robot.commands.climber.JoystickClimber;
import frc.robot.commands.climber.SetClimberPosition;
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
import frc.robot.commands.swerve.DoTheCrawl;
import frc.robot.commands.swerve.TeleopDrive;
import frc.robot.commands.swerve.WatchTagFromLeft;
import frc.robot.commands.swerve.WatchTagFromRight;
import frc.robot.subsystems.LeftLimelight;
import frc.robot.subsystems.RightLimelight;

public class RobotContainer {
    private double MaxSpeed = Constants.Maximums.MaxSpeed;
    private double MaxAngularRate = Constants.Maximums.MaxAngularRate;

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.01).withRotationalDeadband(MaxAngularRate * 0.03) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

    private final SwerveRequest.FieldCentricFacingAngle crawlDrive = new SwerveRequest.FieldCentricFacingAngle()
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
        autoChooser.addOption("Blue Processor Three L4", new PathPlannerAuto("Processor Three L4 Blue"));
        //autoChooser.addOption("Processor Three Low", new PathPlannerAuto("Processor Three Low"));
        autoChooser.addOption("Blue Non-Processor Three L4", new PathPlannerAuto("Non-Processor Three L4 Blue"));
        //autoChooser.addOption("Non-Processor Three Low", new PathPlannerAuto("Non-Processor Three Low"));
        autoChooser.addOption("Non-Processor Center", new PathPlannerAuto("Non-Processor Center"));
        autoChooser.addOption("Processor Center", new PathPlannerAuto("Processor Center"));
        autoChooser.addOption("Red Processor Three L4", new PathPlannerAuto("Processor Three L4 Red"));
        autoChooser.addOption("Red Non-Processor Three L4", new PathPlannerAuto("Non-Processor Three L4 Red"));

        autoTab.add("Mode", autoChooser);
        candleSubsystem.setDefaultCommand(new WatchClock());
        coralIntake.setDefaultCommand(new StopCoralIntake());
        algaeIntake.setDefaultCommand(new HoldAlgae());
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

        // Turn on for comp
        // climber.setDefaultCommand(new JoystickClimber());
        // Always Last
        configureBindings();

        AutoScoreLeftDashboard.AddDashboard();
        AutoScoreRightDashboard.AddDashboard();
    }

    private void configureBindings() { 
        // Driver Buttons
        driverController.rightTrigger().onTrue(new RunCoralIntake());
        //driverController.rightBumper().onTrue(new ShootCoralIntake().withTimeout(0.375).andThen(new SetWristPosition(0).alongWith(new ShootCoralIntake()).withTimeout(0.375))); 
        driverController.rightBumper().onTrue(new TeleAutoScore().alongWith(new SetRainbow().withTimeout(0.1)));
        driverController.x().onTrue(new ShootCoralIntakeTrough().withTimeout(0.375));
        driverController.leftBumper().and(operatorController.leftTrigger().negate()).whileTrue(new IntakeAlgaeForProcessor().alongWith(new SetWristPosition(Constants.Algae.Intake.Processor.Floor.WristPosition)));
        driverController.leftBumper().and(operatorController.leftTrigger().negate()).onFalse(new HoldAlgae().alongWith(new SetWristPosition(0)));
        driverController.leftBumper().and(operatorController.leftTrigger()).whileTrue(new IntakeAlgaeForBarge().alongWith(new SetWristPosition(Constants.Algae.Intake.Barge.Floor.WristPosition)));
        driverController.leftBumper().and(operatorController.leftTrigger()).onFalse(new HoldAlgae().alongWith(new SetWristPosition(0)));
        driverController.leftTrigger().and(operatorController.leftTrigger().negate()).whileTrue(new SetWristPosition(Constants.Algae.Shoot.Processor.WristPosition));
        driverController.leftTrigger().and(operatorController.leftTrigger()).onTrue(new SetElevatorPosition(Constants.Algae.Shoot.Barge.ElevatorPosition).alongWith(new DoNothing().withTimeout(0.58).andThen(new ShootAlgaeForBarge().withTimeout(0.25))).andThen(new SetElevatorPosition(0).alongWith(new HoldAlgae())).raceWith(new DoTheCrawl(drivetrain, crawlDrive).withTimeout(1.3).alongWith(new SetRainbow().withTimeout(0.1))));
        driverController.leftTrigger().and(operatorController.leftTrigger().negate()).onFalse(new ShootAlgaeForProcessor().withTimeout(0.25).andThen(new SetWristPosition(0).alongWith(new SetRainbow().withTimeout(0.1))));
        driverController.a().whileTrue(new AutoScoreLeft(drivetrain, visionDrive).alongWith(new SetRainbow().withTimeout(0.1)).alongWith(new WatchTagFromRight(driverController,operatorController)));
        driverController.b().whileTrue(new AutoScoreRight(drivetrain, visionDrive).alongWith(new SetRainbow().withTimeout(0.1)).alongWith(new WatchTagFromLeft(driverController,operatorController)));
        driverController.start().onTrue(new SetClimberPosition(215).alongWith(new DoNothing()).withTimeout(1.75).andThen(new SetRampPosition(1.63)));

        // Operator Buttons
        operatorController.a().onTrue(new SetWristPosition(0).alongWith(new SetElevatorPosition(0)));
        operatorController.b().onTrue(new SetElevatorPosition(Constants.Coral.Shoot.L2.ElevatorPosition).alongWith(new SetWristPosition(Constants.Coral.Shoot.Default.WristPosition))); 
        operatorController.x().onTrue(new SetElevatorPosition(Constants.Coral.Shoot.L3.ElevatorPosition).alongWith(new SetWristPosition(Constants.Coral.Shoot.Default.WristPosition)));
        operatorController.y().and(operatorController.leftTrigger().negate()).onTrue(new SetElevatorPosition(Constants.Coral.Shoot.L4.ElevatorPosition).alongWith(new DoNothing()).withTimeout(Constants.Coral.Shoot.L4.WristDelay).andThen(new SetWristPosition(Constants.Coral.Shoot.L4.WristPosition)));
        operatorController.y().and(operatorController.leftTrigger()).onTrue(new SetElevatorPosition(Constants.Algae.Shoot.Barge.ElevatorPosition));
        operatorController.rightTrigger().and(operatorController.leftTrigger().negate()).onTrue(new SetElevatorPosition(Constants.Coral.Shoot.L1.ElevatorPosition));
        operatorController.leftBumper().onTrue(new SetRampPosition(0));
        operatorController.rightBumper().onTrue(new SetRampPosition(1.63));
        operatorController.back().and(operatorController.leftTrigger().negate()).whileTrue(new SetElevatorPosition(Constants.Algae.Intake.Processor.L3.ElevatorPosition).andThen(new IntakeAlgaeForProcessor().alongWith(new SetWristPosition(Constants.Algae.Intake.Processor.L3.WristPosition))));
        operatorController.back().and(operatorController.leftTrigger()).whileTrue(new SetElevatorPosition(Constants.Algae.Intake.Barge.L3.ElevatorPosition).andThen(new IntakeAlgaeForBarge().alongWith(new SetWristPosition(Constants.Algae.Intake.Barge.L3.WristPosition))));
        operatorController.back().onFalse(new SetWristPosition(0).deadlineFor(new PullAlgae()));
        operatorController.start().and(operatorController.leftTrigger().negate()).whileTrue(new SetElevatorPosition(Constants.Algae.Intake.Processor.L2.ElevatorPosition).andThen(new IntakeAlgaeForProcessor().alongWith(new SetWristPosition(Constants.Algae.Intake.Processor.L2.WristPosition))));
        operatorController.start().and(operatorController.leftTrigger()).whileTrue(new SetElevatorPosition(Constants.Algae.Intake.Barge.L2.ElevatorPosition).andThen(new IntakeAlgaeForBarge().alongWith(new SetWristPosition(Constants.Algae.Intake.Barge.L2.WristPosition))));
        operatorController.start().onFalse(new SetWristPosition(0).deadlineFor(new PullAlgae()));
        operatorController.rightTrigger().and(operatorController.leftTrigger()).whileTrue(new JoystickClimber());
        
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
        NamedCommands.registerCommand("AutoScoreLeft", new AutoScoreLeft(drivetrain, visionDrive).withTimeout(1.25));
        NamedCommands.registerCommand("AutoScoreRight", new AutoScoreRight(drivetrain, visionDrive).withTimeout(1.25));
        NamedCommands.registerCommand("L2AutoScore", new L2AutoScore());
        NamedCommands.registerCommand("L3AutoScore", new L3AutoScore());
        NamedCommands.registerCommand("L4AutoScore", new L4AutoScore());
        NamedCommands.registerCommand("AutoHome", new AutoHome());
        NamedCommands.registerCommand("L2SetHeight", new L2SetHeight().withTimeout(2));
        NamedCommands.registerCommand("L3SetHeight", new L3SetHeight().withTimeout(2));
        NamedCommands.registerCommand("L4SetHeight", new L4SetHeight().withTimeout(2));
        NamedCommands.registerCommand("RunCoralIntake", new RunCoralIntake());
        NamedCommands.registerCommand("StopCoralIntake", new StopCoralIntake());
        NamedCommands.registerCommand("DoNothing", new HoldAlgae().withTimeout(0.8));  // was 0.9
        NamedCommands.registerCommand("AlgaeL3Intake", new SetElevatorPosition(Constants.Algae.Intake.Barge.L3.ElevatorPosition).andThen(new IntakeAlgaeForBarge().alongWith(new SetWristPosition(Constants.Algae.Intake.Barge.L3.WristPosition))));
    }
}
