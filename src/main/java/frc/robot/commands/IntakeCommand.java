package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.util.Logger;

public class IntakeCommand extends CommandBase {
  private static final Logger logger = new Logger(IntakeCommand.class.getName());

  private final Intake intake;
  private final Feeder feeder;
  private final XboxController auxController;

  private int dPov;
  
  public IntakeCommand(Intake intake, Feeder feeder, XboxController auxController) {
    logger.detail("constructor");
    this.intake = intake;
    this.auxController = auxController;
    this.feeder = feeder;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    dPov = auxController.getPOV();
    if (dPov == 0){
      // intake jiggle
      new IntakeJiggle(intake, feeder);
    } else if (dPov == 90){
      intake.on();
    } else if (dPov == 270){
      intake.reverse();
    }
    if (dPov == 180){
      intake.off();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
