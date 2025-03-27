package frc.robot.commands.wrist;

import frc.robot.Constants;
import frc.robot.RobotContainer;

import edu.wpi.first.wpilibj2.command.Command;

public class SetWristPositionProcessor extends Command {
	private double wristPosition = 0;

	public SetWristPositionProcessor() {
		addRequirements(RobotContainer.wrist);
	}

	// Called just before this Command runs the first time
	public void initialize() {  
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		this.wristPosition = RobotContainer.algaeIntake.isProcessor ? Constants.Algae.Shoot.Processor.WristPosition : Constants.Algae.Shoot.Processor.WristPositionHorns;
		RobotContainer.wrist.setTargetPosition(wristPosition);
		RobotContainer.wrist.motionMagicControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
			return RobotContainer.wrist.isInPosition(wristPosition);
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}
