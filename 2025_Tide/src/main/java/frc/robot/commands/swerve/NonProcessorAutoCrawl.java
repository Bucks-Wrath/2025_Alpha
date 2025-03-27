package frc.robot.commands.swerve;

import frc.robot.subsystems.CommandSwerveDrivetrain;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;

public class NonProcessorAutoCrawl extends Command {    
    private CommandSwerveDrivetrain drivetrain; 
    private SwerveRequest.FieldCentricFacingAngle crawlDrive;

    public NonProcessorAutoCrawl(CommandSwerveDrivetrain drivetrain, SwerveRequest.FieldCentricFacingAngle crawlDrive) {
        this.drivetrain = drivetrain;
        this.crawlDrive = crawlDrive;
        addRequirements(drivetrain);
    }

    public void initialize() {

    }
    
    @Override
    public void execute() {

        /* Drive */
            drivetrain.setControl(
                    crawlDrive.withVelocityX(-0.404) // Drive forward with negative Y (forward)
                        .withVelocityY(0.29) // Drive left with negative X (left)
                        .withTargetDirection(Rotation2d.fromDegrees(-54))
                        .withHeadingPID(2, 0, 0)
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
        end(); 
	}

}