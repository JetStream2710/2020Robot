package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Logger;

public class NavX extends SubsystemBase implements DoubleSupplier {
  private static final Logger logger = new Logger(NavX.class.getName());

  private final AHRS ahrs;
  private double offset;

  public NavX() {
    logger.detail("constructor");
    ahrs = new AHRS(SPI.Port.kMXP);
  }

  public double getAngle() {
    double angle = ahrs.getAngle();
    logger.dashboard("navx angle", angle);
    return angle;
  }

  public void zeroYaw() {
    ahrs.zeroYaw();
  }
  
  public void reset() {
    ahrs.reset();
    logger.dashboard("navx offset", offset);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public double getAsDouble() {
    return getAngle();
  }
}
