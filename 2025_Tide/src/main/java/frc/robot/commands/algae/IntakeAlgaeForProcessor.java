package frc.robot.commands.algae;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class IntakeAlgaeForProcessor extends Command {
    
    public IntakeAlgaeForProcessor() {
        addRequirements(RobotContainer.algaeIntake);
    }
	// Called just before this Command runs the first time
	public void initialize() {
		RobotContainer.algaeIntake.setIsProcessor(true);
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
        RobotContainer.algaeIntake.setSpeed(Constants.Algae.Intake.Processor.IntakeSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		RobotContainer.algaeIntake.setSpeed(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}


