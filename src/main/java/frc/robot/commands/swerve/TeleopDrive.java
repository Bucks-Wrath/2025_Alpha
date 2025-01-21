package frc.robot.commands.swerve;

import frc.robot.RobotContainer;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import static edu.wpi.first.units.Units.*;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj2.command.Command;

public class TeleopDrive extends Command {
	private double elevatorPosition = 0;
	private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

	private CommandSwerveDrivetrain drivetrain;

	/* Setting up bindings for necessary control of the swerve drive platform */
	private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
        .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
	
	public TeleopDrive(CommandSwerveDrivetrain drivetrain) {
		this.drivetrain = drivetrain;
		addRequirements(drivetrain);
	}

	// Called just before this Command runs the first time
	public void initialize() {
		elevatorPosition = RobotContainer.elevator.getCurrentPosition();

		if (elevatorPosition > 10) {  
			MaxAngularRate = RotationsPerSecond.of(0.15).in(RadiansPerSecond); 
		}
		else {
			MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
			MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		elevatorPosition = RobotContainer.elevator.getCurrentPosition();

		if (elevatorPosition > 10) {  
			MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond) * 0.2;
			MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond) * 0.2; 
		}
		else {
			MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
			MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond);
		}
		
		drivetrain.applyRequest(() ->
            
		drive.withVelocityX(RobotContainer.driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
			.withVelocityY(RobotContainer.driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
			.withRotationalRate(-RobotContainer.driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
		);
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
			return false;
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}
