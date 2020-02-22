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

//  private final Solenoid solenoidOn;
//  private final Solenoid solenoidOff;

  private final WPI_VictorSPX victor;

  public Intake() {
    logger.detail("constructor");
//    solenoidOn = new Solenoid(Constants.PCM_NODE, Constants.INTAKE_SOLENOID_ON);
//    solenoidOff = new Solenoid(Constants.PCM_NODE, Constants.INTAKE_SOLENOID_OFF);
    victor = MotorFactory.makeVictor(Constants.INTAKE_VICTOR, "Intake Victor");
  }

  /**
  // TODO: verify solenoid values
  public void raise() {
    solenoidOn.set(false);
    solenoidOff.set(true);
    logger.dashboard("intake", "raised");
  }

  public void lower() {
    solenoidOn.set(true);
    solenoidOff.set(false);
    logger.dashboard("intake", "lowered");
  }
*/

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
