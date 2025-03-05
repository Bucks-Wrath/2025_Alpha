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
        public static final double HoldSpeed = 0.1;
        public static final double WristDelay = 0.45;     // total guess

        
        public static class L2 {
          public static final double ElevatorPosition = 16.6;
          public static final double WristPosition = -5.3; //-15.8;
        
        }

        public static class L3 {
          public static final double ElevatorPosition = 31.7;
          public static final double WristPosition = -6.5; //-19.4;
        }

        public static class Floor {
          public static final double WristPosition = -11.7; //-35.2;
        }
      }
      public static class Barge{
        public static final double IntakeSpeed = -0.8;
        public static final double HoldSpeed = -0.1;
        public static final double WristDelay = 0.45;   // total guess


        public static class L2 {
          public static final double ElevatorPosition = 16.6;   // total guesses
          public static final double WristPosition = -5.3;      // total guesses
        
        }

        public static class L3 {
          public static final double ElevatorPosition = 31.7;   // total guesses
          public static final double WristPosition = -6.5;      // total guesses
        }

        public static class Floor {
          public static final double WristPosition = -11.7;     // total guesses
        }

      }
    }

    public static class Shoot {
      public static class Processor {
          public static final double ShooterSpeed = -1;
          public static final double WristPosition = -3.4; //-10.3;
      }

      public static class Barge {
        public static final double ShooterSpeed = 1;
        public static final double ElevatorPosition = 0;
        public static final double WristPosition = 0;
      }
    }
  }

  public static class Coral { 
    public static class Shoot {
      public static class Default {
        public static final double WristPosition = -3; //-9;
        public static final double ShooterSpeed = 0.3;
        public static final double AutoDelay = 0.25; // Use this to speed up auto.
      }

      public static class L1 {
        public static final double ElevatorPosition = 4.4;
        public static final double ShooterMotorOneSpeed = 1;
        public static final double ShooterMotorTwoSpeed = 0.1;

      }
      public static class L2 {
        public static final double ElevatorPosition = 13.2; 

      }
      public static class L3 {
        public static final double ElevatorPosition = 27.3;

      }
      public static class L4 {
        public static final double ElevatorPosition = 50.5;
        public static final double WristPosition = -5.5; //-16.4;
        public static final double WristDelay = 0.45;  // needs to be retuned
        public static final double ShooterSpeed = 1; 

      }
    }
  }
  
}
