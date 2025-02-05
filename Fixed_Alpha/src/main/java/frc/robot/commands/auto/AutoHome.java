package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.commands.wrist.SetWristPosition;

public class AutoHome extends SequentialCommandGroup {   
    
    public AutoHome() {
        addCommands(
            new SetElevatorPosition(0).alongWith(new SetWristPosition(0)));
    }
}
