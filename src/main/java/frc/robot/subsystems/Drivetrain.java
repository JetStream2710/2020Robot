package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;

public class Drivetrain extends SubsystemBase {
  
  private Logger logger = new Logger(Drivetrain.class.getName());

  private WPI_TalonSRX frontLeftTalon;
  private WPI_TalonSRX rearLeftTalon;
  private WPI_TalonSRX frontRightTalon;
  private WPI_TalonSRX rearRightTalon;

  private SpeedControllerGroup leftGroup;
  private SpeedControllerGroup rightGroup;

  private DifferentialDrive differentialDrive = null;

  public Drivetrain() {
    super();

    logger.detail("constructor");

    // motors defined
    frontLeftTalon = new WPI_TalonSRX(Constants.DRIVETRAIN_FRONT_LEFT_TALON);
    rearLeftTalon = new WPI_TalonSRX(Constants.DRIVETRAIN_REAR_LEFT_TALON);
    frontRightTalon = new WPI_TalonSRX(Constants.DRIVETRAIN_FRONT_RIGHT_TALON);
    rearRightTalon = new WPI_TalonSRX(Constants.DRIVETRAIN_REAR_RIGHT_TALON);

    frontLeftTalon.setSafetyEnabled(false);
    rearLeftTalon.setSafetyEnabled(false);
    frontRightTalon.setSafetyEnabled(false);
    rearRightTalon.setSafetyEnabled(false);

    frontLeftTalon.setNeutralMode(NeutralMode.Brake);
    rearLeftTalon.setNeutralMode(NeutralMode.Brake);
    frontRightTalon.setNeutralMode(NeutralMode.Brake);
    rearRightTalon.setNeutralMode(NeutralMode.Brake);

    frontLeftTalon.configVoltageCompSaturation(12);
    rearLeftTalon.configVoltageCompSaturation(12);
    frontRightTalon.configVoltageCompSaturation(12);
    rearRightTalon.configVoltageCompSaturation(12);

    frontLeftTalon.enableVoltageCompensation(true);
    rearLeftTalon.enableVoltageCompensation(true);
    frontRightTalon.enableVoltageCompensation(true);
    rearRightTalon.enableVoltageCompensation(true);

    // groups defined
    leftGroup = new SpeedControllerGroup(frontLeftTalon, rearLeftTalon);
    rightGroup = new SpeedControllerGroup(frontRightTalon, rearRightTalon);

    differentialDrive = new DifferentialDrive(leftGroup, rightGroup);
  }

  public void arcadeDrive(final double moveSpeed, final double rotateSpeed){
    logger.info("arcade drive movespeed: " + moveSpeed + " rotatespeed: " + rotateSpeed);
    logger.detail("leftGroup: " + frontLeftTalon.get() + " rightGroup: " + frontRightTalon.get());
    differentialDrive.arcadeDrive(moveSpeed, rotateSpeed);
  }

  
  // autonomous functions

  public void shoot(){

  }

  public void move(){

  }

  public void shootNMove(){

  }


  // utility functions

  public void setBrakeMode(){
    frontLeftTalon.setNeutralMode(NeutralMode.Brake);
    rearLeftTalon.setNeutralMode(NeutralMode.Brake);
    frontRightTalon.setNeutralMode(NeutralMode.Brake);
    rearRightTalon.setNeutralMode(NeutralMode.Brake);
  }

  public void setCoastMode(){
    frontLeftTalon.setNeutralMode(NeutralMode.Coast);
    rearLeftTalon.setNeutralMode(NeutralMode.Coast);
    frontRightTalon.setNeutralMode(NeutralMode.Coast);
    rearRightTalon.setNeutralMode(NeutralMode.Coast);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
