package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;
import frc.robot.util.Logger.Level;

public class Drivetrain extends SubsystemBase {
  private static final Logger logger = new Logger(Drivetrain.class.getName(), Level.INFO, false);

  private final WPI_TalonFX frontLeftTalon;
  private final WPI_TalonFX rearLeftTalon;
  private final WPI_TalonFX frontRightTalon;
  private final WPI_TalonFX rearRightTalon;

  private final SpeedControllerGroup leftGroup;
  private final SpeedControllerGroup rightGroup;

  private final DifferentialDrive differentialDrive;

  private static final int MAX_PERIOD_COUNT = 10;
  private int periodicIndex;
  private long[] periodicTimestampArray = new long[MAX_PERIOD_COUNT];
  private int[] leftSidePositionArray = new int[MAX_PERIOD_COUNT];
  private int[] rightSidePositionArray = new int[MAX_PERIOD_COUNT];

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
    // TODO: check and see if this needs to be synchronous
    periodicIndex++;
    if (periodicIndex >= MAX_PERIOD_COUNT) {
      periodicIndex = 0;
    }
    periodicTimestampArray[periodicIndex] = System.nanoTime();
    leftSidePositionArray[periodicIndex] = frontLeftTalon.getSelectedSensorPosition();
    rightSidePositionArray[periodicIndex] = frontRightTalon.getSelectedSensorPosition();
  }

  public double getSpeed() {
    // Currently set to take the oldest index
    int lastIndex = periodicIndex + 1;
    if (lastIndex >= MAX_PERIOD_COUNT) {
      lastIndex = 0;
    }
    long period = periodicTimestampArray[periodicIndex] - periodicTimestampArray[lastIndex];
    if (period == 0) {
      period = 1000;  // default to 1 ms
    }
    // (1000 * (left-side-diff + right-side-diff)) / (2 * time-diff)
    // Returned units are encoder units per milliseconds
    return 500 * ((leftSidePositionArray[periodicIndex] - leftSidePositionArray[lastIndex]) +
        (rightSidePositionArray[periodicIndex] - rightSidePositionArray[lastIndex])) / period;
  }

  public int getLeftPosition() {
    return frontLeftTalon.getSelectedSensorPosition();
  }

  public int getRightPosition() {
    return frontRightTalon.getSelectedSensorPosition();
  }
}
