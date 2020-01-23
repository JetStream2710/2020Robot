package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;
import frc.robot.util.Logger;

public class ClimbMove extends CommandBase {
  private static final Logger logger = new Logger(ClimbMove.class.getName());

  private final Climb climb;
  private final XboxController controller;

  public ClimbMove(Climb climb, XboxController controller) {
    logger.detail("constructor");
    this.climb = climb;
    this.controller = controller;
    addRequirements(climb);
  }

  @Override
  public void initialize() {
    logger.detail("initialize");
  }

  @Override
  public void execute() {
    double speed = -1 * controller.getY(Hand.kRight);
    logger.info("execute speed: ", speed);
    climb.move(speed);
     
  }

  @Override
  public void end(boolean interrupted) {
    logger.info("end");
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
