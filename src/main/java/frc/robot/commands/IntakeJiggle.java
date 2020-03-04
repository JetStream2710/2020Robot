package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.util.Logger;

public class IntakeJiggle extends CommandBase {
  private static final Logger logger = new Logger(IntakeJiggle.class.getName());

  private final Feeder feeder;
  private final Intake intake;
  private long endTime;

  public IntakeJiggle(Intake intake, Feeder feeder) {
    this.feeder = feeder;
    this.intake = intake;
    addRequirements(feeder, intake);
  }

  @Override
  public void initialize() {
    endTime = System.currentTimeMillis() + 1000;
    intake.extend();
  }

  @Override
  public void execute() {
    feeder.feederReverse();
  }

  @Override
  public void end(boolean interrupted) {
    feeder.feederOff();
    intake.retract();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return System.currentTimeMillis() > endTime;
  }
}
