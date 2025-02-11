package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.coral.ShootCoralIntake;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.commands.wrist.SetWristPosition;

public class L3SetHeight extends SequentialCommandGroup {   
    
    public L3SetHeight() {
        addCommands(
            new SetElevatorPosition(27.3).alongWith(new SetWristPosition(-9)));
    }
}
