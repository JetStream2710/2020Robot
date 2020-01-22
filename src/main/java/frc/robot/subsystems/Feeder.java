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


  private WPI_VictorSPX horizontalVictor;
  private WPI_VictorSPX verticalVictor;

  public Feeder() {
    logger.detail("constructor");

    horizontalVictor = MotorFactory.makeVictor(Constants.FEEDER_HORIZONTAL_VICTOR, "horizontalVictor");
    verticalVictor = MotorFactory.makeVictor(Constants.FEEDER_VERTICAL_VICTOR, "verticalVictor");
  }

  // TODO: double check positive and negative values for forward/backwards
  public void allOn(){
    logger.info("allOn speed: " + FEEDER_IN_SPEED);
    horizontalVictor.set(FEEDER_IN_SPEED);
    verticalVictor.set(FEEDER_IN_SPEED);
  }

  public void horizontalOn(){
    logger.info("horizontalOn speed: " + FEEDER_IN_SPEED);
    horizontalVictor.set(FEEDER_IN_SPEED);
  }

  public void verticalOn(){
    logger.info("verticalOn speed: " + FEEDER_IN_SPEED);
    verticalVictor.set(FEEDER_IN_SPEED);
  }

  public void allReverse(){
    logger.info("allReverse speed: " + FEEDER_OUT_SPEED);
    horizontalVictor.set(FEEDER_OUT_SPEED);
    verticalVictor.set(FEEDER_OUT_SPEED);
  }

  public void horizontalOff(){
    logger.info("horizontalOff");
    horizontalVictor.set(0);
  }

  public void verticalOff(){
    logger.info("verticalOff");
    verticalVictor.set(0);
  }

  public void allOff(){
    logger.info("allOff");
    horizontalVictor.set(0);
    verticalVictor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
