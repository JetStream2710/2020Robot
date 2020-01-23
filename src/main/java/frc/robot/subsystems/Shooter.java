package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;

public class Shooter extends SubsystemBase {
  private static final Logger logger = new Logger(Shooter.class.getName());

  public static final double SHOOTER_SPEED = 0.4;

  private final CANSparkMax leftSparkMax;
  private final CANSparkMax rightSparkMax;

  private final SpeedControllerGroup sparkMaxGroup;

  public Shooter() {
    logger.detail("constructor");

    leftSparkMax = new CANSparkMax(Constants.SHOOTER_LEFT_SPARKMAX, MotorType.kBrushless);
    rightSparkMax = new CANSparkMax(Constants.SHOOTER_RIGHT_SPARKMAX, MotorType.kBrushless);

    leftSparkMax.setIdleMode(IdleMode.kBrake);
    rightSparkMax.setIdleMode(IdleMode.kBrake);

    leftSparkMax.enableVoltageCompensation(12);
    rightSparkMax.enableVoltageCompensation(12);

    sparkMaxGroup = new SpeedControllerGroup(leftSparkMax, rightSparkMax);
  }

  // TODO: set w specific encoder value?
  public void on() {
    sparkMaxGroup.set(SHOOTER_SPEED);
    logger.dashboard("shooter", "on");    
  }

  public void off() {
    sparkMaxGroup.stopMotor();
    logger.dashboard("shooter", "off");
  }

  public double speed() {
    // TODO: this should report the actual speed, not the last set speed
    double speed = (leftSparkMax.get() + rightSparkMax.get()) / 2;
    logger.dashboard("shooter speed", speed);
    return speed;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
