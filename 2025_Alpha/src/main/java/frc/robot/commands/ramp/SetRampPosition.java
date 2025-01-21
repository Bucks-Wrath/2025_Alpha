package frc.robot.commands.ramp;

import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.Command;

public class SetRampPosition extends Command {
	private double rampPosition = 0;

	public SetRampPosition(double rampPosition) {
		this.rampPosition = rampPosition;

		addRequirements(RobotContainer.ramp);
	}

	// Called just before this Command runs the first time
	public void initialize() {
		RobotContainer.ramp.setTargetPosition(rampPosition);
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		RobotContainer.ramp.motionMagicControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
			return RobotContainer.ramp.isInPosition(rampPosition);
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}
