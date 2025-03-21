package frc.robot.commands.coral;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class StopCoralIntake extends Command {
	private boolean firstSensorSeesCoral = false;
	private boolean secondSensorSeesCoral = false;
	private boolean thirdSensorSeesCoral = false;


    public StopCoralIntake() {
        addRequirements(RobotContainer.coralIntake);
    }
	// Called just before this Command runs the first time
	public void initialize() {
		firstSensorSeesCoral = false;
		secondSensorSeesCoral = false;
		thirdSensorSeesCoral = false;

	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		firstSensorSeesCoral = RobotContainer.coralIntake.FirstSensorSeesCoral();
		secondSensorSeesCoral = RobotContainer.coralIntake.SecondSensorSeesCoral();
		thirdSensorSeesCoral = RobotContainer.coralIntake.ThirdSensorSeesCoral();

		if (thirdSensorSeesCoral)  {
			RobotContainer.coralIntake.setSpeed( 0.25, 0.25);
		}

	    else if (firstSensorSeesCoral){
			if (secondSensorSeesCoral)  {
				RobotContainer.coralIntake.setSpeed( 0, 0);
				RobotContainer.candleSubsystem.setAnimate("Purple");
			}

			else if (!secondSensorSeesCoral){
				RobotContainer.coralIntake.setSpeed(-0.125, -0.125);
			}
		}

		else if (!firstSensorSeesCoral && !secondSensorSeesCoral && !thirdSensorSeesCoral){
			RobotContainer.coralIntake.setSpeed(0, 0);
		}

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


