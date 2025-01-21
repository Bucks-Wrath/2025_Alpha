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

public class Climber extends SubsystemBase implements IPositionControlledSubsystem {

	private boolean isHoldingPosition = false;

    // Set Different Heights
	private double homePosition = 0;
	private double maxUpTravelPosition = 100;

	public double upPositionLimit = maxUpTravelPosition;
	public double downPositionLimit = 0;
	private double targetPosition = 0;
    private MotionMagicDutyCycle targetPositionDutyCycle = new MotionMagicDutyCycle(0);
	private double feedForward = 0.0;
	public double shooterAddValue;

	private final static double onTargetThreshold = 0.1;
		
	private TalonFX PivotFalcon = new TalonFX(DeviceIds.Climber.PivotMotorId, "rio");

    private TalonFXConfiguration PivotFXConfig = new TalonFXConfiguration();

	private TalonFX ClimberFalcon = new TalonFX(DeviceIds.Climber.ClimberMotorId, "rio");

    private TalonFXConfiguration ClimberFXConfig = new TalonFXConfiguration();


	public Climber() {
		this.configurePivotMotor();
		this.configureClimberMotor();
    }

	public void motionMagicControl() {
		this.manageMotion(targetPosition);
        targetPositionDutyCycle.withPosition(targetPosition);
        targetPositionDutyCycle.withFeedForward(feedForward);
		this.PivotFalcon.setControl(targetPositionDutyCycle);
	}

	private void configurePivotMotor() {

		// Clear Sticky Faults
		this.PivotFalcon.clearStickyFaults();
		
        /** Climber Motor Configuration */
        /* Motor Inverts and Neutral Mode */
		PivotFXConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        PivotFXConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        /* Current Limiting */
        //ClimberFXConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        //ClimberFXConfig.CurrentLimits.SupplyCurrentLimit = 35;
        //ClimberFXConfig.CurrentLimits.SupplyCurrentThreshold = 60;
        //ClimberFXConfig.CurrentLimits.SupplyTimeThreshold = 0.05;

		PivotFXConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        PivotFXConfig.CurrentLimits.StatorCurrentLimit = 35;

        /* PID Config */
        PivotFXConfig.Slot0.kP = 0.2;
        PivotFXConfig.Slot0.kI = 0;
        PivotFXConfig.Slot0.kD = 0.01;

        /* Open and Closed Loop Ramping */
        PivotFXConfig.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 0.25;
        PivotFXConfig.OpenLoopRamps.VoltageOpenLoopRampPeriod = 0.25;

        PivotFXConfig.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0;
        PivotFXConfig.ClosedLoopRamps.VoltageClosedLoopRampPeriod = 0;

        //Config Acceleration and Velocity
        PivotFXConfig.MotionMagic.withMotionMagicAcceleration(300);
        PivotFXConfig.MotionMagic.withMotionMagicCruiseVelocity(300);

        // Config Motor
        PivotFalcon.getConfigurator().apply(PivotFXConfig);
        PivotFalcon.getConfigurator().setPosition(0.0);
	}

	private void configureClimberMotor() {

		// Clear Sticky Faults
		this.ClimberFalcon.clearStickyFaults();
		
        /** Climber Motor Configuration */
        /* Motor Inverts and Neutral Mode */
		ClimberFXConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        ClimberFXConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        /* Current Limiting */
        //ClimberFXConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        //ClimberFXConfig.CurrentLimits.SupplyCurrentLimit = 35;
        //ClimberFXConfig.CurrentLimits.SupplyCurrentThreshold = 60;
        //ClimberFXConfig.CurrentLimits.SupplyTimeThreshold = 0.05;

		ClimberFXConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        ClimberFXConfig.CurrentLimits.StatorCurrentLimit = 35;

        /* PID Config */
        ClimberFXConfig.Slot0.kP = 0.2;
        ClimberFXConfig.Slot0.kI = 0;
        ClimberFXConfig.Slot0.kD = 0.01;

        /* Open and Closed Loop Ramping */
        ClimberFXConfig.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 0.25;
        ClimberFXConfig.OpenLoopRamps.VoltageOpenLoopRampPeriod = 0.25;

        ClimberFXConfig.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0;
        ClimberFXConfig.ClosedLoopRamps.VoltageClosedLoopRampPeriod = 0;

        //Config Acceleration and Velocity
        ClimberFXConfig.MotionMagic.withMotionMagicAcceleration(300);
        ClimberFXConfig.MotionMagic.withMotionMagicCruiseVelocity(300);

        // Config Motor
        ClimberFalcon.getConfigurator().apply(ClimberFXConfig);
        ClimberFalcon.getConfigurator().setPosition(0.0);
	}
	
	public double getCurrentPosition() {
		return this.PivotFalcon.getRotorPosition().getValueAsDouble();
	}

	public double getCurrentDraw() {
		return this.PivotFalcon.getSupplyCurrent().getValueAsDouble();
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
		if (isValidPosition(newTargetPosition)) {		// && isClimberSafe(newTargetPosition) check for other subsystems
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

	public void resetClimberEncoder() {
        try {
			PivotFalcon.getConfigurator().setPosition(0.0);
        }
        catch (Exception e) {
            DriverStation.reportError("Climber.resetClimberEncoders exception.  You're Screwed! : " + e.toString(), false);
        }
	}

	public double PivotJoystickInput(){
		double value = 0;
		value = -Robot.m_robotContainer.getOperatorRightStickY();
		return value;
	}

	public double ClimberJoystickInput(){
		double value = 0;
		value = Robot.m_robotContainer.getOperatorLeftStickY();
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

	public void setClimberSpeed(double speed) {
        this.ClimberFalcon.set(speed);
	}

	public void updateDashboard() {
		SmartDashboard.putNumber("Climber Position", this.getCurrentPosition());
		SmartDashboard.putNumber("Climber Target Position", this.getTargetPosition());
		SmartDashboard.putNumber("Climber Position Error", this.getPositionError());
		SmartDashboard.putNumber("Climber Velocity", this.getCurrentVelocity());
		SmartDashboard.putNumber("Climber Current", this.getCurrentDraw());
	}

	@Override
	public double getCurrentVelocity() {
		double currentVelocity = this.PivotFalcon.getVelocity().getValueAsDouble();
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
