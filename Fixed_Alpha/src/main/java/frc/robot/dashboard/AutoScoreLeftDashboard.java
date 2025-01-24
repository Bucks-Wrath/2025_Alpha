package frc.robot.dashboard;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.RobotContainer;

public class AutoScoreLeftDashboard {

    public static void AddDashboard() {
        Shuffleboard.getTab("Auto Score Left").add("Distance PID",RobotContainer.rightLimelight.distanceController).withWidget(BuiltInWidgets.kPIDController);
        Shuffleboard.getTab("Auto Score Left").add("Strafe PID",RobotContainer.rightLimelight.strafeController).withWidget(BuiltInWidgets.kPIDController);
        Shuffleboard.getTab("Auto Score Left").add("Angle PID",RobotContainer.rightLimelight.angleController).withWidget(BuiltInWidgets.kPIDController);
    }
    
}
