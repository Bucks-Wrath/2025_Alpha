package frc.robot.commands.vision;

import frc.robot.RobotContainer;
import frc.robot.subsystems.LeftLimelight;

import edu.wpi.first.wpilibj2.command.Command;

public class WaitForGoSignal extends Command {    
    private LeftLimelight limelight; 
    private double proximityTarget;

    public WaitForGoSignal(double proximityTarget) {
        this.limelight = RobotContainer.leftLimelight;
        this.proximityTarget = proximityTarget;
    }

    public void initialize() {
    }
    
    @Override
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
        return limelight.gettz() < this.proximityTarget;
    }

	// Called once after isFinished returns true
    @Override public void end(boolean interrupted) {
    }

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
        this.end(true); 
	}

}   