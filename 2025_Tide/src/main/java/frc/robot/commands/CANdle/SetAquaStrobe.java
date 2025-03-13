package frc.robot.commands.CANdle;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class SetAquaStrobe extends Command {
    
    public SetAquaStrobe() {
        addRequirements(RobotContainer.candleSubsystem);
    }

    public void initialize() {
        RobotContainer.candleSubsystem.setAnimate("Strobe Aqua");
    }

    public void execute() {
        RobotContainer.candleSubsystem.setAnimate("Strobe Aqua");
        
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
