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

	private TalonFX IntakeFalcon = new TalonFX(DeviceIds.CoralIntake.LeadMotorId, "canivore");
    private TalonFXConfiguration IntakeFXConfig = new TalonFXConfiguration();
    private TalonFX IntakeFalconFollower = new TalonFX(DeviceIds.CoralIntake.FollowerMotorId, "canivore");
    private CANrange IntakeRangeSensor = new CANrange(DeviceIds.CoralIntake.CANrangeId, "canivore"); 

	public CoralIntake() {
        /** Shooter Motor Configuration */
        /* Motor Inverts and Neutral Mode */
		IntakeFXConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        IntakeFXConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        // Set Followers
		IntakeFalconFollower.setControl(new Follower(IntakeFalcon.getDeviceID(), true));

        /* Current Limiting */
        //IntakeFXConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        //IntakeFXConfig.CurrentLimits.SupplyCurrentLimit = 20;
        //IntakeFXConfig.CurrentLimits.SupplyCurrentThreshold = 30;
        //IntakeFXConfig.CurrentLimits.SupplyTimeThreshold = 0.01;

        IntakeFXConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        IntakeFXConfig.CurrentLimits.StatorCurrentLimit = 25;

        /* PID Config */
        IntakeFXConfig.Slot0.kP = 0.2;
        IntakeFXConfig.Slot0.kI = 0;
        IntakeFXConfig.Slot0.kD = 0;

        /* Open and Closed Loop Ramping */
        IntakeFXConfig.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 0.1;
        IntakeFXConfig.OpenLoopRamps.VoltageOpenLoopRampPeriod = 0.1;

        IntakeFXConfig.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0.1;
        IntakeFXConfig.ClosedLoopRamps.VoltageClosedLoopRampPeriod = 0.1;

        // Config Motor
        IntakeFalcon.getConfigurator().apply(IntakeFXConfig);
        IntakeFalcon.getConfigurator().setPosition(0.0);
        IntakeFalconFollower.getConfigurator().setPosition(0.0);
	}
    public double getRange() {
        return IntakeRangeSensor.getDistance().getValueAsDouble();

    }
	public void setSpeed(double speed) {
        this.IntakeFalcon.set(speed);
	}

	public double getCurrentDrawLeader() {
		return this.IntakeFalcon.getSupplyCurrent().getValueAsDouble();
	}

    public double getCurrentDrawFollower() {
		return this.IntakeFalconFollower.getSupplyCurrent().getValueAsDouble();
	}

	public void resetShooterEncoder() {
        try {
			IntakeFalcon.getConfigurator().setPosition(0.0);
            IntakeFalconFollower.getConfigurator().setPosition(0.0);
        }
        catch (Exception e) {
            DriverStation.reportError("Coral.resetIntakeEncoders exception.  You're Screwed! : " + e.toString(), false);
        }
	}

	public void updateDashboard() {
		SmartDashboard.putNumber("Intake Current", this.getCurrentDrawLeader());
        SmartDashboard.putNumber("Intake Follower Current", this.getCurrentDrawFollower());
        SmartDashboard.putNumber("Intake Sensor Range", this.getRange());

	}
}