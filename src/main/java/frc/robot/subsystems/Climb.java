package frc.robot.subsystems;

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

  private final WPI_TalonSRX extendTalon;
  private final WPI_TalonSRX retractTalon;
  private final WPI_VictorSPX horizontalVictor;

  public Climb() {
    logger.detail("constructor");
    extendTalon = MotorFactory.makeTalon(Constants.CLIMB_EXTEND_TALON, "extendTalon");
    retractTalon = MotorFactory.makeTalon(Constants.CLIMB_RETRACT_TALON, "retractTalon");
    horizontalVictor = MotorFactory.makeVictor(Constants.CLIMB_HORIZONTAL_VICTOR, "horizontalVictor");
  }

  public void extend(){
    logger.info("extend");
    extendTalon.set(EXTEND);
  }

  public void stopExtend(){
    logger.info("stop extend");
    extendTalon.set(0);
  }

  public void retract(){
    logger.info("retract");
    retractTalon.set(RETRACT);
  }
  
  public void stopRetract(){
    logger.info("stop retract");
    retractTalon.set(0);
  }

  public void move(double speed){
    logger.info("move at: " + speed);
    horizontalVictor.set(speed);
  }

  @Override
  public void periodic() {
  }
}
