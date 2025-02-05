package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.coral.ShootCoralIntake;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.commands.wrist.SetWristPosition;

public class AutoScore extends SequentialCommandGroup {   
    
    public AutoScore() {
        addCommands(
            new ShootCoralIntake().withTimeout(0.5)
            .andThen(new SetElevatorPosition(0).alongWith(new SetWristPosition(0))));
    }
}
