package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class SetDefaultPurple extends Command {
    
    public SetDefaultPurple() {
        addRequirements(RobotContainer.candleSubsystem);
    }

    public void initialize() {
        RobotContainer.candleSubsystem.setAnimate("Purple");
    }

    public void execute() {
        RobotContainer.candleSubsystem.setAnimate("Purple");

        
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
