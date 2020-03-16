package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;
import frc.robot.util.Logger;

public class ClimbExtendForward extends CommandBase {
  private static final Logger logger = new Logger(ClimbExtendForward.class.getName(), Logger.Level.SEVERE, true);

  private final Climb climb;

  public ClimbExtendForward(Climb climb) {
    logger.detail("constructor");
    this.climb = climb;
    addRequirements(climb);
  }

  @Override
  public void initialize() {
    logger.detail("initialize");
    climb.extend();
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    climb.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
