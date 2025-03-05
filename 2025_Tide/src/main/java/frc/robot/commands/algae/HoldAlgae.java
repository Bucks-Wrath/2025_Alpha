package frc.robot.commands.algae;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.Constants;

public class HoldAlgae extends Command {
    
    public HoldAlgae() {
        addRequirements(RobotContainer.algaeIntake);
    }

	private boolean isProcessor;

	// Called just before this Command runs the first time
	public void initialize() {
		isProcessor = RobotContainer.algaeIntake.getIsProcessor();
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		if (isProcessor == true) {
        	RobotContainer.algaeIntake.setSpeed(Constants.Algae.Intake.Processor.HoldSpeed);
		}
		else if (isProcessor == false) {
			RobotContainer.algaeIntake.setSpeed(Constants.Algae.Intake.Barge.HoldSpeed);
		}
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


