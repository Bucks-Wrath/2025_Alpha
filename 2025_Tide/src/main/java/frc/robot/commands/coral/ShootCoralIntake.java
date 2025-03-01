package frc.robot.commands.coral;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class ShootCoralIntake extends Command {
    private double RangeThreshold = 0.14;
	private double canRangeValue = 1;

    public ShootCoralIntake() {
        addRequirements(RobotContainer.coralIntake);
    }
	// Called just before this Command runs the first time
	public void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		double elevatorHeight = RobotContainer.elevator.getCurrentPosition();
		double speed = elevatorHeight > 35 ? 1.0 : 0.3;
    		RobotContainer.coralIntake.setSpeed(speed, speed);
			RobotContainer.algaeIntake.setSpeed(-0.3);
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		RobotContainer.coralIntake.setSpeed(0, 0);
		RobotContainer.algaeIntake.setSpeed(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}


