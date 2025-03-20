package frc.robot.commands.vision;

import frc.robot.RobotContainer;
import frc.robot.subsystems.LeftLimelight;
import frc.robot.subsystems.RightLimelight;
import edu.wpi.first.wpilibj2.command.Command;

public class WaitForGoSignal extends Command {    
    private LeftLimelight leftLimelight; 
    private RightLimelight rightLimelight;
    private double proximityTarget;

    public WaitForGoSignal(double proximityTarget) {
        this.leftLimelight = RobotContainer.leftLimelight;
        this.rightLimelight = RobotContainer.rightLimelight;
        this.proximityTarget = proximityTarget;
    }

    public void initialize() {
    }
    
    @Override
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
        boolean leftLimelightAligned = leftLimelight.ifValidTag() && (leftLimelight.gettz() < this.proximityTarget);
        boolean rightLimelightAligned = rightLimelight.ifValidTag() && (rightLimelight.gettz() < this.proximityTarget);
        return leftLimelightAligned || rightLimelightAligned;
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