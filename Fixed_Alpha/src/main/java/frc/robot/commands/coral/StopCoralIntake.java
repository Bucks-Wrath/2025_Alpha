package frc.robot.commands.coral;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.commands.algae.StopAlgaeIntake;

public class StopCoralIntake extends Command {
	private boolean secondSensorSeesCoral = false;


    public StopCoralIntake() {
        addRequirements(RobotContainer.coralIntake);
    }
	// Called just before this Command runs the first time
	public void initialize() {
	

	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		secondSensorSeesCoral = RobotContainer.coralIntake.SecondSensorSeesCoral();

	    if (secondSensorSeesCoral)  {
			RobotContainer.coralIntake.setSpeed( 0, 0);
		}

		else  {
			RobotContainer.coralIntake.setSpeed(-0.125, -0.125);
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		//return canRangeValue1 < RangeThreshold && canRangeValue1 > 0;
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


