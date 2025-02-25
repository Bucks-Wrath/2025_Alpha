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
        if (Timer.getMatchTime()<30 && DriverStation.isTeleop()){
            double timeRemaining=Timer.getMatchTime();
           long timeRounded=Math.round(timeRemaining);
           long remainder=timeRounded % 2;
           if (remainder == 0){
            RobotContainer.candleSubsystem.setAnimate("Strobe Purple"); 
           } 
           else{
            RobotContainer.candleSubsystem.setAnimate("Strobe Yellow"); 
           }
        
        }
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
