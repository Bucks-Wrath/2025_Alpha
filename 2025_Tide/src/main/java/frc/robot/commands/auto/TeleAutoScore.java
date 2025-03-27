package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.coral.ShootCoralIntake;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.commands.wrist.SetWristPosition;

public class TeleAutoScore extends SequentialCommandGroup {   
    
    public TeleAutoScore() {
        addCommands(
            new ShootCoralIntake().withTimeout(Constants.Coral.Shoot.Default.AutoDelay).andThen(new SetWristPosition(0).alongWith(new DoNothing().withTimeout(0.125).andThen(new SetElevatorPosition(0.25)))));
        }
}
