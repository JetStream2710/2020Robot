package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class ControlPanel extends SubsystemBase implements DoubleSupplier {
  private static final Logger logger = new Logger(ControlPanel.class.getName());

  private static final double TURN_SPEED = 0.4;

  private final WPI_TalonSRX turnTalon;
  private final Solenoid solenoid;

  public ControlPanel() {
    logger.detail("constructor");
    solenoid = new Solenoid(Constants.PCM_NODE, Constants.CONTROL_PANEL_SOLENOID);
    turnTalon = MotorFactory.makeTalon(Constants.CONTROL_PANEL_TALON , "Control Panel Turn Talon");
  }

  public void turn() {
    turnTalon.set(TURN_SPEED);
    System.out.println("position: " + getPosition());
    logger.dashboard("control panel turn speed", TURN_SPEED);
  }

  public void turnStop() {
    turnTalon.set(0);
    logger.dashboard("control panel turn speed", 0);
  }

  public void extend() {
    solenoid.set(true);
    logger.info("control panel extended", "yes");
  }

  public void retract() {
    solenoid.set(false);
    logger.info("control panel extended", "no");
  }

  public int getPosition() {
    int position = turnTalon.getSelectedSensorPosition();
    logger.detail("position: %d", position);
    return position;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public double getAsDouble() {
    return getPosition();
  }
}
