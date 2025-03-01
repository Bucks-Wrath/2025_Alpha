package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.commands.wrist.SetWristPosition;

public class L2SetHeight extends SequentialCommandGroup {   
    
    public L2SetHeight() {
        addCommands(
            new SetElevatorPosition(13.2).alongWith(new SetWristPosition(-9)));
    }
}
