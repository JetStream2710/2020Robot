package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Logger;

public class TurnDegrees extends CommandBase {
  private static final Logger logger = new Logger(TurnDegrees.class.getName());

  private static final double WHEELBASE_RADIUS = 22.25 / 2;
  private final Drivetrain drivetrain;
  private final double degrees;
  private boolean isPositive = true;

  private static final double INIT_SPEED = 0.4;
  private static final double MAX_SPEED = 1;

  private int positionInit;
  private int position;
  private double speed;

  // NOTE: check my math on this 
  // NOTE: internet said it was 4096 pulses per revolution, but i thought it was 1000
  private static final double DEGREES_TO_ENCODER = 19*Math.sqrt(2)/(6*Math.PI*360);
 
  public TurnDegrees(Drivetrain drivetrain, double degrees) {
    logger.detail("constructor");
    this.drivetrain = drivetrain;
    if (degrees < 0) {
      isPositive = false;
      degrees = degrees*-1;
    }
    this.degrees = degrees*DEGREES_TO_ENCODER;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    logger.info("initialize");
    if (isPositive){
      positionInit = drivetrain.getLeftPosition();
    }
    else {
      positionInit = drivetrain.getRightPosition();
    }
    speed = INIT_SPEED;
  }

  @Override
  public void execute() {
    logger.info("execute");
    if (isPositive){
      position = drivetrain.getLeftPosition() - positionInit;
    }
    else{
      position = drivetrain.getRightPosition() - positionInit;
    }

    if (speed != MAX_SPEED && position < degrees/2){
      speed += 0.01;
    }
    else if (position > degrees-800){
      speed -= 0.01;
    }
    else {
      speed = MAX_SPEED;
    }

    drivetrain.arcadeDrive(0, speed);
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
