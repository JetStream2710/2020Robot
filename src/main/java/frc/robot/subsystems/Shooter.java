package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

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
    super();

    logger.detail("constructor");

    // check this please
    leftSparkMax = new CANSparkMax(Constants.SHOOTER_LEFT_SPARKMAX, MotorType.kBrushless);
    rightSparkMax = new CANSparkMax(Constants.SHOOTER_RIGHT_SPARKMAX, MotorType.kBrushless);

    leftSparkMax.enableVoltageCompensation(12);
    rightSparkMax.enableVoltageCompensation(12);

    sparkMaxGroup = new SpeedControllerGroup(leftSparkMax, rightSparkMax);
  }

  // TODO: set w specific encoder value?
  public void shootOn(){
    logger.info("shootOn speed: " + Constants.SHOOTER_OUTTAKE_SPEED);    
    sparkMaxGroup.set(Constants.SHOOTER_OUTTAKE_SPEED);
  }

  public void shootOff(){
    logger.info("shootOff");
    sparkMaxGroup.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
