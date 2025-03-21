package frc.robot.commands.coral;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class ShootCoralIntakeTrough extends Command {

    public ShootCoralIntakeTrough() {
        addRequirements(RobotContainer.coralIntake);
    }
	// Called just before this Command runs the first time
	public void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
    		RobotContainer.coralIntake.setSpeed(Constants.Coral.Shoot.L1.ShooterMotorOneSpeed, Constants.Coral.Shoot.L1.ShooterMotorTwoSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override public void end(boolean interrupted) {
		RobotContainer.coralIntake.setSpeed(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end(true);
	}
}


