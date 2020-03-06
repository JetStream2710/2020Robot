package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;
import frc.robot.util.Logger;
import frc.robot.util.Logger.Level;

public class AutoShoot extends CommandBase {
  private static final Logger logger = new Logger(AutoShoot.class.getName(), Level.DETAIL, false);

  private static final long SHOOT_DELAY_MILLIS = 1000;
  private static final double TURRET_SPEED = 0.13;

  private final Vision vision;
  private final Shooter shooter;
  private final Turret turret;
  private final Feeder feeder;
  // private final NavX navx;
  private long shootTime;
  private long endCommandTime;
  private long delayMillis;

  public AutoShoot(Vision vision, Shooter shooter, Turret turret, Feeder feeder) {
    this(vision, shooter, turret, feeder, -1);
  }

  public AutoShoot(Vision vision, Shooter shooter, Turret turret, Feeder feeder, long delayMillis) {
    logger.detail("constructor");
    this.vision = vision;
    this.shooter = shooter;
    this.turret = turret;
    this.feeder = feeder;
    this.delayMillis = delayMillis;
    // this.navx = navx;
    addRequirements(vision);
    addRequirements(shooter);
    addRequirements(turret);
    // addRequirements(feeder);
    // addRequirements(navx);
  }

  @Override
  public void initialize() {
    logger.detail("initialize");
    vision.turnOnCamLeds();
    shooter.shooterOn();
    shooter.acceleratorOn();
    if (delayMillis > 0) {
      endCommandTime = System.currentTimeMillis() + delayMillis;
    } else {
      endCommandTime = -1;
    }
    shootTime = System.currentTimeMillis() + SHOOT_DELAY_MILLIS;
  }

  @Override
  public void execute() {
    logger.info("execute");
    if (vision.hasValidTargets()){
      logger.info("has valid target");
      double offset = Vision.Entry.HORIZONTAL_OFFSET.getValue();
      double turretSpeed = offset > 0 ? -TURRET_SPEED : TURRET_SPEED;
      if (offset > 1) {
        feeder.allOff();
        turret.move(turretSpeed);
        logger.info("moving positive at offset: %f   speed: %f", offset, turretSpeed);
      }
      else if (offset < -1){
        feeder.allOff();
        turret.move(turretSpeed);
        logger.info("moving negative at offset: %f   speed: %f", offset, turretSpeed);
      } else {
        turret.move(0);
        if (System.currentTimeMillis() > shootTime) {
          feeder.allOn();
        }
        logger.info("shooting");
      }

      logger.info("Area: %f  Horiz: %f  Vert: %f",
        Vision.Entry.AREA.getValue(), Vision.Entry.HORIZONTAL_LENGTH.getValue(), Vision.Entry.VERTICAL_LENGTH.getValue());
      logger.info("Distance from area: %f  horiz: %f  vert: %f",
        vision.getDistanceFromArea(), vision.getDistanceFromHorizontal(), vision.getDistanceFromVertical());
    }
  }

  @Override
  public void end(boolean interrupted) {
    shooter.shooterOff();
    shooter.acceleratorOff();
    feeder.allOff();
    // vision.turnOffCamLeds();
  }

  @Override
  public boolean isFinished() {
    return endCommandTime > 0 ? System.currentTimeMillis() > endCommandTime : false;
  }
}
