package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;
import frc.robot.util.Logger.Level;

public class Intake extends SubsystemBase {
  private static final Logger logger = new Logger(Intake.class.getName(), Level.WARNING, true);

  public static final double INTAKE_IN_SPEED = 1;
  public static final double INTAKE_OUT_SPEED = -0.3;

  private final Solenoid solenoid;
  private final WPI_VictorSPX victor;

  private boolean isExtended = false;

  public Intake() {
    logger.detail("constructor");
    solenoid = new Solenoid(Constants.PCM_NODE, Constants.INTAKE_SOLENOID);
    victor = MotorFactory.makeVictor(Constants.INTAKE_VICTOR, "Intake Victor");
  }


  public void extend() {
    if (isExtended) {
      return;
    }
    solenoid.set(true);
    isExtended = true;
    logger.dashboard("intake", "extended");
  }

  public void retract() {
    if (!isExtended) {
      return;
    }
    solenoid.set(false);
    isExtended = false;
    logger.dashboard("intake", "retracted");
  }

  public void on() {
    victor.set(INTAKE_IN_SPEED);
    logger.info("intake speed: %f", INTAKE_IN_SPEED);
  }

  public void reverse() {
    victor.set(INTAKE_OUT_SPEED);
    logger.info("intake speed: %f", INTAKE_OUT_SPEED);
  }

  public void off() {
    victor.set(0);
    logger.info("intake stopped");
  }

  public boolean isExtended() {
    return isExtended;
  }

  public void setIntakeFalse(){
    isExtended = false;
  }

  @Override
  public void periodic() {
  }
}
