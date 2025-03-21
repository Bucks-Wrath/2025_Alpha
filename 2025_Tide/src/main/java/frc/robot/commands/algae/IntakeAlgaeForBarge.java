package frc.robot.commands.algae;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class IntakeAlgaeForBarge extends Command {
	private double gotAlgae = 0;
    
    public IntakeAlgaeForBarge() {
        addRequirements(RobotContainer.algaeIntake);
    }
	// Called just before this Command runs the first time
	public void initialize() {
		RobotContainer.algaeIntake.setIsProcessor(false);
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
        RobotContainer.algaeIntake.setSpeed(Constants.Algae.Intake.Barge.IntakeSpeed);

		gotAlgae = RobotContainer.algaeIntake.getCurrentDrawLeader();

		if (gotAlgae <3 && gotAlgae > 1) {
			RobotContainer.candleSubsystem.setAnimate("Strobe Aqua");
		}
		else {
			RobotContainer.candleSubsystem.setAnimate("Aqua");
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override public void end(boolean interrupted) {
		RobotContainer.algaeIntake.setSpeed(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end(true);
	}
}


