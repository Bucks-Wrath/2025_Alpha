package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.algae.StopAlgaeIntake;
import frc.robot.commands.coral.ShootCoralIntake;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.commands.wrist.SetWristPosition;

public class L4AutoScore extends SequentialCommandGroup {   
    
    public L4AutoScore() {
        addCommands(
            new ShootCoralIntake().withTimeout(0.5).andThen(new SetWristPosition(0).alongWith(new StopAlgaeIntake().withTimeout(0.25).andThen(new SetElevatorPosition(0)))));
        }
}
