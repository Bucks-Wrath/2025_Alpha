package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DeviceIds;
import frc.robot.config.AutoScoreRightConfig;

public class LeftLimelight extends SubsystemBase {

   private double ta;
   private double tx;
   private double ty;
   private double tl;
   private double ts;
   private double[] tAng;
   private int tv;

   NetworkTableEntry prelimtx;
   NetworkTableEntry prelimty;
   NetworkTableEntry prelimta;
   NetworkTableEntry prelimtl;
   NetworkTableEntry prelimts;
   NetworkTableEntry prelimtAng;
   NetworkTableEntry prelimtv;
   NetworkTableEntry prelimCamtran;
   NetworkTable table;
   NetworkTableInstance Inst;

   public final PIDController angleController = new PIDController(AutoScoreRightConfig.AnglePID.P, AutoScoreRightConfig.AnglePID.I,AutoScoreRightConfig.AnglePID.D); // needs to be tuned
   public final PIDController strafeController = new PIDController(AutoScoreRightConfig.StrafePID.P,AutoScoreRightConfig.StrafePID.I,AutoScoreRightConfig.StrafePID.D);
   public final PIDController distanceController = new PIDController(AutoScoreRightConfig.DistancePID.P, AutoScoreRightConfig.DistancePID.I,AutoScoreRightConfig.DistancePID.D);

   public LeftLimelight() {
      Inst = NetworkTableInstance.getDefault();
      table = Inst.getTable(DeviceIds.Limelight.LeftTableName);
      prelimta = table.getEntry("ta");
      prelimtx = table.getEntry("tx");
      prelimty = table.getEntry("ty");
      prelimtAng = table.getEntry("targetpose_robotspace");
      prelimtv = table.getEntry("tv");

      angleController.setTolerance(AutoScoreRightConfig.AngleTolerance);  // needs to be tuned
      strafeController.setTolerance(AutoScoreRightConfig.StrafeTolerance);
      distanceController.setTolerance(AutoScoreRightConfig.DistanceTolerance);
   }

   public void updateGameState(){
      ta = prelimta.getDouble(ta);
      tx = prelimtx.getDouble(0);
      ty = prelimty.getDouble(ty);
      tv = (int) prelimtv.getInteger(tv);
      tAng = prelimtAng.getDoubleArray(new double[6]);

   }

   public double getArea(){
      ta = prelimta.getDouble(ta);
      return ta;
   }

   public double getX(){
      tx = prelimtx.getDouble(tx);
      return tx;
   }

   public double getY(){
      ty = prelimty.getDouble(ty);
      return ty;
   }

   public boolean ifValidTag() {
      tv = (int) prelimtv.getInteger(tv);
      if (tv == 1) {
         return true;   
      }
      else {
         return false;
      }
      
   }

   public double gettAng() {
      tAng = prelimtAng.getDoubleArray(new double[6]);
      double actAng = tAng[4];
      return actAng;
   }

   public double gettx() {
      tAng = prelimtAng.getDoubleArray(new double[6]);
      double actX = tAng[0];
      return actX;
   }

   public double getty() {
      tAng = prelimtAng.getDoubleArray(new double[6]);
      double actY = tAng[1];
      return actY;
   }

   public double gettz() {
      tAng = prelimtAng.getDoubleArray(new double[6]);
      double actZ = tAng[2];
      return actZ;
   }

   public void visionMode(){
      NetworkTableInstance.getDefault().getTable("limelight-left").getEntry("ledMode").setNumber(3);
      NetworkTableInstance.getDefault().getTable("limelight-left").getEntry("camMode").setNumber(0);
   }

   public void cameraMode(){
      NetworkTableInstance.getDefault().getTable("limelight-left").getEntry("ledMode").setNumber(1);
      NetworkTableInstance.getDefault().getTable("limelight-left").getEntry("camMode").setNumber(1);
   }

   public void updateDashboard() {
	   SmartDashboard.putNumber("Left ta", getArea());
      SmartDashboard.putNumber("Left tx", getX());
      SmartDashboard.putNumber("Left ty", getY());
      SmartDashboard.putNumber("Left tAng", gettAng());
      SmartDashboard.putNumber("Left tX", gettx());
      SmartDashboard.putNumber("Left tY", getty());
      SmartDashboard.putNumber("Left tZ", gettz());
      SmartDashboard.putBoolean("Left tv", ifValidTag());
	}
}
