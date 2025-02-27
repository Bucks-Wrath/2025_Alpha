package frc.robot.dashboard;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.config.AutoScoreLeftConfig;

public class AutoScoreLeftDashboard {
    
    private static DoubleEntry StrafeTargetEntry;
    private static DoubleEntry StrafeToleranceEntry;
    private static DoubleEntry AngleTargetEntry;
    private static DoubleEntry AngleToleranceEntry;
    private static DoubleEntry DistanceTargetEntry;
    private static DoubleEntry DistanceToleranceEntry;

    private static NetworkTableInstance nti = NetworkTableInstance.getDefault();
    private static NetworkTable aslTable = nti.getTable("Auto Score Left");

    public static void AddDashboard() {


        //STRAFE CONFIG
        SmartDashboard.putData("AutoScoreLeft Strafe PID",RobotContainer.rightLimelight.strafeController);
        StrafeTargetEntry = addEntryWithValue("Strafe Target", AutoScoreLeftConfig.StrafeTarget);
        StrafeToleranceEntry = addEntryWithValue("Strafe Tolerance", AutoScoreLeftConfig.StrafeTolerance);

        //ANGLE CONFIG
        SmartDashboard.putData("AutoScoreLeft Angle PID", RobotContainer.rightLimelight.angleController);
        AngleTargetEntry = addEntryWithValue("Angle Target",AutoScoreLeftConfig.AngleTarget);
        AngleToleranceEntry = addEntryWithValue("Angle Tolerance", AutoScoreLeftConfig.AngleTolerance);

        //DISTANCE CONFIG
        SmartDashboard.putData("AutoScoreLeft Distance PID",RobotContainer.rightLimelight.distanceController);
        DistanceTargetEntry = addEntryWithValue("Distance Target", AutoScoreLeftConfig.DistanceTarget);
        DistanceToleranceEntry = addEntryWithValue("Distance Tolerance", AutoScoreLeftConfig.DistanceTolerance);
    }

    public static void syncDashboard() {
        AutoScoreLeftConfig.StrafeTarget = StrafeTargetEntry.get(AutoScoreLeftConfig.StrafeTarget);
        AutoScoreLeftConfig.StrafeTolerance = StrafeToleranceEntry.get(AutoScoreLeftConfig.StrafeTolerance);
        AutoScoreLeftConfig.AngleTarget = AngleTargetEntry.get(AutoScoreLeftConfig.AngleTarget);
        AutoScoreLeftConfig.AngleTolerance = AngleToleranceEntry.get(AutoScoreLeftConfig.AngleTolerance);
        AutoScoreLeftConfig.DistanceTarget = DistanceTargetEntry.get(AutoScoreLeftConfig.DistanceTarget);
        AutoScoreLeftConfig.DistanceTolerance = DistanceToleranceEntry.get(AutoScoreLeftConfig.DistanceTolerance);
        return;

    }


    private static DoubleEntry addEntryWithValue(String name, double defaultValue) {
        DoubleEntry newEntry = aslTable.getDoubleTopic(name).getEntry(defaultValue);
        newEntry.set(defaultValue);
        return newEntry;
    }

    
}
