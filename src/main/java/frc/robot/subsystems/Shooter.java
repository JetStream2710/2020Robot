package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class Shooter extends SubsystemBase {
  private static final Logger logger = new Logger(Shooter.class.getName());

  public static final double SHOOTER_SPEED = .8;

  private final WPI_TalonFX leftTalon;
  private final WPI_TalonFX rightTalon;

  public Shooter() {
    logger.detail("constructor");

    leftTalon = MotorFactory.makeTalonFX(Constants.SHOOTER_LEFT_TALON, "leftTalon");
    rightTalon = MotorFactory.makeTalonFX(Constants.SHOOTER_RIGHT_TALON, "rightTalon");
  }

  // TODO: set w specific encoder value?
  public void on() {
    leftTalon.set(-SHOOTER_SPEED);
    rightTalon.set(SHOOTER_SPEED);
    logger.dashboard("shooter", "on");    
  }

  public void off() {
    leftTalon.set(0);
    rightTalon.set(0);
    logger.dashboard("shooter", "off");
  }

  public double speed() {
    // TODO: this should report the actual speed, not the last set speed
    double speed = (leftTalon.get() + rightTalon.get()) / 2;
    logger.dashboard("shooter speed", speed);
    return speed;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
