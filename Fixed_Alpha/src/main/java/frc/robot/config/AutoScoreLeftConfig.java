package frc.robot.config;

public class AutoScoreLeftConfig {

    //Configurations for DISTANCE component of left auto-positioning at the Reef
    public static class DistancePID{
        public static double P = 0.015;
        public static double I = 0.0;
        public static double D = 0.001;
    }

    public static double DistanceTarget = 23.9;
    public static double DistanceTolerance = 0.25;

    //Configurations for STRAFE component of left auto-positioning at the Reef
    public static class StrafePID{
        public static double P = 0.015;
        public static double I = 0.0;
        public static double D = 0.001;
    }

    public static double StrafeTolerance = 0.25;

    //strafeTarget = A * (B ^ target_area) + C
    public static double StrafeFunctionAValue = 14;
    public static double StrafeFunctionBValue = 0.9;
    public static double StrafeFunctionCValue = -13;
    public static double CalculateStrafeTarget(double targetArea) {
        return StrafeFunctionAValue*(Math.pow(StrafeFunctionBValue, targetArea)) + StrafeFunctionCValue;
    }

    //Configurations for ANGLE component of left auto-positioning at the Reef
    public static class AnglePID{
        public static double P = 0.1;
        public static double I = 0.0;
        public static double D = 0.00001;
    }

    public static double AngleTarget = 0;
    public static double AngleTolerance = 2;

}
