package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.util.Logger;

public class ShooterOff extends CommandBase {
  private static final Logger logger = new Logger(ShooterOn.class.getName());

  private final Shooter shooter;

  public ShooterOff(Shooter shooter) {
    logger.detail("constructor");
    this.shooter = shooter;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    logger.info("initialize");
    shooter.off();
  }

  @Override
  public void execute() {
    logger.detail("execute");
  }

  @Override
  public void end(boolean interrupted) {
    logger.detail("end");
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
