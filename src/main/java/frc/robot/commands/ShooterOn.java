package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.util.Logger;

public class ShooterOn extends CommandBase {
  private static final Logger logger = new Logger(ShooterOn.class.getName());

  private static final int MAX_PERIOD_COUNT = 10;
  private int periodicIndex;
  private long[] periodicTimestampArray = new long[MAX_PERIOD_COUNT];
  private int[] leftSidePositionArray = new int[MAX_PERIOD_COUNT];
  private int[] rightSidePositionArray = new int[MAX_PERIOD_COUNT];

  private final Shooter shooter;
  
  public ShooterOn(Shooter shooter) {
    logger.detail("constructor");
    this.shooter = shooter;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    logger.info("initialize");
    shooter.on();
  }

  @Override
  public void execute() {
    logger.detail("execute");
    periodicIndex++;
    if (periodicIndex >= MAX_PERIOD_COUNT) {
      periodicIndex = 0;
    }
    periodicTimestampArray[periodicIndex] = System.nanoTime();
    leftSidePositionArray[periodicIndex] = shooter.getLeftPosition();
    rightSidePositionArray[periodicIndex] = shooter.getRightPosition();

    logger.info("shooter speed: %f   speed2: %f", shooter.speed(), getSpeed());
  }

  @Override
  public void end(boolean interrupted) {
    logger.info("end");
    shooter.off();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  public double getSpeed() {
    // Currently set to take the oldest index
    int lastIndex = periodicIndex + 1;
    if (lastIndex >= MAX_PERIOD_COUNT) {
      lastIndex = 0;
    }
    long period = (periodicTimestampArray[periodicIndex] - periodicTimestampArray[lastIndex]) / 1000;
    if (period == 0) {
      period = 1000;  // default to 1 ms
    }
    /*
    System.out.println("period: " + period + "  idx: " + periodicIndex + "  left: " +
    (leftSidePositionArray[periodicIndex] - leftSidePositionArray[lastIndex]) + "  right: " +
    (rightSidePositionArray[periodicIndex] - rightSidePositionArray[lastIndex]));
    */
    
    // (1000 * (left-side-diff + right-side-diff)) / (2 * time-diff)
    // Returned units are encoder units per milliseconds
    return 500 * ((leftSidePositionArray[periodicIndex] - leftSidePositionArray[lastIndex]) +
        (rightSidePositionArray[periodicIndex] - rightSidePositionArray[lastIndex])) / period;
  }
}
