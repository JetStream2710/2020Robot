package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;
import frc.robot.util.Logger;

public class ClimbRetract extends CommandBase {
  private static final Logger logger = new Logger(ClimbCommand.class.getName(), Logger.Level.SEVERE, true);

  private final Climb climb;

  public ClimbRetract(Climb climb) {
    logger.detail("constructor");
    this.climb = climb;
    addRequirements(climb);
  }

  @Override
  public void initialize() {
    climb.retract();
  }

  @Override
  public void execute() {
  }

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
