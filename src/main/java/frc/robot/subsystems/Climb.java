package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;

public class Climb extends SubsystemBase {

  Logger logger = new Logger(Feeder.class.getName());

  private WPI_TalonSRX verticalTalon;
  private WPI_VictorSPX horizontalVictor;

  public Climb() {
    super();

    logger.detail("constructor");

    verticalTalon = new WPI_TalonSRX(Constants.CLIMB_VERTICAL_TALON);
    horizontalVictor = new WPI_VictorSPX(Constants.CLIMB_HORIZONTAL_VICTOR);

    verticalTalon.setSafetyEnabled(false);
    horizontalVictor.setSafetyEnabled(false);

    verticalTalon.setNeutralMode(NeutralMode.Brake);
    horizontalVictor.setNeutralMode(NeutralMode.Brake);

    verticalTalon.configVoltageCompSaturation(12);
    horizontalVictor.configVoltageCompSaturation(12);

    verticalTalon.enableVoltageCompensation(true);
    horizontalVictor.enableVoltageCompensation(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
