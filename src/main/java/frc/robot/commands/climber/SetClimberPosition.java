package frc.robot.commands.climber;

import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.Command;

public class SetClimberPosition extends Command {
	private double climberPosition = 0;

	public SetClimberPosition(double climberPosition) {
		this.climberPosition = climberPosition;

		addRequirements(RobotContainer.climber);
	}

	// Called just before this Command runs the first time
	public void initialize() {
		RobotContainer.climber.setTargetPosition(climberPosition);
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		RobotContainer.climber.motionMagicControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
			return RobotContainer.climber.isInPosition(climberPosition);
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}
