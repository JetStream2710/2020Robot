package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.util.Logger;
import frc.robot.util.Logger.Level;

public class IntakeCommand extends CommandBase {
  private static final Logger logger = new Logger(IntakeOn.class.getName(), Level.SEVERE, false);

  private final Intake intake;

  public IntakeCommand(Intake intake) {
    logger.detail("constructor");
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    logger.detail("initialize");
  }

  @Override
  public void execute() {
    logger.detail("execute");
    if(intake.isRaised){
      intake.on();
    } else{
      intake.off();
    }
  }

  @Override
  public void end(boolean interrupted) {
    logger.info("end");
    intake.off();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
