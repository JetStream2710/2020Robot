package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Logger;

public class MoveDistance extends CommandBase {
  private static final Logger logger = new Logger(MoveDistance.class.getName());

  private static final double ENCODER_UNITS_PER_FOOT = (12 * 24000) / (Math.PI * 6);
  private static final double INIT_OUTPUT = 0.4;
  private static final double MAX_OUTPUT = 1.0;
  private static final double MIN_OUTPUT = 0.2;
  private static final double ACCELERATION_OUTPUT_PER_PERIOD = 0.001;
  private static final double DECELERATION_OUTPUT_PER_PERIOD = 0.001;

  private final Drivetrain drivetrain;
  private final int targetEncoderDistance;
  private final boolean isMovingForward;

  private int leftPosition;
  private int rightPosition;
  private int leftTargetPosition;
  private int rightTargetPosition;
  private double output;

  public MoveDistance(Drivetrain drivetrain, double distanceInFeet) {
    logger.detail("constructor");
    this.drivetrain = drivetrain;
    targetEncoderDistance = (int)(distanceInFeet * ENCODER_UNITS_PER_FOOT);
    isMovingForward = distanceInFeet > 0;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    logger.detail("initialize");
    updatePosition();

    if (isMovingForward) {
      leftTargetPosition = leftPosition + targetEncoderDistance;
      rightTargetPosition = rightPosition + targetEncoderDistance;
      drivetrain.arcadeDrive(INIT_OUTPUT, 0);
    } else {
      leftTargetPosition = leftPosition - targetEncoderDistance;
      rightTargetPosition = rightPosition - targetEncoderDistance;
      drivetrain.arcadeDrive(-INIT_OUTPUT, 0);
    }
    logger.info("left pos: %d [target: %d]  right pos: %d [target: %d]", leftPosition, leftTargetPosition, rightPosition, rightTargetPosition);
  }

  @Override
  public void execute() {
    logger.detail("execute");
    updatePosition();

    int decelOffset = calculateDecelOffset();
    int leftDecelPosition = leftTargetPosition - decelOffset;
    int rightDecelPosition = rightTargetPosition - decelOffset;
    if (isMovingForward) {
      // Check if we should decelerate
      if (leftPosition >= leftDecelPosition || rightPosition >= rightDecelPosition) {
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
      if (leftPosition <= leftDecelPosition || rightPosition <= rightDecelPosition) {
        if (output < -MIN_OUTPUT) {
          output += DECELERATION_OUTPUT_PER_PERIOD;
          logger.info("BACKWARD DECEL: %f  left pos: %d [decel: %d]  right pos: %d [decel: %d]", output, leftPosition, leftDecelPosition, rightPosition, rightDecelPosition);
        }
      } else if (output > -MAX_OUTPUT) {
        output -= ACCELERATION_OUTPUT_PER_PERIOD;
        logger.info("BACKWARD ACCEL: %f  left pos: %d [decel: %d]  right pos: %d [decel: %d]", output, leftPosition, leftDecelPosition, rightPosition, rightDecelPosition);
      }
    }
    drivetrain.arcadeDrive(output, 0);
  }

  @Override
  public void end(boolean interrupted) {
    logger.detail("end");
    drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isMovingForward
        ? (leftPosition >= leftTargetPosition || rightPosition >= rightTargetPosition)
        : (leftPosition <= leftTargetPosition || rightPosition <= rightTargetPosition);
  }

  private void updatePosition() {
    leftPosition = drivetrain.getLeftPosition();
    rightPosition = drivetrain.getRightPosition();
  }

  private int calculateDecelOffset() {
    return isMovingForward
      ? (int) ((output - MIN_OUTPUT) / DECELERATION_OUTPUT_PER_PERIOD)
      : (int) ((output + MIN_OUTPUT) / DECELERATION_OUTPUT_PER_PERIOD);
  }
}
