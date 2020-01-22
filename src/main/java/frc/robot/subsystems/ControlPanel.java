package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class ControlPanel extends SubsystemBase {

  private static final Logger logger = new Logger(ControlPanel.class.getName());

  private static final double TURN = 0.4;

  private WPI_TalonSRX turnTalon;

  private Solenoid solenoidOn;
  private Solenoid solenoidOff;

  public ControlPanel() {
    logger.detail("constructor");

    solenoidOn = new Solenoid(Constants.PCM_NODE, Constants.INTAKE_SOLENOID_ON);
    solenoidOff = new Solenoid(Constants.PCM_NODE, Constants.INTAKE_SOLENOID_OFF); 

    turnTalon = MotorFactory.makeTalon(Constants.CONTROL_PANEL_TALON , "turnTalon");

  }

  public void turn(){
    logger.info("turn at " + TURN);
    turnTalon.set(TURN);
  }

  public void turnStop(){
    logger.info("turn stopped");
    turnTalon.set(0);
  }

  // TODO: check solenoid values
  public void extend(){
    logger.info("extend called");
    solenoidOn.set(true);
    solenoidOff.set(false);
  }

  public void retract(){
    logger.info("retract called");
    solenoidOn.set(false);
    solenoidOff.set(true);
  }

  public int getPosition(){
    return turnTalon.getSelectedSensorPosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
