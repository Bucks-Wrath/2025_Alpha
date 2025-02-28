package frc.robot.commands.swerve;

import frc.robot.RobotContainer;
import frc.robot.config.AutoScoreLeftConfig;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.RightLimelight;

import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.wpilibj2.command.Command;

public class AutoScoreLeft extends Command {    
    private CommandSwerveDrivetrain drivetrain; 
    private SwerveRequest.RobotCentric visionDrive;   

    private double tx;
    private double ta;
    private double tAng;
    private double rotationVal;
    private double strafeVal;
    private double distanceVal;

    private RightLimelight limelight; 

    public AutoScoreLeft(CommandSwerveDrivetrain drivetrain, SwerveRequest.RobotCentric visionDrive) {
        this.drivetrain = drivetrain;
        this.visionDrive = visionDrive;
        this.limelight = RobotContainer.rightLimelight;
        addRequirements(drivetrain);
        addRequirements(RobotContainer.rightLimelight);
    }

    public void initialize() {
        limelight.strafeController.setSetpoint(AutoScoreLeftConfig.StrafeTarget);
        limelight.distanceController.setSetpoint(AutoScoreLeftConfig.DistanceTarget);
        limelight.angleController.setSetpoint(AutoScoreLeftConfig.AngleTarget);
    }
    
    @Override
    public void execute() {
        // If we don't see a target, don't do anything
        if (!limelight.ifValidTag()) return;

        // find target location
        tx = limelight.gettx();
        ta = limelight.gettz();
        tAng = limelight.gettAng();
         
        // Uses PID to point at target
        rotationVal = -limelight.angleController.calculate(tAng, AutoScoreLeftConfig.AngleTarget);
        strafeVal = limelight.strafeController.calculate(tx, AutoScoreLeftConfig.StrafeTarget);
        distanceVal = -limelight.distanceController.calculate(ta, AutoScoreLeftConfig.DistanceTarget);

        if(limelight.distanceController.atSetpoint())
            distanceVal = 0;
        if (limelight.strafeController.atSetpoint())
            strafeVal = 0;
        if (limelight.angleController.atSetpoint())
            rotationVal = 0;

        /* Drive */
        drivetrain.setControl(
                visionDrive.withVelocityX(distanceVal * 5.2) // Drive forward with negative Y (forward)
                        .withVelocityY(strafeVal * 5.2) // Drive left with negative X (left)
                        .withRotationalRate(-rotationVal * 0.75) // Drive counterclockwise with negative X (left)
        );
    }

    // Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
        // If all 3 PIDs are at their target, we're done
		return !limelight.ifValidTag() || (limelight.distanceController.atSetpoint() 
            && limelight.strafeController.atSetpoint() 
            && limelight.angleController.atSetpoint());
	}

	// Called once after isFinished returns true
	protected void end() {
        RobotContainer.candleSubsystem.setAnimate("Rainbow");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
        end(); 
	}

}   