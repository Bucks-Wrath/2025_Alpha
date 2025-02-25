package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class SetRainbow extends Command {
    
    public SetRainbow() {
        addRequirements(RobotContainer.candleSubsystem);
    }

    public void initialize() {
        RobotContainer.candleSubsystem.setAnimate("Rainbow");
    }

    public void execute() {

        
    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        end();
    }
}
