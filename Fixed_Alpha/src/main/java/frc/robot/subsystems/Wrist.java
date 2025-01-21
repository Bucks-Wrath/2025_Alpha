package frc.robot.subsystems;

import frc.lib.models.*;
import frc.robot.DeviceIds;
import frc.robot.Robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Wrist extends SubsystemBase implements IPositionControlledSubsystem {

	private boolean isHoldingPosition = false;

    // Set Different Heights
	private double homePosition = 0;
	private double maxUpTravelPosition = 0;

	public double upPositionLimit = maxUpTravelPosition;
	public double downPositionLimit = -45;
	private double targetPosition = 0;
    private MotionMagicDutyCycle targetPositionDutyCycle = new MotionMagicDutyCycle(0);
	private double feedForward = 0.0;
	public double shooterAddValue;

	private final static double onTargetThreshold = 0.1;
		
	private TalonFX WristFalcon = new TalonFX(DeviceIds.Wrist.MotorId, "canivore");

    private TalonFXConfiguration WristFXConfig = new TalonFXConfiguration();

	public Wrist() {
		// Clear Sticky Faults
		this.WristFalcon.clearStickyFaults();
		
        /** Wrist Motor Configuration */
        /* Motor Inverts and Neutral Mode */
		WristFXConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        WristFXConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        /* Current Limiting */
        //WristFXConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        //WristFXConfig.CurrentLimits.SupplyCurrentLimit = 35;
        //WristFXConfig.CurrentLimits.SupplyCurrentThreshold = 60;
        //WristFXConfig.CurrentLimits.SupplyTimeThreshold = 0.05;

		WristFXConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        WristFXConfig.CurrentLimits.StatorCurrentLimit = 35;

        /* PID Config */
        WristFXConfig.Slot0.kP = 0.2;
        WristFXConfig.Slot0.kI = 0;
        WristFXConfig.Slot0.kD = 0.01;

        /* Open and Closed Loop Ramping */
        WristFXConfig.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 0.25;
        WristFXConfig.OpenLoopRamps.VoltageOpenLoopRampPeriod = 0.25;

        WristFXConfig.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0;
        WristFXConfig.ClosedLoopRamps.VoltageClosedLoopRampPeriod = 0;

        //Config Acceleration and Velocity
        WristFXConfig.MotionMagic.withMotionMagicAcceleration(300);
        WristFXConfig.MotionMagic.withMotionMagicCruiseVelocity(300);

        // Config Motor
        WristFalcon.getConfigurator().apply(WristFXConfig);
        WristFalcon.getConfigurator().setPosition(0.0);
    }

	public void motionMagicControl() {
		this.manageMotion(targetPosition);
        targetPositionDutyCycle.withPosition(targetPosition);
        targetPositionDutyCycle.withFeedForward(feedForward);
		this.WristFalcon.setControl(targetPositionDutyCycle);
	}

	public double getCurrentPosition() {
		return this.WristFalcon.getRotorPosition().getValueAsDouble();
	}

	public double getCurrentDraw() {
		return this.WristFalcon.getSupplyCurrent().getValueAsDouble();
	}

	public boolean isHoldingPosition() {
		return this.isHoldingPosition;
	}

	public void setIsHoldingPosition(boolean isHoldingPosition) {
		this.isHoldingPosition = isHoldingPosition;
	}

	public double getTargetPosition() {
		return this.targetPosition;
	}

	public boolean setTargetPosition(double position) {
		if (!isValidPosition(position)) {
			return false;
		} else {
			this.targetPosition = position;
			return true;
		}
	}

	public void forceSetTargetPosition(double position) {
		this.targetPosition = position;
	}

	public void incrementTargetPosition(double increment) {
		double currentTargetPosition = this.targetPosition;
		double newTargetPosition = currentTargetPosition + increment;
		if (isValidPosition(newTargetPosition)) {		// && isWristSafe(newTargetPosition) check for other subsystems
			this.targetPosition = newTargetPosition;
		}
	}

	public boolean isValidPosition(double position) {
		boolean withinBounds = position <= upPositionLimit && position >= downPositionLimit;
		return withinBounds;
	}

    // communicate with commands
	public double getHomePosition() {
		return this.homePosition;
	}

	public double getMaxUpTravelPosition() {
		return this.maxUpTravelPosition;
	}

	public double getFeedForward() {
		return this.feedForward;
	}

	public void resetWristEncoder() {
        try {
			WristFalcon.getConfigurator().setPosition(0.0);
        }
        catch (Exception e) {
            DriverStation.reportError("Wrist.resetWristEncoders exception.  You're Screwed! : " + e.toString(), false);
        }
	}

	public double JoystickWrist(){
		double value = 0;
		value = -Robot.m_robotContainer.getOperatorRightStickY();
		return value;
	}

	public double getPositionError() {
		double currentPosition = this.getCurrentPosition();
		double targetPosition = this.getTargetPosition();
		double positionError = Math.abs(currentPosition - targetPosition);
		return positionError;
	}

	public void manageMotion(double targetPosition) {
		double currentPosition = getCurrentPosition();
		if (currentPosition < targetPosition) {
				// set based on gravity
		}
		else {
				//set based on gravity
		}
	}

	public void zeroTarget() {
		targetPosition = 0;
	}

	public void updateDashboard() {
		SmartDashboard.putNumber("Wrist Position", this.getCurrentPosition());
		SmartDashboard.putNumber("Wrist Target Position", this.getTargetPosition());
		SmartDashboard.putNumber("Wrist Position Error", this.getPositionError());
		SmartDashboard.putNumber("Wrist Velocity", this.getCurrentVelocity());
		SmartDashboard.putNumber("Wrist Current", this.getCurrentDraw());
	}

	@Override
	public double getCurrentVelocity() {
		double currentVelocity = this.WristFalcon.getVelocity().getValueAsDouble();
		return currentVelocity;
	}

	@Override
	public boolean isInPosition(double targetPosition) {
		double currentPosition = this.getCurrentPosition();
		double positionError = Math.abs(currentPosition - targetPosition);
		if (positionError < onTargetThreshold) {
			return true;
		} else {
			return false;
		}
	}
}   
