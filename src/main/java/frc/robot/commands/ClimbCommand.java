package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;
import frc.robot.util.Logger;

public class ClimbCommand extends CommandBase {
  private static final Logger logger = new Logger(ClimbCommand.class.getName());

  private final Climb climb;
  private final XboxController auxController;

  private static double value;

  public ClimbCommand(Climb climb, XboxController auxController) {
    logger.detail("constructor");
    this.climb = climb;
    this.auxController = auxController;
    addRequirements(climb);
  }

  @Override
  public void initialize() {
    logger.detail("initialize");
  }

  @Override
  public void execute() {
    logger.detail("execute");
    value = auxController.getRawAxis(3);
    logger.info("controller position: %f", value);
    if (value > 0.02){
      climb.extend();
    } else if (value < -0.02){
      climb.retract();
    }
  }

  @Override
  public void end(boolean interrupted) {
    climb.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
