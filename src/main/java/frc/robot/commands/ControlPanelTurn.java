package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanel;
import frc.robot.util.Logger;

public class ControlPanelTurn extends CommandBase {

  private static final Logger logger = new Logger(ControlPanelTurn.class.getName());

  private final ControlPanel cp;

  public ControlPanelTurn(ControlPanel cp) {
    logger.detail("constructor");
    this.cp = cp;
    addRequirements(cp);  
  }

  @Override
  public void initialize() {
    logger.info("initialize");
    cp.turn();
  }

  @Override
  public void execute() {
    logger.detail("execute");
  }

  @Override
  public void end(boolean interrupted) {
    logger.info("end");
    cp.turnStop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
