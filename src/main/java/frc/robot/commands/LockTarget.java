package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;
import frc.robot.util.Logger;

public class LockTarget extends CommandBase {
  private static final Logger logger = new Logger(LockTarget.class.getName());

  private final Vision vision;
//  private final NavX navx;
  private final Shooter shooter;
  private final Turret turret;

  public LockTarget(Vision vision, Shooter shooter, Turret turret) {
    logger.detail("constructor");
    this.vision = vision;
//    this.navx = navx;
    this.shooter = shooter;
    this.turret = turret;
    addRequirements(vision);
//    addRequirements(navx);
    addRequirements(shooter);
    addRequirements(turret);
  }

  @Override
  public void initialize() {
    logger.detail("initialize");
  }

  @Override
  public void execute() {
    logger.info("execute");
    if (vision.hasValidTargets()){
      logger.info("has valid target");
      double offset = Vision.Entry.HORIZONTAL_OFFSET.getValue();
      if (offset > 2) {
        turret.move(0.3);
        logger.info("moving positive at offset: %f", offset);
      }
      else if (offset < -2){
        turret.move(-0.3);
        logger.info("moving negative at offset: %f", offset);
      }
    }
    /**
   * first receive information from vision about distance and from navx about angle
   * second turn turret according degrees
   * third move shooter up/down according to the distance and corresponding angle w ground
   * hold it there?
   */
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return Vision.Entry.HORIZONTAL_OFFSET.getValue() < 2 && Vision.Entry.HORIZONTAL_OFFSET.getValue() > -2;
  }
}
