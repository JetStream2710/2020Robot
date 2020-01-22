package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;
import frc.robot.util.Logger;

public class ClimbMoveRight extends CommandBase {

  private static final Logger logger = new Logger(ClimbMoveRight.class.getName());

  private final Climb climb;

  public ClimbMoveRight(Climb climb) {
    logger.detail("constructor");
    this.climb = climb;
    addRequirements(climb);
  }

  @Override
  public void initialize() {
    logger.info("initialize");
    climb.moveRight();
  }

  @Override
  public void execute() {
    logger.detail("execute");
  }

  @Override
  public void end(boolean interrupted) {
    logger.info("ende");
    climb.moveStop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
