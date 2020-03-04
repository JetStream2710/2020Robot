package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.util.Logger;

public class FeederReverse extends CommandBase {
  private static final Logger logger = new Logger(FeederReverse.class.getName());

  private final Feeder feeder;

  public FeederReverse(Feeder feeder) {
    logger.detail("constructor");
    this.feeder = feeder;
    addRequirements(feeder);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    logger.detail("execute");
    feeder.feederReverse();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    feeder.feederOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
