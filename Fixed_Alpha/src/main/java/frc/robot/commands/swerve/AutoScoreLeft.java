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
    private double rotationVal;
    private double strafeVal;
    private double distanceVal;

    private final PIDController angleController = new PIDController(0.0065, 0, 0.0001); // needs to be tuned
    private final PIDController strafeController = new PIDController(0.0065, 0, 0.0001);
    private final PIDController distanceController = new PIDController(0.0065, 0, 0.0001);

    private double targetAngle = 0;
    private double targetStrafe = 0;
    private double targetArea = 40; // needs to be tuned

    public AutoScoreLeft(CommandSwerveDrivetrain drivetrain, SwerveRequest.RobotCentric visionDrive) {
        this.drivetrain = drivetrain;
        this.visionDrive = visionDrive;
        addRequirements(drivetrain);
        addRequirements(RobotContainer.rightLimelight);
    }

    public void initialize() {
        tx = RobotContainer.rightLimelight.getX();
        ta = RobotContainer.rightLimelight.getArea();
        angleController.setTolerance(0.05);  // needs to be tuned
        strafeController.setTolerance(0.05);
        distanceController.setTolerance(0.05);
    }
    
    @Override
    public void execute() {
        // find target location
        tx = RobotContainer.rightLimelight.getX();
        ta = RobotContainer.rightLimelight.getArea();
 
        // Uses PID to point at target
        rotationVal = angleController.calculate(tx,targetAngle);
        strafeVal = strafeController.calculate(tx, targetStrafe);
        distanceVal = distanceController.calculate(ta, targetArea);

        /* Drive */
        drivetrain.setControl(
                visionDrive.withVelocityX(distanceVal * 5.2) // Drive forward with negative Y (forward)
                    .withVelocityY(strafeVal * 5.2) // Drive left with negative X (left)
                    .withRotationalRate(rotationVal * 0.75) // Drive counterclockwise with negative X (left)
        );
    }
}