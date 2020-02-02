package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class Shooter extends SubsystemBase {
  private static final Logger logger = new Logger(Shooter.class.getName());

  public static final double SHOOTER_SPEED = 0.8;

  private final WPI_TalonFX leftTalon;
  private final WPI_TalonFX rightTalon;

  private static final int MAX_PERIOD_COUNT = 10;

  private int periodicIndex;
  private long[] periodicTimestampArray = new long[MAX_PERIOD_COUNT];
  private int[] leftSidePositionArray = new int[MAX_PERIOD_COUNT];
  private int[] rightSidePositionArray = new int[MAX_PERIOD_COUNT];

  public Shooter() {
    logger.detail("constructor");

    leftTalon = MotorFactory.makeTalonFX(Constants.SHOOTER_LEFT_TALON, "leftTalon");
    rightTalon = MotorFactory.makeTalonFX(Constants.SHOOTER_RIGHT_TALON, "rightTalon");
  }

  // TODO: set w specific encoder value?
  public void on() {
    leftTalon.set(-SHOOTER_SPEED);
    rightTalon.set(SHOOTER_SPEED);
    logger.dashboard("shooter", "on");    
  }

  public void off() {
    leftTalon.set(0);
    rightTalon.set(0);
    logger.dashboard("shooter", "off");
  }

  public double speed() {
    // TODO: this should report the actual speed, not the last set speed
    double speed = (-leftTalon.get() + rightTalon.get()) / 2;
    logger.dashboard("shooter speed", speed);
    return speed;
  }

  public int getLeftPosition() {
    return -1 * leftTalon.getSelectedSensorPosition();
  }

  public int getRightPosition() {
    return rightTalon.getSelectedSensorPosition();
  }

  @Override
  public void periodic() {
    periodicIndex++;
    if (periodicIndex >= MAX_PERIOD_COUNT) {
      periodicIndex = 0;
    }
    periodicTimestampArray[periodicIndex] = System.nanoTime();
    leftSidePositionArray[periodicIndex] = getLeftPosition();
    rightSidePositionArray[periodicIndex] = getRightPosition();

    logger.info("shooter speed: %f   speed2: %f", speed(), getSpeed());
  }

  public double getSpeed() {
    // Currently set to take the oldest index
    int lastIndex = periodicIndex + 1;
    if (lastIndex >= MAX_PERIOD_COUNT) {
      lastIndex = 0;
    }
    long period = (periodicTimestampArray[periodicIndex] - periodicTimestampArray[lastIndex]) / 1000;
    if (period == 0) {
      period = 1000;  // default to 1 ms
    }
    /*
    System.out.println("period: " + period + "  idx: " + periodicIndex + "  left: " +
    (leftSidePositionArray[periodicIndex] - leftSidePositionArray[lastIndex]) + "  right: " +
    (rightSidePositionArray[periodicIndex] - rightSidePositionArray[lastIndex]));
    */
    
    // (1000 * (left-side-diff + right-side-diff)) / (2 * time-diff)
    // Returned units are encoder units per milliseconds
    return 500 * ((leftSidePositionArray[periodicIndex] - leftSidePositionArray[lastIndex]) +
        (rightSidePositionArray[periodicIndex] - rightSidePositionArray[lastIndex])) / period;
  }
}
