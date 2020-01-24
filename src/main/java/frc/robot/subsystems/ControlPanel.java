package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class ControlPanel extends SubsystemBase {
  private static final Logger logger = new Logger(ControlPanel.class.getName());

  private static final double TURN = 0.4;

  private final WPI_TalonSRX turnTalon;

  private final Solenoid solenoidOn;
  private final Solenoid solenoidOff;

  private final Encoder enc;

  public ControlPanel() {
    logger.detail("constructor");
    solenoidOn = new Solenoid(Constants.PCM_NODE, Constants.INTAKE_SOLENOID_ON);
    solenoidOff = new Solenoid(Constants.PCM_NODE, Constants.INTAKE_SOLENOID_OFF); 
    turnTalon = MotorFactory.makeTalon(Constants.CONTROL_PANEL_TALON , "Control Panel Turn Talon");
    enc = new Encoder(Constants.CONTROL_PANEL_ENCODERA, Constants.CONTROL_PANEL_ENCODERB, false, Encoder.EncodingType.k4X);
    enc.reset();
    enc.setDistancePerPulse(6*Math.PI/1024);
  }

  public void turn() {
    turnTalon.set(TURN);
    logger.dashboard("control panel turn speed", TURN);
  }

  public void turnStop() {
    turnTalon.set(0);
    logger.dashboard("control panel turn speed", 0);
  }

  // TODO: check solenoid values
  public void extend() {
    solenoidOn.set(true);
    solenoidOff.set(false);
    logger.info("control panel extended", "yes");
  }

  public void retract() {
    solenoidOn.set(false);
    solenoidOff.set(true);
    logger.info("control panel extended", "no");
  }

  public int getPosition() {
    int position = turnTalon.getSelectedSensorPosition();
    logger.detail("position: %d", position);
    return position;
  }

  public int getCount(){
    int count = enc.get();
    logger.dashboard("climb encoder count: %d" , count);
    return count;
  }

  public int getRaw(){
    int raw = enc.getRaw();
    logger.dashboard("climb encoder raw: %d" , raw);
    return raw;
  }

  public double getRate(){
    double rate = enc.getRate();
    logger.dashboard("climb encoder rate: %f" , rate);
    return rate;
  }

  public double getDistance(){
    double distance = enc.getDistance();
    logger.dashboard("climb encoder distance: %f" , distance);
    return distance;
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
