package frc.robot.config;

public class AutoScoreRightConfig {

    //Configurations for DISTANCE component of left auto-positioning at the Reef
    public static class DistancePID{
        public static double P = 0.4;
        public static double I = 0.0;
        public static double D = 0.0;
    }

    public static double DistanceTarget = 0.39;
    public static double DistanceTolerance = 0.01;

    //Configurations for STRAFE component of left auto-positioning at the Reef
    public static class StrafePID{
        public static double P = 0.5;
        public static double I = 0.0;
        public static double D = 0.0;
    }
    public static double StrafeTarget = -0.17; // left is positive
    public static double StrafeTolerance = 0.01;

    //Configurations for ANGLE component of left auto-positioning at the Reef
    public static class AnglePID{
        public static double P = 0.1;
        public static double I = 0.0;
        public static double D = 0.0;
    }

    public static double AngleTarget = 0;
    public static double AngleTolerance = 0.5;

}
