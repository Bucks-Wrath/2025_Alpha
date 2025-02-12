package frc.robot.commands.coral;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.commands.algae.StopAlgaeIntake;

public class StopCoralIntake extends Command {
	private boolean firstSensorSeesCoral = false;
	private boolean secondSensorSeesCoral = false;
	private boolean thirdSensorSeesCoral = false;


    public StopCoralIntake() {
        addRequirements(RobotContainer.coralIntake);
    }
	// Called just before this Command runs the first time
	public void initialize() {
		RobotContainer.candleSubsystem.setAnimate("Orange");

	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		firstSensorSeesCoral = RobotContainer.coralIntake.FirstSensorSeesCoral();
		secondSensorSeesCoral = RobotContainer.coralIntake.SecondSensorSeesCoral();
		thirdSensorSeesCoral = RobotContainer.coralIntake.ThirdSensorSeesCoral();

		if (thirdSensorSeesCoral)  {
			RobotContainer.coralIntake.setSpeed( 0.2, 0.2);
		}

	    else {
			if (secondSensorSeesCoral && firstSensorSeesCoral )  {
				RobotContainer.coralIntake.setSpeed( 0, 0);
			}

			else if (!secondSensorSeesCoral && firstSensorSeesCoral){
				RobotContainer.coralIntake.setSpeed(-0.125, -0.125);
			}
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		RobotContainer.coralIntake.setSpeed(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}


