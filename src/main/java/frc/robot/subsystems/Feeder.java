package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class Feeder extends SubsystemBase {
  private static final Logger logger = new Logger(Feeder.class.getName());

  public static final double FEEDER_IN_SPEED = 0;
  public static final double FEEDER_OUT_SPEED = 0;

  private final WPI_VictorSPX horizontalVictor;
  private final WPI_VictorSPX verticalVictor;

  public Feeder() {
    logger.detail("constructor");
    horizontalVictor = MotorFactory.makeVictor(Constants.FEEDER_HORIZONTAL_VICTOR, "Feeder Horizontal Victor");
    verticalVictor = MotorFactory.makeVictor(Constants.FEEDER_VERTICAL_VICTOR, "Feeder Vertical Victor");
  }

  // TODO: double check positive and negative values for forward/backwards
  public void allOn() {
    horizontalOn();
    verticalOn();
  }

  public void horizontalOn() {
    setHorizontalSpeed(FEEDER_IN_SPEED);
  }

  public void verticalOn() {
    setVerticalSpeed(FEEDER_IN_SPEED);
  }

  public void allReverse() {
    setHorizontalSpeed(FEEDER_OUT_SPEED);
    setVerticalSpeed(FEEDER_OUT_SPEED);
  }

  public void allOff() {
    horizontalOff();
    verticalOff();
  }

  public void horizontalOff() {
    setHorizontalSpeed(0);
  }

  public void verticalOff() {
    setVerticalSpeed(0);
  }

  private void setHorizontalSpeed(double speed) {
    horizontalVictor.set(speed);
    logger.dashboard("feeder horizontal speed", speed);
  }

  private void setVerticalSpeed(double speed) {
    verticalVictor.set(speed);
    logger.dashboard("feeder vertical speed", speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
