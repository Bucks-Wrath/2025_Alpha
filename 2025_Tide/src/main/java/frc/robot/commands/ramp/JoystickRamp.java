package frc.robot.commands.ramp;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class JoystickRamp extends Command {

	private double positionIncrement = 0.005;
    
    public JoystickRamp() {
        addRequirements(RobotContainer.ramp);
    }
	// Called just before this Command runs the first time
	public void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {

		// joystick control
        double signal = RobotContainer.ramp.JoystickRamp();

        RobotContainer.ramp.incrementTargetPosition((double) (signal * positionIncrement));

		RobotContainer.ramp.motionMagicControl();

	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}