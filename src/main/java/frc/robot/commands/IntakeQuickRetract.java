package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.util.Logger;

public class IntakeQuickRetract extends CommandBase {
  private static final Logger logger = new Logger(IntakeRetract.class.getName());

  private final Intake intake;

  public IntakeQuickRetract(Intake intake) { 
  logger.detail("constructor");
  this.intake = intake;
  addRequirements(intake);
}

  @Override
  public void initialize() {
    logger.info("initialize");
    intake.retract();
  }

  @Override
  public void execute() {
    logger.detail("execute");
  }

  @Override
  public void end(boolean interrupted) {
    intake.off();
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
