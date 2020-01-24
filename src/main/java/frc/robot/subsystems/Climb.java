package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.EncoderType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Logger;
import frc.robot.util.MotorFactory;

public class Climb extends SubsystemBase {
  private static final Logger logger = new Logger(Climb.class.getName());

  private static final double EXTEND = 0.4;
  private static final double RETRACT = 0.4;

  private final WPI_TalonSRX extendTalon;
  private final WPI_TalonSRX retractTalon;
  private final WPI_VictorSPX horizontalVictor;

  private final Encoder enc;

  public Climb() {
    logger.detail("constructor");
    extendTalon = MotorFactory.makeTalon(Constants.CLIMB_EXTEND_TALON, "Climb Extend Talon");
    retractTalon = MotorFactory.makeTalon(Constants.CLIMB_RETRACT_TALON, "Climb Retract Talon");
    horizontalVictor = MotorFactory.makeVictor(Constants.CLIMB_HORIZONTAL_VICTOR, "Climb Horizontal Victor");
    // constructor: (int channelA, int channelB, boolean reverseDirection, CounterBase.EncodingType encodingType)
    enc = new Encoder(Constants.CLIMB_ENCODERA, Constants.CLIMB_ENCODERB, false, Encoder.EncodingType.k4X);
    enc.reset();
    // lol why VV
    enc.setDistancePerPulse(6*Math.PI/1024);
    }

  public void extend() {
    extendTalon.set(EXTEND);
    retractTalon.set(-RETRACT);
    logger.dashboard("climb extend", EXTEND);
    logger.dashboard("climb retract", -RETRACT);
  }

  public void retract() {
    extendTalon.set(-EXTEND);
    retractTalon.set(RETRACT);
    logger.dashboard("climb extend", -EXTEND);
    logger.dashboard("climb retract", RETRACT);
  }
  
  public void stop() {
    extendTalon.set(0);
    retractTalon.set(0);
    logger.dashboard("climb extend", 0);
    logger.dashboard("climb retract", 0);
  }

  public void move(double speed) {
    horizontalVictor.set(speed);
    logger.dashboard("climb move speed: %f", speed);
  }

  // encoder, but how does the logger work now?

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

  @Override
  public void periodic() {
  }
}
