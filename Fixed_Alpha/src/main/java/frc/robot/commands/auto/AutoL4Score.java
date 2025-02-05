package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.coral.ShootCoralIntake;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.commands.wrist.SetWristPosition;

public class AutoL4Score extends SequentialCommandGroup {   
    
    public AutoL4Score() {
        addCommands(
            new ShootCoralIntake().withTimeout(0.5).andThen(new SetWristPosition(0))
            .andThen(new SetElevatorPosition(0)));
        }
}
