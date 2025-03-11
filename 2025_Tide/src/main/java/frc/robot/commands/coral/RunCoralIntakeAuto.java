package frc.robot.commands.coral;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class RunCoralIntakeAuto extends Command {
	private boolean firstSensorSeesCoral = false;
	private boolean secondSensorSeesCoral = false;
	private boolean thirdSensorSeesCoral = false;
	private boolean done = false;


    public RunCoralIntakeAuto() {
        addRequirements(RobotContainer.coralIntake);
    }
	// Called just before this Command runs the first time
	public void initialize() {
		RobotContainer.candleSubsystem.setAnimate("Color Flow");
		done = false;
		firstSensorSeesCoral = false;
		secondSensorSeesCoral = false;

	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		firstSensorSeesCoral = RobotContainer.coralIntake.FirstSensorSeesCoral();
		secondSensorSeesCoral = RobotContainer.coralIntake.SecondSensorSeesCoral();
		thirdSensorSeesCoral = RobotContainer.coralIntake.ThirdSensorSeesCoral();
		

		if (thirdSensorSeesCoral)  {
			RobotContainer.coralIntake.setSpeed( 0.2, 0.2);
		}

		else if (secondSensorSeesCoral){
			done = true;
		}

	    //else if (firstSensorSeesCoral){
		//	if (secondSensorSeesCoral)  {
		//		RobotContainer.coralIntake.setSpeed( 0, 0);
		//	}
		//
		//	else if (!secondSensorSeesCoral){
		//		RobotContainer.coralIntake.setSpeed(-0.125, -0.125);
		//	}
		//}

		else if (!firstSensorSeesCoral && !secondSensorSeesCoral && !thirdSensorSeesCoral){
			RobotContainer.coralIntake.setSpeed(0, 0);
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return done == true;
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