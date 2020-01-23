package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.util.Logger;

public class FeederHorizontalOn extends CommandBase {
  private static final Logger logger = new Logger(FeederHorizontalOn.class.getName());

  private final Feeder feeder;

  public FeederHorizontalOn(Feeder feeder) {
    logger.detail("constructor");
    this.feeder = feeder;
    addRequirements(feeder);
  }

  @Override
  public void initialize() {
    logger.info("initialize");
    feeder.horizontalOn();
  }

  @Override
  public void execute() {
    logger.detail("execute");
  }

  @Override
  public void end(boolean interrupted) {
    logger.info("end");
    feeder.horizontalOff();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
