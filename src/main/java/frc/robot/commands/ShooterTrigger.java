package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.util.Logger;

public class ShooterTrigger extends CommandBase {
  private static final Logger logger = new Logger(ShooterTrigger.class.getName(), Logger.Level.DETAIL, false);

  private final Shooter shooter;
  
  public ShooterTrigger(Shooter shooter) {
    logger.detail("constructor");
    this.shooter = shooter;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    logger.info("initialize");
    shooter.triggerOn();
  }

  @Override
  public void execute() {
    logger.detail("execute");
  }

  @Override
  public void end(boolean interrupted) {
    logger.info("end");
    shooter.triggerOff();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
