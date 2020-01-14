package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;

public class Intake extends SubsystemBase {

  private Logger logger = new Logger(Intake.class.getName());

  private Solenoid solenoidOn;
  private Solenoid solenoidOff;

  private WPI_VictorSPX victor;

  public Intake() {
    super();

    logger.detail("constructor");

    solenoidOn = new Solenoid(Constants.PCM_NODE, Constants.INTAKE_SOLENOID_ON);
    solenoidOff = new Solenoid(Constants.PCM_NODE, Constants.INTAKE_SOLENOID_OFF);

    victor = new WPI_VictorSPX(Constants.INTAKE_VICTOR);
    victor.setSafetyEnabled(false);
    victor.setNeutralMode(NeutralMode.Brake);
    victor.configVoltageCompSaturation(12);
    victor.enableVoltageCompensation(true);
  }

  // TODO: verify solenoid values
  public void intakeRaise(){
    logger.info("intakeRaise called");
    solenoidOn.set(false);
    solenoidOff.set(true);
  }

  public void intakeLower(){
    logger.info("intakeLower called");
    solenoidOn.set(true);
    solenoidOff.set(false);
  }

  public void intakeIn(){
    logger.info("intakeIn speed: " + Constants.INTAKE_IN_SPEED);
    victor.set(Constants.INTAKE_IN_SPEED);
  }

  public void intakeOut(){
    logger.info("intakeOut speed: " + Constants.INTAKE_OUT_SPEED);
    victor.set(Constants.INTAKE_OUT_SPEED);
  }

  public void intakeStop(){
    logger.info("intakeOut");
    victor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
