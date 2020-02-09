package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;
import frc.robot.util.Logger;
import frc.robot.util.Logger.Level;

public class TurretCommand extends CommandBase {
  private static final Logger logger = new Logger(TurretCommand.class.getName(), Level.SEVERE, false);

  private final Turret turret;
  private final XboxController controller;

  public TurretCommand(Turret turret, XboxController controller) {
    logger.detail("constructor");
    this.turret = turret;
    this.controller = controller;
    addRequirements(turret);
  }

  @Override
  public void initialize() {
    logger.detail("intialize");
  }

  @Override
  public void execute() {
    double speed = -0.35 * controller.getRawAxis(0);
    logger.info("execute speed: %f", speed);
    if ((speed < 0 && turret.getPosition() > 7000) ||
        (speed > 0 && turret.getPosition() < -7000)) {
      turret.move(0);
      return;
    }
    turret.move(speed);
    logger.info("sensor position %d", turret.getPosition());
  }

  @Override
  public void end(boolean interrupted) {
    logger.detail("end");
    turret.move(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

}
