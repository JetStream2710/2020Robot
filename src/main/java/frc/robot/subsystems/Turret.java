package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class Turret extends SubsystemBase {

  private static final Logger logger = new Logger(Turret.class.getName());

  private WPI_TalonSRX talon;

  public Turret() {
    logger.detail("constructor");

    talon = MotorFactory.makeTalon(Constants.TURRET_TALON, "talon");
  }

  public void move(double speed){
    logger.info("move");
    talon.set(speed);
  }

  public void moveStop(){
    logger.info("moveStop");
    talon.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
