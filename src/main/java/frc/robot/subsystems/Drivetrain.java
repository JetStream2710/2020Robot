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
  private static final Logger logger = new Logger(Drivetrain.class.getName());

  private final WPI_TalonFX frontLeftTalon;
  private final WPI_TalonFX rearLeftTalon;
  private final WPI_TalonFX frontRightTalon;
  private final WPI_TalonFX rearRightTalon;

  private final SpeedControllerGroup leftGroup;
  private final SpeedControllerGroup rightGroup;

  private final DifferentialDrive differentialDrive;

  public Drivetrain() {
    logger.detail("constructor");

    // motors defined
    frontLeftTalon = MotorFactory.makeTalonFX(Constants.DRIVETRAIN_FRONT_LEFT_TALON, "Drivetrain FrontLeft Talon");
    rearLeftTalon = MotorFactory.makeTalonFX(Constants.DRIVETRAIN_REAR_LEFT_TALON, "Drivetrain RearLeft Talon");
    frontRightTalon = MotorFactory.makeTalonFX(Constants.DRIVETRAIN_FRONT_RIGHT_TALON, "Drivetrain FrontRight Talon");
    rearRightTalon = MotorFactory.makeTalonFX(Constants.DRIVETRAIN_REAR_RIGHT_TALON, "Drivetrain RearRight Talon");
  
    // groups defined
    leftGroup = new SpeedControllerGroup(frontLeftTalon, rearLeftTalon);
    rightGroup = new SpeedControllerGroup(frontRightTalon, rearRightTalon);

    differentialDrive = new DifferentialDrive(leftGroup, rightGroup);
  }

  public void arcadeDrive(final double moveSpeed, final double rotateSpeed) {
    logger.dashboard("drive move speed", moveSpeed);
    logger.dashboard("drive rotate speed", rotateSpeed);
    differentialDrive.arcadeDrive(moveSpeed, rotateSpeed);
    logger.detail("leftGroup: %f  rightGroup: %f", leftGroup.get(), rightGroup.get());
    logger.info("FRONT: left position: %d  right position: %d", frontLeftTalon.getSelectedSensorPosition(), frontRightTalon.getSelectedSensorPosition());
    logger.info("REAR: left position: %d  right position: %d", rearLeftTalon.getSelectedSensorPosition(), rearRightTalon.getSelectedSensorPosition());
  }
  
  // autonomous functions
  public void move() {
  }
  
  // utility functions

  public void setBrakeMode() {
    frontLeftTalon.setNeutralMode(NeutralMode.Brake);
    rearLeftTalon.setNeutralMode(NeutralMode.Brake);
    frontRightTalon.setNeutralMode(NeutralMode.Brake);
    rearRightTalon.setNeutralMode(NeutralMode.Brake);
    logger.dashboard("brake mode", "on");
  }

  public void setCoastMode() {
    frontLeftTalon.setNeutralMode(NeutralMode.Coast);
    rearLeftTalon.setNeutralMode(NeutralMode.Coast);
    frontRightTalon.setNeutralMode(NeutralMode.Coast);
    rearRightTalon.setNeutralMode(NeutralMode.Coast);
    logger.dashboard("brake mode", "off");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
