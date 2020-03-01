package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.util.Logger;
import frc.robot.util.Logger.Level;

public class IntakeCommand extends CommandBase {
  private static final Logger logger = new Logger(IntakeCommand.class.getName(), Level.SEVERE, false);

  private final Intake intake;
  private boolean isOn;
  private long stopTime;

  public IntakeCommand(Intake intake) {
    logger.detail("constructor");
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    logger.detail("initialize");
    if (!isOn) {
      intake.retract();
      intake.on();
      isOn = true;
      stopTime = 0;
    } else {
      intake.extend();
      if (stopTime == 0) {
        stopTime = System.currentTimeMillis() + 500;
      }
    }
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    logger.info("end");
    if (stopTime != 0) {
      intake.off();
      isOn = false;
      stopTime = 0;
    }
  }

  @Override
  public boolean isFinished() {
    return System.currentTimeMillis() > stopTime;
  }
}
