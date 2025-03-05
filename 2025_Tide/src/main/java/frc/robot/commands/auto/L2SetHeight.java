package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.commands.wrist.SetWristPosition;

public class L2SetHeight extends SequentialCommandGroup {   
    
    public L2SetHeight() {
        addCommands(
            new SetElevatorPosition(Constants.Coral.Shoot.L2.ElevatorPosition).alongWith(new SetWristPosition(Constants.Coral.Shoot.Default.WristPosition)));
    }
}
