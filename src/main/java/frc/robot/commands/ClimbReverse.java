package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;
import frc.robot.util.Logger;

public class ClimbReverse extends CommandBase {
  private static final Logger logger = new Logger(ClimbReverse.class.getName(), Logger.Level.SEVERE, true);

  private final Climb climb;

  public ClimbReverse(Climb climb) {
    logger.detail("constructor");
    this.climb = climb;
    addRequirements(climb);
  }

  @Override
  public void initialize() {
    logger.detail("initialize");
    climb.extendReverse();
  }

  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climb.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
