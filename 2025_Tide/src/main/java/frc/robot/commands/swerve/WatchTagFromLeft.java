package frc.robot.commands.swerve;

import frc.robot.RobotContainer;
import frc.robot.subsystems.LeftLimelight;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class WatchTagFromLeft extends Command {    
    private CommandXboxController driverController;
    private LeftLimelight limelight; 

    public WatchTagFromLeft(CommandXboxController driverController) {
        this.driverController = driverController;
        this.limelight = RobotContainer.leftLimelight;
    }

    public void initialize() {
    }
    
    @Override
    public void execute() {
        // If we don't see a valid tag, flash red lights and return
        if (!limelight.ifValidTag()) {
            RobotContainer.candleSubsystem.setAnimate("Strobe Red");
            driverController.setRumble(RumbleType.kBothRumble, 1.0);
            return;
        }

        // if we do see a valid tag, flash purple lights, rumble driver controller, and align
        RobotContainer.candleSubsystem.setAnimate("Strobe Purple");
        driverController.setRumble(RumbleType.kBothRumble, 0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
        return false;
    }

	// Called once after isFinished returns true
    @Override public void end(boolean interrupted) {
        driverController.setRumble(RumbleType.kBothRumble, 0.0); 
    }

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
        this.end(true); 
	}

}   