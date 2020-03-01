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

  public Climb() {
    logger.detail("constructor");
    extendTalon = MotorFactory.makeTalon(Constants.CLIMB_EXTEND_TALON, "Climb Extend Talon");
    retractTalon = MotorFactory.makeTalon(Constants.CLIMB_RETRACT_TALON, "Climb Retract Talon");
    }

  public void extend() {
    extendTalon.set(EXTEND);
    retractTalon.set(-RETRACT);
    logger.dashboard("climb extend", EXTEND);
    logger.dashboard("climb retract", -RETRACT);
  }

  public void retract() {
    extendTalon.set(-EXTEND);
    retractTalon.set(RETRACT);
    logger.dashboard("climb extend", -EXTEND);
    logger.dashboard("climb retract", RETRACT);
  }
  
  public void stop() {
    extendTalon.set(0);
    retractTalon.set(0);
    logger.dashboard("climb extend", 0);
    logger.dashboard("climb retract", 0);
  }

  @Override
  public void periodic() {
  }
}
