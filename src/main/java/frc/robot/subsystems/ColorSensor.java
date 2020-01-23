package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Logger;

public class ColorSensor extends SubsystemBase {
  private static final Logger logger = new Logger(ColorSensor.class.getName());
  
  public enum Color{
    RED,
    GREEN,
    BLUE,
    YELLOW,
    UNKNOWN
  }

  private final ColorSensorV3 colorSensor;

  public ColorSensor() {
    logger.detail("constructor");
    colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
  }

  public Color getColor() {
    double red = colorSensor.getRed();
    double green = colorSensor.getGreen();
    double blue = colorSensor.getBlue();
    int proximity = colorSensor.getProximity();

    logger.detail("ADC Red: %f", red);
    logger.detail("ADC Green: %f", green);
    logger.detail("ADC Blue: %f", blue);
    logger.detail("ADC Proximity: %d", proximity);

    /**
     * blue: blue and green within 10 percent,, red less than 50%
     * green: green :D  w red and blue similar (less than 50&)
     * red: red slightly higher than green
     * yellow: red at 50% value of green,, blue much smaller than red
     */

    double max = red;
    if (green > max) {
      max = green;
    }
    if (blue > max) {
      max = blue;
    }
    boolean hasRed = red == max;
    boolean hasBlue = blue >= max * 0.9;
    boolean hasGreen = green == max && red < max * 0.5 && blue < max * 0.5;
    boolean hasYellow = green == max && red > max * 0.5 && red < max * 0.8 && blue < max * 0.5;
 
    // Test code for the color sensor
    if (hasYellow) {
      logger.dashboard("Color", "Yellow");
      return Color.YELLOW;
    }
    else if (hasRed) {
      logger.dashboard("Color", "Red");
     return Color.RED;
    }
    else if (hasGreen) {
      logger.dashboard("Color", "Green");
     return Color.GREEN;
    }
    else if (hasBlue) {
      logger.dashboard("Color", "Blue");
     return Color.BLUE;
    }
    else {
      logger.dashboard("Color", "Unknown");
      return Color.UNKNOWN;
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
