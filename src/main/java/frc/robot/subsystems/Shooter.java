package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;

public class Shooter extends SubsystemBase {

  private Logger logger = new Logger(Feeder.class.getName());

  private CANSparkMax leftSparkMax;
  private CANSparkMax rightSparkMax;

  private SpeedControllerGroup sparkMaxGroup;

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
  public void on(){
    logger.info("on speed: " + Constants.SHOOTER_OUTTAKE_SPEED);    
    sparkMaxGroup.set(Constants.SHOOTER_OUTTAKE_SPEED);
  }

  public void off(){
    logger.info("off");
    sparkMaxGroup.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
