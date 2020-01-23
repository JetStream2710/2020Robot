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
    logger.info("initialized");
    feeder.allReverse();
  }

  @Override
  public void execute() {
    logger.detail("execute");
  }

  @Override
  public void end(boolean interrupted) {
    logger.info("end");
    feeder.allOff();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
