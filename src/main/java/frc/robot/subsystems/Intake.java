package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;
import frc.robot.util.Logger.Level;

public class Intake extends SubsystemBase {
  private static final Logger logger = new Logger(Intake.class.getName(), Level.DETAIL, true);

  public static final double INTAKE_IN_SPEED = -1;
  public static final double INTAKE_OUT_SPEED = 0.3;

  public static boolean isRaised = false;

  private final Solenoid solenoid;

  private final WPI_VictorSPX victor;

  public Intake() {
    logger.detail("constructor");
    solenoid = new Solenoid(Constants.PCM_NODE, Constants.INTAKE_SOLENOID);
    victor = MotorFactory.makeVictor(Constants.INTAKE_VICTOR, "Intake Victor");
  }


  // TODO: verify solenoid values
  public void raise() {
    solenoid.set(true);
    isRaised = true;
    logger.dashboard("intake", "raised");
  }

  public void lower() {
    solenoid.set(false);
    isRaised = false;
    logger.dashboard("intake", "lowered");
  }


  public void on() {
    victor.set(INTAKE_IN_SPEED);
    logger.dashboard("intake speed", INTAKE_IN_SPEED);
  }

  public void reverse() {
    victor.set(INTAKE_OUT_SPEED);
    logger.dashboard("intake speed", INTAKE_OUT_SPEED);
  }

  public void off() {
    victor.set(0);
    logger.dashboard("intake speed", 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
