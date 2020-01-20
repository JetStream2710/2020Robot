package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class Climb extends SubsystemBase {

  private static final Logger logger = new Logger(Climb.class.getName());

  private static final double EXTEND = 0.4;
  private static final double RETRACT = 0.4;
  private static final double MOVE = 0.4;

  private WPI_TalonSRX extendTalon;
  private WPI_TalonSRX retractTalon;
  private WPI_VictorSPX horizontalVictor;

  public Climb() {
    super();

    logger.detail("constructor");

    extendTalon = MotorFactory.makeTalon(Constants.CLIMB_EXTEND_TALON, "extendTalon");
    retractTalon = MotorFactory.makeTalon(Constants.CLIMB_RETRACT_TALON, "retractTalon");
    horizontalVictor = MotorFactory.makeVictor(Constants.CLIMB_HORIZONTAL_VICTOR, "horizontalVictor");
  }

  public void extend(){
    logger.info("extend");
    extendTalon.set(EXTEND);
  }

  public void retract(){
    logger.info("retract");
    retractTalon.set(RETRACT);
  }

  public void moveLeft(){
    logger.info("move left");
    horizontalVictor.set(MOVE);
  }

  public void moveRight(){
    logger.info("move right");
    horizontalVictor.set(-MOVE);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
