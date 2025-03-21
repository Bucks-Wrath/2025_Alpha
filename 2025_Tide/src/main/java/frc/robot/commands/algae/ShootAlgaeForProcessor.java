package frc.robot.commands.algae;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class ShootAlgaeForProcessor extends Command {
    
    public ShootAlgaeForProcessor() {
        addRequirements(RobotContainer.algaeIntake);
    }
	// Called just before this Command runs the first time
	public void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		if(RobotContainer.algaeIntake.isProcessor){
        	RobotContainer.algaeIntake.setSpeed(Constants.Algae.Shoot.Processor.ShooterSpeed);
		}
		else {
			RobotContainer.algaeIntake.setSpeed(Constants.Algae.Shoot.Processor.ShooterSpeedFromHorns);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override public void end(boolean interrupted) {
		RobotContainer.algaeIntake.setSpeed(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end(true);
	}
}


