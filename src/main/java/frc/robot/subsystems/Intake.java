package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class Intake extends SubsystemBase {

  private static final Logger logger = new Logger(Intake.class.getName());

  public static final double INTAKE_IN_SPEED = 0;
  public static final double INTAKE_OUT_SPEED = 0;

  private Solenoid solenoidOn;
  private Solenoid solenoidOff;

  private WPI_VictorSPX victor;

  public Intake() {
    logger.detail("constructor");

    solenoidOn = new Solenoid(Constants.PCM_NODE, Constants.INTAKE_SOLENOID_ON);
    solenoidOff = new Solenoid(Constants.PCM_NODE, Constants.INTAKE_SOLENOID_OFF);

    victor = MotorFactory.makeVictor(Constants.INTAKE_VICTOR, "victor");
  }

  // TODO: verify solenoid values
  public void raise(){
    logger.info("raise called");
    solenoidOn.set(false);
    solenoidOff.set(true);
  }

  public void lower(){
    logger.info("lower called");
    solenoidOn.set(true);
    solenoidOff.set(false);
  }

  public void on(){
    logger.info("in speed: " + INTAKE_IN_SPEED);
    victor.set(INTAKE_IN_SPEED);
  }

  public void reverse(){
    logger.info("out speed: " + INTAKE_OUT_SPEED);
    victor.set(INTAKE_OUT_SPEED);
  }

  public void off(){
    logger.info("stop");
    victor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
