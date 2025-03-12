package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.dashboard.AutoScoreLeftDashboard;
import frc.robot.dashboard.AutoScoreRightDashboard;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  public static RobotContainer m_robotContainer;
  public static UsbCamera intakeCamera;

  public Robot() {
    m_robotContainer = new RobotContainer();
    intakeCamera = CameraServer.startAutomaticCapture("Intake", 0);
    intakeCamera.setResolution(160,120);
    intakeCamera.setFPS(30);
  }

  @Override
  public void robotPeriodic() {
    RobotContainer.coralIntake.updateDashboard();
    RobotContainer.elevator.updateDashboard();
    RobotContainer.wrist.updateDashboard();
    RobotContainer.ramp.updateDashboard();
    RobotContainer.climber.updateDashboard();
    RobotContainer.leftLimelight.updateDashboard();
    RobotContainer.rightLimelight.updateDashboard();
    RobotContainer.algaeIntake.updateDashboard();

    AutoScoreLeftDashboard.syncDashboard();
    AutoScoreRightDashboard.syncDashboard();
    CommandScheduler.getInstance().run(); 
  }

  @Override
  public void disabledInit() {
    RobotContainer.candleSubsystem.setAnimate("Rainbow");
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  @Override
  public void simulationPeriodic() {}
}
