package frc.robot.commands.swerve;

import frc.robot.RobotContainer;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.math.controller.PIDController;
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

    private final PIDController angleController = new PIDController(0.1, 0, 0.00001); // needs to be tuned
    private final PIDController strafeController = new PIDController(0.01, 0, 0.00001);
    private final PIDController distanceController = new PIDController(0.015, 0, 0.001);

    private double targetAngle = 0;
    private double targetStrafe = -13.5;
    private double targetArea = 23.9; // needs to be tuned

    public AutoScoreLeft(CommandSwerveDrivetrain drivetrain, SwerveRequest.RobotCentric visionDrive) {
        this.drivetrain = drivetrain;
        this.visionDrive = visionDrive;
        addRequirements(drivetrain);
        addRequirements(RobotContainer.rightLimelight);
    }

    public void initialize() {
        tx = RobotContainer.rightLimelight.getX();
        ta = RobotContainer.rightLimelight.getArea();
        tAng = RobotContainer.rightLimelight.gettAng();
        tv =  RobotContainer.rightLimelight.ifValidTag();

        angleController.setTolerance(2);  // needs to be tuned
        strafeController.setTolerance(0.25);
        distanceController.setTolerance(0.25);
    }
    
    @Override
    public void execute() {
        // find target location
        tx = RobotContainer.rightLimelight.getX();
        ta = RobotContainer.rightLimelight.getArea();
        tAng = RobotContainer.rightLimelight.gettAng();
        tv =  RobotContainer.rightLimelight.ifValidTag();
         
        // Uses PID to point at target
        rotationVal = angleController.calculate(tAng, targetAngle);
        strafeVal = strafeController.calculate(tx, targetStrafe);
        distanceVal = distanceController.calculate(ta, targetArea);

        if(distanceController.atSetpoint())
            distanceVal = 0;
        if (strafeController.atSetpoint())
            strafeVal = 0;
        if (angleController.atSetpoint())
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