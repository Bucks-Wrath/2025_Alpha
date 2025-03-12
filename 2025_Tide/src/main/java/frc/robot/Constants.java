package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  public static class Algae{
    public static class Intake{
      public static class Processor{
        public static final double IntakeSpeed = 0.8;
        public static final double HoldSpeed = 0.04;
        public static final double Pullpeed = 0.2;
        public static final double WristDelay = 0.45;

        
        public static class L2 {
          public static final double ElevatorPosition = 16.0;
          public static final double WristPosition = -15.9;
        
        }

        public static class L3 {
          public static final double ElevatorPosition = 30.1;
          public static final double WristPosition = -12.8;
        }

        public static class Floor {
          public static final double WristPosition = -31.3;
        }
      }
      public static class Barge{
        public static final double IntakeSpeed = -0.8;
        public static final double HoldSpeed = -0.2;
        public static final double PullSpeed = -0.2;
        public static final double WristDelay = 0.45;


        public static class L2 {
          public static final double ElevatorPosition = 3.8;   // 3.4  
          public static final double WristPosition = -14.4;      
        
        }

        public static class L3 {
          public static final double ElevatorPosition = 18.2;  // 17.8
          public static final double WristPosition = -12.7;
        }

        public static class Floor {
          public static final double WristPosition = -31.3;
        }

      }
    }

    public static class Shoot {
      public static class Processor {
          public static final double ShooterSpeed = -1;
          public static final double WristPosition = -10.2;
      }

      public static class Barge {
        public static final double ShooterSpeed = 1;
        public static final double ElevatorPosition = 52.3;
        public static final double WristPosition = 0;
      }
    }
  }

  public static class Coral { 
    public static class Shoot {
      public static class Default {
        public static final double WristPosition = -9;
        public static final double ShooterSpeed = 0.3;
        public static final double AutoDelay = 0.125; // This is time it takes to shoot coral
      }

      public static class L1 {
        public static final double ElevatorPosition = 4.4;
        public static final double ShooterMotorOneSpeed = 1;
        public static final double ShooterMotorTwoSpeed = 0;

      }
      public static class L2 {
        public static final double ElevatorPosition = 13.2; 

      }
      public static class L3 {
        public static final double ElevatorPosition = 27.3;

      }
      public static class L4 {
        public static final double ElevatorPosition = 50.5;
        public static final double WristPosition = -16.4;
        public static final double WristDelay = 0.45;
        public static final double ShooterSpeed = 1; 

      }
    }
  }
  
}
