package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanel;
import frc.robot.util.Logger;

public class ControlPanelExtend extends CommandBase {
  private static final Logger logger = new Logger(ControlPanelExtend.class.getName());

  private final ControlPanel cp;
  
  public ControlPanelExtend(ControlPanel cp) {
    logger.detail("constructor");
    this.cp = cp;
    addRequirements(cp);
  }

  @Override
  public void initialize() {
    logger.info("initialize");
    cp.extend();
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
