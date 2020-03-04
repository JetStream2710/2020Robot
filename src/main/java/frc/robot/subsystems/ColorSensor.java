package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

  public boolean isColorMatch() {
    Color gameColor = getGameColor();
    if (gameColor == Color.UNKNOWN) {
      return false;
    }
    Color sensorColor = getColor();
    if (sensorColor == Color.UNKNOWN) {
      return false;
    }
    switch (gameColor) {
      case YELLOW: return sensorColor == Color.GREEN;
      case GREEN: return sensorColor == Color.YELLOW;
      case BLUE: return sensorColor == Color.RED;
      case RED: return sensorColor == Color.BLUE;
      default:
        return false;
    }
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

  public Color getGameColor() {
    Color color = Color.UNKNOWN;
    String gameData = DriverStation.getInstance().getGameSpecificMessage();
    if (gameData.length() > 0) {
      switch (gameData.charAt(0)) {
        case 'B':
          color = Color.BLUE;
          break;
        case 'G':
          color = Color.GREEN;
          break;
        case 'R':
          color = Color.RED;
          break;
        case 'Y':
          color = Color.YELLOW;
          break;
        default:
          break;
      }
    }
    if (color != Color.UNKNOWN) {
      SmartDashboard.putString("Game Color", color.toString());
    }
    return color;
  }
}
