package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class Turret extends SubsystemBase {
  private static final Logger logger = new Logger(Turret.class.getName());

  private final WPI_TalonSRX talon;

  public Turret() {
    logger.detail("constructor");
    talon = MotorFactory.makeTalon(Constants.TURRET_TALON, "Turret Talon");
  }

  public void move(double speed) {
    talon.set(speed);
    logger.dashboard("turret speed", speed);
  }

  public void moveStop() {
    talon.set(0);
    logger.dashboard("turret speed", 0);
  }

  public int getPosition() {
    int position = talon.getSelectedSensorPosition();
    logger.dashboard("turret position", position);
    return position;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
