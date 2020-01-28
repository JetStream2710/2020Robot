package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;
import frc.robot.util.Logger;

public class ClimbExtend extends CommandBase {
  private static final Logger logger = new Logger(ClimbExtend.class.getName());

  private final Climb climb;

  public ClimbExtend(Climb climb) { 
    logger.detail("constructor");
    this.climb = climb;
    addRequirements(climb);
  }

  @Override
  public void initialize() { 
    logger.info("initialize");
    climb.extend();
  }

  @Override
  public void execute() {
    logger.detail("execute");
  }

  @Override
  public void end(boolean interrupted) {
    logger.info("end");
    climb.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
