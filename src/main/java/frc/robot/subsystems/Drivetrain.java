package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class Drivetrain extends SubsystemBase {
  
  private Logger logger = new Logger(Drivetrain.class.getName());

  private WPI_TalonFX frontLeftTalon;
  private WPI_TalonFX rearLeftTalon;
  private WPI_TalonFX frontRightTalon;
  private WPI_TalonFX rearRightTalon;

  private SpeedControllerGroup leftGroup;
  private SpeedControllerGroup rightGroup;

  private DifferentialDrive differentialDrive = null;

  public Drivetrain() {
    super();

    logger.detail("constructor");

    // motors defined
    frontLeftTalon = MotorFactory.makeTalonFX(Constants.DRIVETRAIN_FRONT_LEFT_TALON, "frontLeftTalon");
    rearLeftTalon = MotorFactory.makeTalonFX(Constants.DRIVETRAIN_REAR_LEFT_TALON, "rearLeftTalon");
    frontRightTalon = MotorFactory.makeTalonFX(Constants.DRIVETRAIN_FRONT_RIGHT_TALON, "frontRightTalon");
    rearRightTalon = MotorFactory.makeTalonFX(Constants.DRIVETRAIN_REAR_RIGHT_TALON, "rearRightTalon");

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
  public void move(){

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
