package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanel;
import frc.robot.util.Logger;

public class ControlPanelStage1 extends CommandBase {
  private static final Logger logger = new Logger(ControlPanelStage1.class.getName(), Logger.Level.DETAIL, true);

  private final ControlPanel cp;
  private static final int SPINS = 4;
  private static final int ENCODER_UNITS_PER_SPIN = 24000*8;

  private static int position;
  private static int target;

  public ControlPanelStage1(ControlPanel cp) {
    logger.detail("constructor");
    this.cp = cp;
    addRequirements(cp);
  }

  @Override
  public void initialize() {
    position = cp.getPosition();
    target = position + SPINS*ENCODER_UNITS_PER_SPIN;
  }

  @Override
  public void execute() {
    position = cp.getPosition();
    cp.turn();
//    logger.info("execute position: %f", position);
  }

  @Override
  public void end(boolean interrupted) {
    cp.turnStop();
  }

  @Override
  public boolean isFinished() {
    System.out.println("position: " + position + " target: " + target);
    return position >= target;
  }
}
