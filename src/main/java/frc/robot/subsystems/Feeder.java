package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;

public class Feeder extends SubsystemBase {

  private Logger logger = new Logger(Feeder.class.getName());

  private WPI_VictorSPX horizontalVictor;
  private WPI_VictorSPX verticalVictor;

  public Feeder() {
    super();

    logger.detail("constructor");

    horizontalVictor = new WPI_VictorSPX(Constants.FEEDER_HORIZONTAL_VICTOR);
    verticalVictor = new WPI_VictorSPX(Constants.FEEDER_VERTICAl_VICTOR);

    horizontalVictor.setSafetyEnabled(false);
    verticalVictor.setSafetyEnabled(false);

    horizontalVictor.setNeutralMode(NeutralMode.Brake);
    verticalVictor.setNeutralMode(NeutralMode.Brake);

    horizontalVictor.configVoltageCompSaturation(12);
    verticalVictor.configVoltageCompSaturation(12);

    horizontalVictor.enableVoltageCompensation(true);
    verticalVictor.enableVoltageCompensation(true);
  }

  // TODO: double check positive and negative values for forward/backwards
  public void onAll(){
    logger.info("onAll speed: " + Constants.FEEDER_IN_SPEED);
    horizontalVictor.set(Constants.FEEDER_IN_SPEED);
    verticalVictor.set(Constants.FEEDER_IN_SPEED);
  }

  public void onHorizontal(){
    logger.info("onHorizontal speed: " + Constants.FEEDER_IN_SPEED);
    horizontalVictor.set(Constants.FEEDER_IN_SPEED);
  }

  public void onVertical(){
    logger.info("onVertical speed: " + Constants.FEEDER_IN_SPEED);
    verticalVictor.set(Constants.FEEDER_IN_SPEED);
  }

  public void reverseAll(){
    logger.info("reverseAll speed: " + Constants.FEEDER_OUT_SPEED);
    horizontalVictor.set(Constants.FEEDER_OUT_SPEED);
    verticalVictor.set(Constants.FEEDER_OUT_SPEED);
  }

  public void offAll(){
    logger.info("offAll");
    horizontalVictor.set(0);
    verticalVictor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
