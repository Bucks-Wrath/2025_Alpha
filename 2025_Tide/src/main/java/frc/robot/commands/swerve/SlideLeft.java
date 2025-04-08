package frc.robot.commands.swerve;

import frc.robot.subsystems.CommandSwerveDrivetrain;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj2.command.Command;

public class SlideLeft extends Command {    
    private CommandSwerveDrivetrain drivetrain; 
    private SwerveRequest.RobotCentric algaeDrive;

    public SlideLeft(CommandSwerveDrivetrain drivetrain, SwerveRequest.RobotCentric algaeDrive) {
        this.drivetrain = drivetrain;
        this.algaeDrive = algaeDrive;
        addRequirements(drivetrain);
    }

    public void initialize() {

    }
    
    @Override
    public void execute() {

        /* Drive */
        // this assumes AutoScoreLeft was used to align the robot to the reef
        // it also assumes it will be called with a timeout or will turn off when the button is no longer held
            drivetrain.setControl(
                algaeDrive.withVelocityX(0.0) // Drive forward with X
                        .withVelocityY(1.0) // Drive left with positive Y
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