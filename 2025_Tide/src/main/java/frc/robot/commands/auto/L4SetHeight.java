package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.commands.wrist.SetWristPosition;

public class L4SetHeight extends SequentialCommandGroup {   
    
    public L4SetHeight() {
        addCommands(
            new SetElevatorPosition(Constants.Coral.Shoot.L4.ElevatorPosition).alongWith(new DoNothing()).withTimeout(Constants.Coral.Shoot.L4.WristDelay).andThen(new SetWristPosition(Constants.Coral.Shoot.L4.WristPosition)));
    }
}
