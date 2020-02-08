package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Logger;
import frc.robot.util.Logger.Level;

public class MoveDistance extends CommandBase {
  private static final Logger logger = new Logger(MoveDistance.class.getName(), Level.DETAIL, false);

  private static final double ENCODER_UNITS_PER_FOOT = (12 * 24000) / (Math.PI * 6);
  private static final double INIT_OUTPUT = 0.5;
  private static final double MAX_OUTPUT = 1.0;
  private static final double MIN_OUTPUT = 0.2;
  private static final double ACCELERATION_OUTPUT_PER_PERIOD = 0.01;
  private static final double DECELERATION_OUTPUT_PER_PERIOD = 0.1;

  private final Drivetrain drivetrain;
  private final int targetEncoderDistance;
  private final boolean isMovingForward;

  private int leftPosition;
  private int rightPosition;
  private int leftTargetPosition;
  private int rightTargetPosition;
  private double output;

  private int decelOffset;
  private int leftDecelPosition;
  private int rightDecelPosition;

  public MoveDistance(Drivetrain drivetrain, double distanceInFeet) {
    logger.detail("constructor");
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
    targetEncoderDistance = (int)(distanceInFeet * ENCODER_UNITS_PER_FOOT);
    isMovingForward = distanceInFeet > 0;
    // TODO: figure out if we need the following line
    //initialize();
  }

  @Override
  public void initialize() {
    logger.detail("initialize");
    updatePosition();

    decelOffset = calculateDecelOffset();
    if (isMovingForward) {
      leftTargetPosition = leftPosition + targetEncoderDistance;
      rightTargetPosition = rightPosition + targetEncoderDistance;
      leftDecelPosition = leftTargetPosition - decelOffset;
      rightDecelPosition = rightTargetPosition - decelOffset;
      output = INIT_OUTPUT;
      drivetrain.arcadeDrive(output, 0);
    } else {
      leftTargetPosition = leftPosition - targetEncoderDistance;
      rightTargetPosition = rightPosition - targetEncoderDistance;
      leftDecelPosition = leftTargetPosition + decelOffset;
      rightDecelPosition = rightTargetPosition + decelOffset;  
      output = -INIT_OUTPUT;
      drivetrain.arcadeDrive(output, 0);
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
    // int decelOffset = calculateDecelOffset();
    // int leftDecelPosition = leftTargetPosition - decelOffset;
    // int rightDecelPosition = rightTargetPosition - decelOffset;
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
