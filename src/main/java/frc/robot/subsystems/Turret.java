package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class Turret extends SubsystemBase {
  private static final Logger logger = new Logger(Turret.class.getName());

  private final WPI_TalonSRX talon;

//  private final Encoder enc;

  public Turret() {
    logger.detail("constructor");
    talon = MotorFactory.makeTalon(Constants.TURRET_TALON, "Turret Talon");
//    enc = new Encoder(Constants.TURRET_ENCODERA, Constants.TURRET_ENCODERB, false, Encoder.EncodingType.k4X);
//    enc.reset();
//    enc.setDistancePerPulse(6*Math.PI/1024);
  }

  public void move(double speed) {
    talon.set(speed);
    logger.dashboard("turret speed", speed);
  }

  public void moveStop() {
    talon.set(0);
    logger.dashboard("turret speed", 0);
  }

  public int getPosition() {
    int position = talon.getSelectedSensorPosition();
    logger.dashboard("turret position", position);
    return position;
  }

/*
  public int getCount(){
    int count = enc.get();
    logger.dashboard("climb encoder count: %d" , count);
    return count;
  }

  public int getRaw(){
    int raw = enc.getRaw();
    logger.dashboard("climb encoder raw: %d" , raw);
    return raw;
  }

  public double getRate(){
    double rate = enc.getRate();
    logger.dashboard("climb encoder rate: %f" , rate);
    return rate;
  }

  public double getDistance(){
    double distance = enc.getDistance();
    logger.dashboard("climb encoder distance: %f" , distance);
    return distance;
  }
*/

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
