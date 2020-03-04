package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NavX;
import frc.robot.util.Logger;

public class TurnDegrees extends CommandBase {
  private static final Logger logger = new Logger(TurnDegrees.class.getName(), Logger.Level.DETAIL, false);

  private static final double WHEELBASE_RADIUS = 22.25 / 2;
  private static final double ENCODER_UNITS_PER_DEGREE = ((24000 / (Math.PI * 6)) * (2 * Math.PI * WHEELBASE_RADIUS)) / 360;
//  private static final double ENCODER_UNITS_PER_DEGREE = (Math.sqrt(25*25 + 23*23) * 24000) / (360 * 6);
  private static final double INIT_OUTPUT = 0.4;
  private static final double MAX_OUTPUT = 0.5;
  private static final double MIN_OUTPUT = 0.0;
  private static final double ACCELERATION_OUTPUT_PER_PERIOD = 0.05;
  // multiplied deceleration by 5
  private static final double DECELERATION_OUTPUT_PER_PERIOD = 0.05;

  /**
   * Calculating ENCODER_UNITS_PER_DEGREE
   * TURN_RADIUS = Math.sqrt(25*25 + 23*23)/2
   * ENCODER_UNITS_PER_INCH = 24000 / (6*Math.PI)
   * FULL_SPIN = (TURN_RADIUS)*2*Math.PI << inches
   * ONE_DEGREE_SPIN = FULL_SPIN / 360
   * ENCODER_UNITS_PER_DEGREE = ENCODER_UNITS_PER_INCH * ONE_DEGREE_SPIN
   */

  private final Drivetrain drivetrain;
  private final int targetEncoderDistance;
  private final boolean isTurningRight;

  private int leftPosition;
  private int rightPosition;
  private int leftTargetPosition;
  private int rightTargetPosition;
  private double output;
 
  public TurnDegrees(Drivetrain drivetrain, NavX navx, double distanceInDegrees) {
    logger.detail("constructor");
    this.drivetrain = drivetrain;
    targetEncoderDistance = (int) (distanceInDegrees * ENCODER_UNITS_PER_DEGREE);
    isTurningRight = distanceInDegrees > 0;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    logger.detail("initialize");
    updatePosition();

    if (isTurningRight) {
      leftTargetPosition = leftPosition + targetEncoderDistance;
      rightTargetPosition = rightPosition - targetEncoderDistance;
      drivetrain.arcadeDrive(0, INIT_OUTPUT);
    } else {
      leftTargetPosition = leftPosition - targetEncoderDistance;
      rightTargetPosition = rightPosition + targetEncoderDistance;
      drivetrain.arcadeDrive(0, -INIT_OUTPUT);
    }
    logger.warning("left pos: %d [target: %d]  right pos: %d [target: %d]", leftPosition, leftTargetPosition, rightPosition, rightTargetPosition);
  }

  @Override
  public void execute() {
    logger.detail("execute");
    updatePosition();

    if (isFinished()) {
      drivetrain.arcadeDrive(0, 0);
      return;
    }

    // initialize output?
    int decelOffset = calculateDecelOffset();
    int leftDecelPosition = leftTargetPosition - decelOffset;
    int rightDecelPosition = rightTargetPosition + decelOffset;
    logger.detail("decelOffset: %d  leftDecelPosition: %d  rightDecelPosition: %d", decelOffset, leftDecelPosition, rightDecelPosition);
    if (isTurningRight) {
      // Check if we should decelerate
      if (leftPosition >= leftDecelPosition || rightPosition <= rightDecelPosition) {
        if (output > MIN_OUTPUT) {
          output -= DECELERATION_OUTPUT_PER_PERIOD;
          logger.info("FORWARD DECEL: %f  left pos: %d [decel: %d]  right pos: %d [decel: %d]", output, leftPosition, leftDecelPosition, rightPosition, rightDecelPosition);
        }
      } else if (output < MAX_OUTPUT) {
        output += ACCELERATION_OUTPUT_PER_PERIOD;
        logger.info("FORWARD ACCEL: %f  left pos: %d [decel: %d]  right pos: %d [decel: %d]", output, leftPosition, leftDecelPosition, rightPosition, rightDecelPosition);        
      }
    } else {
      // Check if we should decelerate
      if (leftPosition <= leftDecelPosition || rightPosition >= rightDecelPosition) {
        if (output < -MIN_OUTPUT) {
          output += DECELERATION_OUTPUT_PER_PERIOD;
          logger.info("BACKWARD DECEL: %f  left pos: %d [decel: %d]  right pos: %d [decel: %d]", output, leftPosition, leftDecelPosition, rightPosition, rightDecelPosition);
        }
      } else if (output > -MAX_OUTPUT) {
        output -= ACCELERATION_OUTPUT_PER_PERIOD;
        logger.info("BACKWARD ACCEL: %f  left pos: %d [decel: %d]  right pos: %d [decel: %d]", output, leftPosition, leftDecelPosition, rightPosition, rightDecelPosition);
      }
    }
    drivetrain.arcadeDrive(0, -output);
  }

  @Override
  public void end(boolean interrupted) {
    logger.info("end");
    drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isTurningRight
        ? (leftPosition >= leftTargetPosition || rightPosition <= rightTargetPosition)
        : (leftPosition <= leftTargetPosition || rightPosition >= rightTargetPosition);
  }

  private void updatePosition() {
    leftPosition = drivetrain.getLeftPosition();
    rightPosition = drivetrain.getRightPosition();
  }

  private int calculateDecelOffset() {
    return 20 * (isTurningRight
      ? (int) ((output - MIN_OUTPUT) / DECELERATION_OUTPUT_PER_PERIOD)
      : (int) ((output + MIN_OUTPUT) / DECELERATION_OUTPUT_PER_PERIOD));
  }
}
