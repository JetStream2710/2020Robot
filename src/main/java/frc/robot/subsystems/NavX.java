package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Logger;

public class NavX extends SubsystemBase {

  private static final Logger logger = new Logger(NavX.class.getName());

  private AHRS ahrs;

  public NavX() {
    super();
    logger.detail("constructor");

    ahrs = new AHRS(SPI.Port.kMXP);
  }

  public double getAngle(){
    double angle = ahrs.getAngle();
    logger.info("getAngle called w angle: " + angle);
    return angle;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
