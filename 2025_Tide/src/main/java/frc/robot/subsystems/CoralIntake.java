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
    private CANrange FirstIntakeRangeSensor = new CANrange(DeviceIds.CoralIntake.CANrangeId, "canivore");
    private CANrange SecondIntakeRangeSensor = new CANrange(DeviceIds.CoralIntake.CANrangeId2, "canivore");
    private CANrange ThirdIntakeRangeSensor = new CANrange(DeviceIds.CoralIntake.CANrangeId3, "canivore");
    private double RangeThreshold = 0.1;
    private double LongRangeThreshold = 0.25;

    public CoralIntake() {
        /** Shooter Motor Configuration */
        /* Motor Inverts and Neutral Mode */
        IntakeFXConfig1.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        IntakeFXConfig2.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        IntakeFXConfig1.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        IntakeFXConfig2.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        /* Current Limiting */
        // IntakeFXConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        // IntakeFXConfig.CurrentLimits.SupplyCurrentLimit = 20;
        // IntakeFXConfig.CurrentLimits.SupplyCurrentThreshold = 30;
        // IntakeFXConfig.CurrentLimits.SupplyTimeThreshold = 0.01;

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

    public double getFirstSensorRange() {
        return FirstIntakeRangeSensor.getDistance().getValueAsDouble();
    }

    public double getSecondSensorRange() {
        return SecondIntakeRangeSensor.getDistance().getValueAsDouble();
    }

    public double getThirdSensorRange() {
        return ThirdIntakeRangeSensor.getDistance().getValueAsDouble();
    }

    public boolean FirstSensorSeesCoral() {
        return WithinThreshold(getFirstSensorRange());

    }
    public boolean SecondSensorSeesCoral() {
        return WithinThreshold(getSecondSensorRange());

    }
    public boolean ThirdSensorSeesCoral() {
        return WithinLongThreshold(getThirdSensorRange());

    }

    public boolean WithinThreshold(double currentValue) {
        return currentValue < RangeThreshold && currentValue > 0;
    }

    public boolean WithinLongThreshold(double currentValue) {
        return currentValue < LongRangeThreshold && currentValue > 0;
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
        } catch (Exception e) {
            DriverStation.reportError("Coral.resetIntakeEncoders exception.  You're Screwed! : " + e.toString(), false);
        }
    }

    public void updateDashboard() {
        SmartDashboard.putNumber("Intake Coral One Current", this.getCurrentDrawOne());
        SmartDashboard.putNumber("Intake Coral Two Current", this.getCurrentDrawTwo());
        SmartDashboard.putNumber("Intake Sensor Range", this.getFirstSensorRange());
        SmartDashboard.putNumber("Intake Sensor Range2", this.getSecondSensorRange());
        SmartDashboard.putNumber("Intake Sensor Range3", this.getThirdSensorRange());
        SmartDashboard.putBoolean("Intake Third Sensor", this.ThirdSensorSeesCoral());
    }
}