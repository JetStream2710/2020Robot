package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class Feeder extends SubsystemBase {
  private static final Logger logger = new Logger(Feeder.class.getName());

  private static final double FEEDER_SPEED = -0.5;
  private static final double TRIGGER_SPEED = -1;

  private final WPI_VictorSPX leftVictor;
  private final WPI_VictorSPX rightVictor;
  private final WPI_VictorSPX trigger;

  private final SpeedControllerGroup group;

  private boolean isFeederOn;

  public Feeder() {
    logger.detail("constructor");
    leftVictor = MotorFactory.makeVictor(Constants.FEEDER_LEFT_VICTOR, "Feeder Left Victor");
    rightVictor = MotorFactory.makeVictor(Constants.FEEDER_RIGHT_VICTOR, "Feeder Right Victor");
    trigger = MotorFactory.makeVictor(Constants.SHOOTER_TRIGGER_VICTOR, "triggerVictor");

    group = new SpeedControllerGroup(leftVictor, rightVictor);
    isFeederOn = false;
  }

  public void allOn(){
    feederOn();
    triggerOn();
  }

  public void allOff(){
    feederOff();
    triggerOff();
  }

  public void feederOn(){
    group.set(FEEDER_SPEED);
    isFeederOn = true;
  }

  public void feederOff(){
    group.set(0);
    isFeederOn = false;
  }
  
  public void feederReverse(){
    // This must not change isFeederOn.
    group.set(-FEEDER_SPEED);
  }

  public boolean isFeederOn() {
    return isFeederOn;
  }

  public void setSpeed(double speed){
    group.set(speed);
  }

  public void triggerOn() {
    trigger.set(TRIGGER_SPEED);
  }

  public void triggerOff() {
    trigger.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
