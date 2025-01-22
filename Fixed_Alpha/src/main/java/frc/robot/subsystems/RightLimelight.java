package frc.robot.subsystems;

import edu.wpi.first.networktables.DoubleArrayEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DeviceIds;

public class RightLimelight extends SubsystemBase {

   private double ta;
   private double tx;
   private double ty;
   private double tl;
   private double ts;
   private double[] tAng;
   private DoubleArrayEntry tAngArray;

   NetworkTableEntry prelimtx;
   NetworkTableEntry prelimty;
   NetworkTableEntry prelimta;
   NetworkTableEntry prelimtl;
   NetworkTableEntry prelimts;
   NetworkTableEntry prelimtAng;
   NetworkTableEntry prelimCamtran;
   NetworkTable table;
   NetworkTableInstance Inst;

   public RightLimelight() {
      Inst = NetworkTableInstance.getDefault();
      table = Inst.getTable(DeviceIds.Limelight.RightTableName);
      prelimta = table.getEntry("ta");
      prelimtx = table.getEntry("tx");
      prelimty = table.getEntry("ty");
      prelimtl = table.getEntry("tlong");
      prelimts = table.getEntry("tshort");
      prelimtAng = table.getEntry("botpose_targetspace");
   }

   public void updateGameState(){
      ta = prelimta.getDouble(ta);
      tx = prelimtx.getDouble(0);
      ty = prelimty.getDouble(ty);
      tl = prelimtl.getDouble(tl);
      ts = prelimts.getDouble(ts);
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

   public double getShort() {
      ts = prelimts.getDouble(ts);
      return ts;
   }

   public double getLong() {
      tl = prelimtl.getDouble(tl);
      return tl;
   }

   public double gettAng() {
      tAng = prelimtAng.getDoubleArray(new double[6]);
      double actAng = tAng[4];
      return actAng;
   }

   public void visionMode(){
      NetworkTableInstance.getDefault().getTable("limelight-right").getEntry("ledMode").setNumber(3);
      NetworkTableInstance.getDefault().getTable("limelight-right").getEntry("camMode").setNumber(0);
   }

   public void cameraMode(){
      NetworkTableInstance.getDefault().getTable("limelight-right").getEntry("ledMode").setNumber(1);
      NetworkTableInstance.getDefault().getTable("limelight-right").getEntry("camMode").setNumber(1);
   }

   public void updateDashboard() {
	   SmartDashboard.putNumber("Right ta", getArea());
      SmartDashboard.putNumber("Right tx", getX());
      SmartDashboard.putNumber("Right ty", getY());
      SmartDashboard.putNumber("Right tl", getLong());
      SmartDashboard.putNumber("Right ts", getShort());
      SmartDashboard.putNumber("Right tAng", gettAng());

	}
}
