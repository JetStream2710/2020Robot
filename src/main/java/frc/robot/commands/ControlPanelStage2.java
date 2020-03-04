package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.ControlPanel;
import frc.robot.util.Logger;

public class ControlPanelStage2 extends CommandBase {
  private static final Logger logger = new Logger(ControlPanelStage2.class.getName(), Logger.Level.INFO, true);

  private final ControlPanel cp;
  private final ColorSensor sensor;

  public ControlPanelStage2(ControlPanel cp, ColorSensor sensor) {
    logger.detail("constructor");
    this.cp = cp;
    this.sensor = sensor;
    addRequirements(cp, sensor);
  }

  @Override
  public void initialize() {
    cp.turn();
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    cp.turnStop();
  }

  @Override
  public boolean isFinished() {
    return sensor.isColorMatch();
  }
}
