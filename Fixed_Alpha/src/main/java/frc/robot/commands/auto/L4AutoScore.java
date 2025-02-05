package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.coral.ShootCoralIntake;
import frc.robot.commands.coral.StopCoralIntake;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.commands.wrist.SetWristPosition;

public class L4AutoScore extends SequentialCommandGroup {   
    
    public L4AutoScore() {
        addCommands(
            new SetElevatorPosition(50.4).alongWith(new StopCoralIntake()).withTimeout(0.45).andThen(new SetWristPosition(-16.4)));
    }
}
