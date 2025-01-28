package frc.robot.dashboard;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.config.AutoScoreRightConfig;

public class AutoScoreRightDashboard {
    

    private static DoubleEntry StrafeToleranceEntry;
    private static DoubleEntry AngleTargetEntry;
    private static DoubleEntry AngleToleranceEntry;
    private static DoubleEntry DistanceTargetEntry;
    private static DoubleEntry DistanceToleranceEntry;

    private static NetworkTableInstance nti = NetworkTableInstance.getDefault();
    private static NetworkTable aslTable = nti.getTable("Auto Score Right");

    public static void AddDashboard() {


        //STRAFE CONFIG
        SmartDashboard.putData("AutoScoreRight Strafe PID",RobotContainer.leftLimelight.strafeController);
        StrafeToleranceEntry = addEntryWithValue("Strafe Tolerance", AutoScoreRightConfig.StrafeTolerance);

        //ANGLE CONFIG
        SmartDashboard.putData("AutoScoreRight Angle PID", RobotContainer.leftLimelight.angleController);
        AngleTargetEntry = addEntryWithValue("Angle Target",AutoScoreRightConfig.AngleTarget);
        AngleToleranceEntry = addEntryWithValue("Angle Tolerance", AutoScoreRightConfig.AngleTolerance);

        //DISTANCE CONFIG
        SmartDashboard.putData("AutoScoreRight Distance PID",RobotContainer.leftLimelight.distanceController);
        DistanceTargetEntry = addEntryWithValue("Distance Target", AutoScoreRightConfig.DistanceTarget);
        DistanceToleranceEntry = addEntryWithValue("Distance Tolerance", AutoScoreRightConfig.DistanceTolerance);
    }

    public static void syncDashboard() {
        AutoScoreRightConfig.StrafeTolerance = StrafeToleranceEntry.get(AutoScoreRightConfig.StrafeTolerance);
        AutoScoreRightConfig.AngleTarget = AngleTargetEntry.get(AutoScoreRightConfig.AngleTarget);
        AutoScoreRightConfig.AngleTolerance = AngleToleranceEntry.get(AutoScoreRightConfig.AngleTolerance);
        AutoScoreRightConfig.DistanceTarget = DistanceTargetEntry.get(AutoScoreRightConfig.DistanceTarget);
        AutoScoreRightConfig.DistanceTolerance = DistanceToleranceEntry.get(AutoScoreRightConfig.DistanceTolerance);
        return;

    }


    private static DoubleEntry addEntryWithValue(String name, double defaultValue) {
        DoubleEntry newEntry = aslTable.getDoubleTopic(name).getEntry(defaultValue);
        newEntry.set(defaultValue);
        return newEntry;
    }

    
}
