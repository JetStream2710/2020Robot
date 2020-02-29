package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;
import frc.robot.util.Logger;
import frc.robot.util.Logger.Level;

public class AutoShoot extends CommandBase {
  private static final Logger logger = new Logger(AutoShoot.class.getName(), Level.DETAIL, false);

  private final Vision vision;
//  private final NavX navx;
  private final Shooter shooter;
  private final Turret turret;
  private final Feeder feeder;

  public AutoShoot(Vision vision, Shooter shooter, Turret turret, Feeder feeder) {
    logger.detail("constructor");
    this.vision = vision;
//    this.navx = navx;
    this.shooter = shooter;
    this.turret = turret;
    this.feeder = feeder;
    addRequirements(vision);
//    addRequirements(navx);
    addRequirements(shooter);
    addRequirements(turret);
    addRequirements(feeder);
  }

  @Override
  public void initialize() {
    logger.detail("initialize");
    shooter.shooterOn();
    shooter.acceleratorOn();
  }

  @Override
  public void execute() {
    logger.info("execute");
    if (vision.hasValidTargets()){
      logger.info("has valid target");
      double offset = Vision.Entry.HORIZONTAL_OFFSET.getValue();
      //double speed = 0.1 + (0.3 * offset / 160);
      double speed = offset > 2 ? -0.15 : 0.15;
      if (offset > 2) {
        feeder.allOff();
        turret.move(speed);
        logger.info("moving positive at offset: %f   speed: %f", offset, speed);
      }
      else if (offset < -2){
        feeder.allOff();
        turret.move(speed);
        logger.info("moving negative at offset: %f   speed: %f", offset, speed);
      } else {
        turret.move(0);
        feeder.allOn();
        logger.info("shooting");
      }

      logger.info("Area: %f  Horiz: %f  Vert: %f",
        Vision.Entry.AREA.getValue(), Vision.Entry.HORIZONTAL_LENGTH.getValue(), Vision.Entry.VERTICAL_LENGTH.getValue());
      logger.info("Distance from area: %f  horiz: %f  vert: %f",
        vision.getDistanceFromArea(), vision.getDistanceFromHorizontal(), vision.getDistanceFromVertical());
    }
  }

  // Called once the command ends or is interrupted.
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
