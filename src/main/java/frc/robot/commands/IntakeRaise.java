package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.util.Logger;

public class IntakeRaise extends CommandBase {
  private static final Logger logger = new Logger(IntakeRaise.class.getName());

  private final Intake intake;

  public IntakeRaise(Intake intake) {
    logger.detail("constructor");
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    logger.info("initialized");
    intake.raise();
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
    return true;
  }
}
