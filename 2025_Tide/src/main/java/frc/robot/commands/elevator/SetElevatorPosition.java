package frc.robot.commands.elevator;

import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.Command;

public class SetElevatorPosition extends Command {
	private double elevatorPosition = 0;

	public SetElevatorPosition(double elevatorPosition) {
		this.elevatorPosition = elevatorPosition;

		addRequirements(RobotContainer.elevator);
	}

	// Called just before this Command runs the first time
	public void initialize() {
		RobotContainer.elevator.setTargetPosition(elevatorPosition);
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		RobotContainer.elevator.motionMagicControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
			return RobotContainer.elevator.isInPosition(elevatorPosition);
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}
