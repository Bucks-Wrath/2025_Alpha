package frc.robot.commands.CANdle;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class SetRedStrobe extends Command {
    
    public SetRedStrobe() {
        addRequirements(RobotContainer.candleSubsystem);
    }

    public void initialize() {
        RobotContainer.candleSubsystem.setAnimate("Strobe Red");
    }

    public void execute() {
        RobotContainer.candleSubsystem.setAnimate("Strobe Red");
        
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
