package frc.robot.subsystems;

import frc.lib.models.*;
import frc.robot.Constants;
import frc.robot.DeviceIds;
import frc.robot.Robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase implements IPositionControlledSubsystem {

	private boolean isHoldingPosition = false;

    // Set Different Heights
	private double homePosition = 0;
	private double maxUpTravelPosition = Constants.Maximums.MaxElevatorHeight;

	public double upPositionLimit = maxUpTravelPosition;
	public double downPositionLimit = 0;
	private double targetPosition = 0;
    private MotionMagicDutyCycle targetPositionDutyCycle = new MotionMagicDutyCycle(0);
	private double feedForward = 0.0;
	public double shooterAddValue;

	private final static double onTargetThreshold = 0.25;
		
	private TalonFX ElevatorFalcon = new TalonFX(DeviceIds.Elevator.LeadMotorId, "canivore");
	private TalonFX ElevatorFalconFollower = new TalonFX(DeviceIds.Elevator.FollowerMotorId, "canivore");

    private TalonFXConfiguration ElevatorFXConfig = new TalonFXConfiguration();

	public Elevator() {
		// Clear Sticky Faults
		this.ElevatorFalcon.clearStickyFaults();
		this.ElevatorFalconFollower.clearStickyFaults();
		
        // Set Followers
		this.ElevatorFalconFollower.setControl(new Follower(DeviceIds.Elevator.LeadMotorId, false));

        /** Shooter Motor Configuration */
        /* Motor Inverts and Neutral Mode */
		ElevatorFXConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        ElevatorFXConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        /* Current Limiting */
		ElevatorFXConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        ElevatorFXConfig.CurrentLimits.StatorCurrentLimit = 60;// 35 // 50

        /* PID Config */
        ElevatorFXConfig.Slot0.kP = 0.15; // 2
        ElevatorFXConfig.Slot0.kI = 0;
        ElevatorFXConfig.Slot0.kD = 0.01; // 0.01 // 0.005

        /* Open and Closed Loop Ramping */
        ElevatorFXConfig.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 0.25;
        ElevatorFXConfig.OpenLoopRamps.VoltageOpenLoopRampPeriod = 0.25;

        ElevatorFXConfig.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0;
        ElevatorFXConfig.ClosedLoopRamps.VoltageClosedLoopRampPeriod = 0;

        //Config Acceleration and Velocity
        ElevatorFXConfig.MotionMagic.withMotionMagicAcceleration(450); //300
        ElevatorFXConfig.MotionMagic.withMotionMagicCruiseVelocity(450); //300

        // Config Motor
        ElevatorFalcon.getConfigurator().apply(ElevatorFXConfig);
        ElevatorFalcon.getConfigurator().setPosition(0.0);
		ElevatorFalconFollower.getConfigurator().setPosition(0);
	}

	public void motionMagicControl() {
		this.manageMotion(targetPosition);
        targetPositionDutyCycle.withPosition(targetPosition);
        targetPositionDutyCycle.withFeedForward(feedForward);
		this.ElevatorFalcon.setControl(targetPositionDutyCycle);
	}

	public double getCurrentPosition() {
		return this.ElevatorFalcon.getRotorPosition().getValueAsDouble();
	}

	public double getCurrentDraw() {
		return this.ElevatorFalcon.getSupplyCurrent().getValueAsDouble();
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
		if (isValidPosition(newTargetPosition)) {		// && isElevatorSafe(newTargetPosition) check for other subsystems
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

	public void resetElevatorEncoder() {
        try {
			ElevatorFalcon.getConfigurator().setPosition(0.0);
			ElevatorFalconFollower.getConfigurator().setPosition(0);
        }
        catch (Exception e) {
            DriverStation.reportError("Elevator.resetElevatorEncoders exception.  You're Screwed! : " + e.toString(), false);
        }
	}

	public double JoystickElevator(){
		double value = 0;
		value = -Robot.m_robotContainer.getOperatorLeftStickY();
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
		SmartDashboard.putNumber("Elevator Position", this.getCurrentPosition());
		SmartDashboard.putNumber("Elevator Target Position", this.getTargetPosition());
		SmartDashboard.putNumber("Elevator Position Error", this.getPositionError());
		SmartDashboard.putNumber("Elevator Velocity", this.getCurrentVelocity());
		SmartDashboard.putNumber("Elevator Current", this.getCurrentDraw());
	}

	@Override
	public double getCurrentVelocity() {
		double currentVelocity = this.ElevatorFalcon.getVelocity().getValueAsDouble();
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
