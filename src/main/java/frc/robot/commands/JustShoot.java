package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.util.Logger;
import frc.robot.util.Logger.Level;

public class JustShoot extends CommandBase {

  private static final Logger logger = new Logger(JustShoot.class.getName(), Level.DETAIL, false);

  private final Feeder feeder;
  private final Shooter shooter;

  private static final long SHOOT_DELAY_MILLIS = 1000;

  private static long shootTime;

  public JustShoot(Feeder feeder, Shooter shooter) {
    logger.detail("constructor");
    this.feeder = feeder;
    this.shooter = shooter;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    shooter.shooterOn();
    shooter.acceleratorOn();
    shootTime = System.currentTimeMillis() + SHOOT_DELAY_MILLIS;
  }

  @Override
  public void execute() {
    logger.detail("execute");
    if (System.currentTimeMillis() > shootTime) {
      feeder.allOn();
    }
    logger.info("shooting");
  }

  @Override
  public void end(boolean interrupted) {
    shooter.shooterOff();
    shooter.acceleratorOff();
    feeder.allOff();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
