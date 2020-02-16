package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Turret;
import frc.robot.util.Logger;
import frc.robot.util.Logger.Level;

public class TurretTurn extends CommandBase {
  private static final Logger logger = new Logger(LockTarget.class.getName(), Level.DETAIL, false);

  private final Turret turret;
  private final NavX navx;
  private final double SPEED = 0.3;

  private double angle;
  private int position;
  private boolean isPositive; // or like is going right 
  private int target;

  public TurretTurn(Turret turret, NavX navx) {
    logger.detail("constructor");
    this.turret = turret;
    this.navx = navx;
    addRequirements(turret);
    addRequirements(navx);
  }

  @Override
  public void initialize() {
    logger.info("initialize");
    angle = navx.getAngle();
    position = turret.getPosition();
    if(angle>180 && (360-angle)>position){
      isPositive = true;
      target = position + 360 - (int)angle;
    } else {
      isPositive = false;
      target = position - (int)angle;
    }
  }
  
  @Override
  public void execute() {
    logger.info("execute");
    position = turret.getPosition();
    if(isPositive){
      turret.move(SPEED);
    } else {
      turret.move(-SPEED);
    }
  }

  @Override
  public void end(boolean interrupted) {
    logger.info("end");
    turret.move(0);
  }

  @Override
  public boolean isFinished() {
    return target == position;
  }
}
