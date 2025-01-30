package frc.robot.commands.coral;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.commands.algae.StopAlgaeIntake;

public class RunCoralIntake extends Command {
    private double RangeThreshold = 0.1;
	private double canRangeValue1 = 0;
	private double canRangeValue2 = 0;
	private boolean done;


    public RunCoralIntake() {
        addRequirements(RobotContainer.coralIntake);
    }
	// Called just before this Command runs the first time
	public void initialize() {
		done = false;

	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		canRangeValue1 = RobotContainer.coralIntake.getRange1();
		canRangeValue2 = RobotContainer.coralIntake.getRange2();

		if ((canRangeValue1 > RangeThreshold || canRangeValue1 == 0) && (canRangeValue2 > RangeThreshold || canRangeValue2 == 0)) {
			RobotContainer.coralIntake.setSpeed(0.5, 0.5);
		}
		
		else if ((canRangeValue1 > RangeThreshold || canRangeValue1 == 0) && (canRangeValue2 < RangeThreshold && canRangeValue2 > 0)) {
			RobotContainer.coralIntake.setSpeed(0.125, 0.125);
		}

		else if (canRangeValue2 < RangeThreshold && canRangeValue2 > 0 && canRangeValue1 < RangeThreshold && canRangeValue1 > 0) {
			Timer.delay(0.15);
			done = true;
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		//return canRangeValue1 < RangeThreshold && canRangeValue1 > 0;
		return done == true;
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


