package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.commands.wrist.SetWristPosition;

public class L3SetHeight extends SequentialCommandGroup {   
    
    public L3SetHeight() {
        addCommands(
            new SetElevatorPosition(Constants.Coral.Shoot.L3.ElevatorPosition).alongWith(new SetWristPosition(Constants.Coral.Shoot.DefaultWristPosition)));
    }
}
