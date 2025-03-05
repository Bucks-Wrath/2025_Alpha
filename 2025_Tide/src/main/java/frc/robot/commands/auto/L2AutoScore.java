package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.coral.ShootCoralIntake;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.commands.wrist.SetWristPosition;

public class L2AutoScore extends SequentialCommandGroup {   
    
    public L2AutoScore() {
        addCommands(
            new ShootCoralIntake().withTimeout(Constants.Coral.Shoot.Default.AutoDelay)
            .andThen(new SetElevatorPosition(0).alongWith(new SetWristPosition(0))));
    }
}
