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

public class Ramp extends SubsystemBase implements IPositionControlledSubsystem {

	private boolean isHoldingPosition = false;

    // Set Different Heights
	private double homePosition = 0;
	private double maxUpTravelPosition = 1;

	public double upPositionLimit = maxUpTravelPosition;
	public double downPositionLimit = 0;
	private double targetPosition = 0;
    private MotionMagicDutyCycle targetPositionDutyCycle = new MotionMagicDutyCycle(0);
	private double feedForward = 0.0;
	public double shooterAddValue;

	private final static double onTargetThreshold = 0.1;
		
	private TalonFX RampFalcon = new TalonFX(DeviceIds.Ramp.MotorId, "rio");

    private TalonFXConfiguration RampFXConfig = new TalonFXConfiguration();

	public Ramp() {
		// Clear Sticky Faults
		this.RampFalcon.clearStickyFaults();
		
        /** Ramp Motor Configuration */
        /* Motor Inverts and Neutral Mode */
		RampFXConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        RampFXConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        /* Current Limiting */
        //RampFXConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        //RampFXConfig.CurrentLimits.SupplyCurrentLimit = 35;
        //RampFXConfig.CurrentLimits.SupplyCurrentThreshold = 60;
        //RampFXConfig.CurrentLimits.SupplyTimeThreshold = 0.05;

		RampFXConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        RampFXConfig.CurrentLimits.StatorCurrentLimit = 35;

        /* PID Config */
        RampFXConfig.Slot0.kP = 0.2;
        RampFXConfig.Slot0.kI = 0;
        RampFXConfig.Slot0.kD = 0.01;

        /* Open and Closed Loop Ramping */
        RampFXConfig.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 0.25;
        RampFXConfig.OpenLoopRamps.VoltageOpenLoopRampPeriod = 0.25;

        RampFXConfig.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0;
        RampFXConfig.ClosedLoopRamps.VoltageClosedLoopRampPeriod = 0;

        //Config Acceleration and Velocity
        RampFXConfig.MotionMagic.withMotionMagicAcceleration(300);
        RampFXConfig.MotionMagic.withMotionMagicCruiseVelocity(300);

        // Config Motor
        RampFalcon.getConfigurator().apply(RampFXConfig);
        RampFalcon.getConfigurator().setPosition(0.0);
    }

	public void motionMagicControl() {
		this.manageMotion(targetPosition);
        targetPositionDutyCycle.withPosition(targetPosition);
        targetPositionDutyCycle.withFeedForward(feedForward);
		this.RampFalcon.setControl(targetPositionDutyCycle);
	}

	public double getCurrentPosition() {
		return this.RampFalcon.getRotorPosition().getValueAsDouble();
	}

	public double getCurrentDraw() {
		return this.RampFalcon.getSupplyCurrent().getValueAsDouble();
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
		if (isValidPosition(newTargetPosition)) {		// && isRampSafe(newTargetPosition) check for other subsystems
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

	public void resetRampEncoder() {
        try {
			RampFalcon.getConfigurator().setPosition(0.0);
        }
        catch (Exception e) {
            DriverStation.reportError("Ramp.resetRampEncoders exception.  You're Screwed! : " + e.toString(), false);
        }
	}

	public double JoystickRamp(){
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
		SmartDashboard.putNumber("Ramp Position", this.getCurrentPosition());
		SmartDashboard.putNumber("Ramp Target Position", this.getTargetPosition());
		SmartDashboard.putNumber("Ramp Position Error", this.getPositionError());
		SmartDashboard.putNumber("Ramp Velocity", this.getCurrentVelocity());
		SmartDashboard.putNumber("Ramp Current", this.getCurrentDraw());
	}

	@Override
	public double getCurrentVelocity() {
		double currentVelocity = this.RampFalcon.getVelocity().getValueAsDouble();
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
