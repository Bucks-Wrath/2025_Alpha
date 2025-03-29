package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class L4orL2SetHeight extends SequentialCommandGroup {   
    
    public L4orL2SetHeight() {
        addCommands(
            new ConditionalCommand(new L4SetHeight(), new L2SetHeight(), this::DoWeHaveTimeForL4)
        );
    }

    private boolean DoWeHaveTimeForL4() {
        if (Timer.getMatchTime()<1 && DriverStation.isAutonomous()){
            return false;
        }
        else {
            return true;
        }
    }
}
