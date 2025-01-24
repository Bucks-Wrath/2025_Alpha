package frc.robot.commands.swerve;

import frc.robot.RobotContainer;
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
    private boolean tv;
    private double rotationVal;
    private double strafeVal;
    private double distanceVal;

    private double targetAngle = 0;
    private double targetStrafe = -13.5;
    private double targetArea = 23.9; // needs to be tuned

    private RightLimelight limelight; 

    public AutoScoreLeft(CommandSwerveDrivetrain drivetrain, SwerveRequest.RobotCentric visionDrive) {
        this.drivetrain = drivetrain;
        this.visionDrive = visionDrive;
        this.limelight = RobotContainer.rightLimelight;
        addRequirements(drivetrain);
        addRequirements(RobotContainer.rightLimelight);
    }

    public void initialize() {
        tx = limelight.getX();
        ta = limelight.getArea();
        tAng = limelight.gettAng();
        tv =  limelight.ifValidTag();
    }
    
    @Override
    public void execute() {
        // find target location
        tx = limelight.getX();
        ta = limelight.getArea();
        tAng = limelight.gettAng();
        tv =  limelight.ifValidTag();
         
        // Uses PID to point at target
        rotationVal = limelight.angleController.calculate(tAng, targetAngle);
        strafeVal = limelight.strafeController.calculate(tx, targetStrafe);
        distanceVal = limelight.distanceController.calculate(ta, targetArea);

        if(limelight.distanceController.atSetpoint())
            distanceVal = 0;
        if (limelight.strafeController.atSetpoint())
            strafeVal = 0;
        if (limelight.angleController.atSetpoint())
            rotationVal = 0;

        /* Drive */
        if(tv == true) {
            drivetrain.setControl(
                    visionDrive.withVelocityX(distanceVal * 5.2) // Drive forward with negative Y (forward)
                        .withVelocityY(strafeVal * 5.2) // Drive left with negative X (left)
                        .withRotationalRate(-rotationVal * 0.75) // Drive counterclockwise with negative X (left)

            );
        }
    }

    // Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
        end(); 
	}

}   