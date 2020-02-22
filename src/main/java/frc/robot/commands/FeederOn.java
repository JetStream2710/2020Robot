package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.util.Logger;

public class FeederOn extends CommandBase {
  private static final Logger logger = new Logger(FeederOn.class.getName());

  private final Feeder feeder;

  public FeederOn(Feeder feeder) {
    logger.detail("constructor");
    this.feeder = feeder;
    addRequirements(feeder);
  }

  @Override
  public void initialize() {
    logger.info("initialize");
    feeder.allOn();
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
