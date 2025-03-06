package frc.robot.commands.CANdle;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class WatchClock extends Command {
    
    public WatchClock() {
        addRequirements(RobotContainer.candleSubsystem);
    }

    public void initialize() {
        
    }

    public void execute() {
        // if (Timer.getMatchTime()<15 && DriverStation.isTeleop()){
        //     RobotContainer.candleSubsystem.setAnimate("Strobe Yellow"); 
        //    }
        //  else if (Timer.getMatchTime()<30 && DriverStation.isTeleop()){
        //     RobotContainer.candleSubsystem.setAnimate("Yellow"); 
        //    }

        end();
        
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
