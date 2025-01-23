package frc.robot.commands.coral;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class RunCoralIntake extends Command {
    private double RangeThreshold = 0.14;
	private double canRangeValue = 1;

    public RunCoralIntake() {
        addRequirements(RobotContainer.coralIntake);
    }
	// Called just before this Command runs the first time
	public void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		canRangeValue = RobotContainer.coralIntake.getRange();
		//if (RobotContainer.coralIntake.getRange() < RangeThreshold) {
		//	RobotContainer.coralIntake.setSpeed(0);
		//}
		//else {
    		RobotContainer.coralIntake.setSpeed(0.5);
		//}
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return canRangeValue < RangeThreshold && canRangeValue > 0;
		//return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		RobotContainer.coralIntake.setSpeed(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}


