package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Logger;

public class MoveDistance extends CommandBase {
  private static final Logger logger = new Logger(MoveDistance.class.getName());

  private final Drivetrain drivetrain;
  private final double distance;

  private static final double INIT_SPEED = 0.4;
  private static final double MAX_SPEED = 1;

  private int leftInitial;
  private int rightInitial;
  private int leftPosition;
  private int rightPosition;
  private int position;
  private double speed;

  private static final double FEET_TO_REV = 12/(6*Math.PI);
  // NOTE: internet said it was 4096 pulses per revolution, but i thought it was 1000
  private static final double REV_TO_ENCODER = 4096;
  
  public MoveDistance(Drivetrain drivetrain, double distance) {
    logger.detail("constructor");
    this.drivetrain = drivetrain;
    this.distance = distance*FEET_TO_REV*REV_TO_ENCODER;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    logger.info("initialize");
    speed = INIT_SPEED;
    leftInitial = drivetrain.getLeftPosition();
    rightInitial = drivetrain.getRightPosition();
  }

  @Override
  public void execute() {
    logger.info("execute");
    leftPosition = drivetrain.getLeftPosition() - leftInitial;
    rightPosition = drivetrain.getRightPosition() - rightInitial;
    position = (leftPosition + rightPosition)/2;

/*
    // proportionate to distance
    // acceleration
    // arbitrary point to end acceleration
    if (position < distance*.2){
        speed += (position/distance/0.2)*MAX_SPEED + INIT_SPEED;
    }
    // decceleration
    else if (position > distance*.8){
      speed += ((1-position)/distance/0.2)*MAX_SPEED;
    }
    else {
      speed = MAX_SPEED;
    }
*/

    // by distance
    if (speed != MAX_SPEED && position < distance/2){
      speed += 0.01;
    }
    else if (position > distance-800){
      speed -= 0.01;
    }
    else {
      speed = MAX_SPEED;
    }

    drivetrain.arcadeDrive(speed, 0);
  }

  @Override
  public void end(boolean interrupted) {
    logger.info("end");
    drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
