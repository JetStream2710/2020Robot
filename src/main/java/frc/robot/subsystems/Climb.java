package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class Climb extends SubsystemBase {
  private static final Logger logger = new Logger(Climb.class.getName());

  private static final double EXTEND = 0.4;
  private static final double RETRACT = 1;

  private final WPI_VictorSPX extendVictor;
  private final WPI_VictorSPX retractVictor;

  public Climb() {
    logger.detail("constructor");
    extendVictor = MotorFactory.makeVictor(Constants.CLIMB_EXTEND_VICTOR, "Climb Extend Victor");
    retractVictor = MotorFactory.makeVictor(Constants.CLIMB_RETRACT_VICTOR, "Climb Retract Victor");
    }

  public void extend() {
    extendVictor.set(EXTEND);
    retractVictor.set(-RETRACT);
    logger.dashboard("climb extend", EXTEND);
    logger.dashboard("climb retract", -RETRACT);
  }

  public void retract() {
    extendVictor.set(-EXTEND);
    retractVictor.set(RETRACT);
    logger.dashboard("climb extend", -EXTEND);
    logger.dashboard("climb retract", RETRACT);
  }
  
  public void stop() {
    extendVictor.set(0);
    retractVictor.set(0);
    logger.dashboard("climb extend", 0);
    logger.dashboard("climb retract", 0);
  }

  @Override
  public void periodic() {
  }
}
