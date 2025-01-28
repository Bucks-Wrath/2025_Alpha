package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DeviceIds;

public class CoralIntake extends SubsystemBase {

	private TalonFX IntakeFalconOne = new TalonFX(DeviceIds.CoralIntake.LeadMotorId, "canivore");
    private TalonFXConfiguration IntakeFXConfig1 = new TalonFXConfiguration();
    private TalonFXConfiguration IntakeFXConfig2 = new TalonFXConfiguration();
    private TalonFX IntakeFalconTwo = new TalonFX(DeviceIds.CoralIntake.FollowerMotorId, "canivore");
    private CANrange IntakeRangeSensor = new CANrange(DeviceIds.CoralIntake.CANrangeId, "canivore"); 
    private CANrange IntakeRangeSensor2 = new CANrange(DeviceIds.CoralIntake.CANrangeId2, "canivore"); 


	public CoralIntake() {
        /** Shooter Motor Configuration */
        /* Motor Inverts and Neutral Mode */
		IntakeFXConfig1.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        IntakeFXConfig2.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        IntakeFXConfig1.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        IntakeFXConfig2.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        /* Current Limiting */
        //IntakeFXConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        //IntakeFXConfig.CurrentLimits.SupplyCurrentLimit = 20;
        //IntakeFXConfig.CurrentLimits.SupplyCurrentThreshold = 30;
        //IntakeFXConfig.CurrentLimits.SupplyTimeThreshold = 0.01;

        IntakeFXConfig1.CurrentLimits.StatorCurrentLimitEnable = true;
        IntakeFXConfig2.CurrentLimits.StatorCurrentLimitEnable = true;
        IntakeFXConfig1.CurrentLimits.StatorCurrentLimit = 25;
        IntakeFXConfig2.CurrentLimits.StatorCurrentLimit = 25;
        /* PID Config */
        IntakeFXConfig1.Slot0.kP = 0.2;
        IntakeFXConfig1.Slot0.kI = 0;
        IntakeFXConfig1.Slot0.kD = 0;

        IntakeFXConfig2.Slot0.kP = 0.2;
        IntakeFXConfig2.Slot0.kI = 0;
        IntakeFXConfig2.Slot0.kD = 0;
        /* Open and Closed Loop Ramping */
        IntakeFXConfig1.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 0.1;
        IntakeFXConfig2.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 0.1;
        IntakeFXConfig1.OpenLoopRamps.VoltageOpenLoopRampPeriod = 0.1;
        IntakeFXConfig2.OpenLoopRamps.VoltageOpenLoopRampPeriod = 0.1;

        IntakeFXConfig1.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0.1;
        IntakeFXConfig2.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0.1;
        IntakeFXConfig1.ClosedLoopRamps.VoltageClosedLoopRampPeriod = 0.1;
        IntakeFXConfig2.ClosedLoopRamps.VoltageClosedLoopRampPeriod = 0.1;

        // Config Motor
        IntakeFalconOne.getConfigurator().apply(IntakeFXConfig1);
        IntakeFalconTwo.getConfigurator().apply(IntakeFXConfig2);
        IntakeFalconOne.getConfigurator().setPosition(0.0);
        IntakeFalconTwo.getConfigurator().setPosition(0.0);
	}
    public double getRange1() {
        return IntakeRangeSensor.getDistance().getValueAsDouble();
    }

    public double getRange2() {
        return IntakeRangeSensor2.getDistance().getValueAsDouble();
    }

	public void setSpeed(double motorOneSpeed, double motorTwoSpeed) {
        this.IntakeFalconOne.set(motorOneSpeed);
        this.IntakeFalconTwo.set(motorTwoSpeed);
	}
   
	public double getCurrentDrawOne() {
		return this.IntakeFalconOne.getSupplyCurrent().getValueAsDouble();
	}

    public double getCurrentDrawTwo() {
		return this.IntakeFalconTwo.getSupplyCurrent().getValueAsDouble();
	}

	public void resetShooterEncoder() {
        try {
			IntakeFalconOne.getConfigurator().setPosition(0.0);
            IntakeFalconTwo.getConfigurator().setPosition(0.0);
        }
        catch (Exception e) {
            DriverStation.reportError("Coral.resetIntakeEncoders exception.  You're Screwed! : " + e.toString(), false);
        }
	}

	public void updateDashboard() {
		SmartDashboard.putNumber("Intake Coral One Current", this.getCurrentDrawOne());
        SmartDashboard.putNumber("Intake Coral Two Current", this.getCurrentDrawTwo());
        SmartDashboard.putNumber("Intake Sensor Range", this.getRange1());
        SmartDashboard.putNumber("Intake Sensor Range2", this.getRange2());


	}
}