package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class Climb extends SubsystemBase {
  private static final Logger logger = new Logger(Climb.class.getName());

  private static final double EXTEND = 0.6;
  private static final double RETRACT = 0.6;

  private final WPI_VictorSPX extendVictor;
  private final WPI_VictorSPX retractVictor;

  public Climb() {
    logger.detail("constructor");
    extendVictor = MotorFactory.makeVictor(Constants.CLIMB_EXTEND_VICTOR, "Climb Extend Victor");
    retractVictor = MotorFactory.makeVictor(Constants.CLIMB_RETRACT_VICTOR, "Climb Retract Victor");
    }

  public void extend() {
    extendVictor.set(EXTEND);
    System.out.println("EXTEND: " + EXTEND);
//    retractVictor.set(-RETRACT);
    logger.dashboard("climb extend", EXTEND);
//    logger.dashboard("climb retract", -RETRACT);
  }

  public void extendReverse() {
    retractVictor.set(-RETRACT);
  }

  public void retract() {
//    extendVictor.set(-EXTEND);
    retractVictor.set(RETRACT);
//    logger.dashboard("climb extend", -EXTEND);
    logger.dashboard("climb retract", RETRACT);
  }

  
  public void stop() {
    extendVictor.set(0);
    retractVictor.set(0);
    logger.dashboard("climb extend", 0);
    logger.dashboard("climb retract", 0);
  }

  public void setCoastMode(){
    extendVictor.setNeutralMode(NeutralMode.Coast);
    retractVictor.setNeutralMode(NeutralMode.Coast);
   }

   public void setBrakeMode(){
    extendVictor.setNeutralMode(NeutralMode.Brake);
    retractVictor.setNeutralMode(NeutralMode.Brake);
   }

  @Override
  public void periodic() {
  }
}
