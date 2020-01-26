package frc.robot.util;

import frc.robot.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Logger{

  public static final Map<String, Level> LOG_LEVELS = new HashMap<>();

  static {
    // Robot systems
    LOG_LEVELS.put(Robot.class.getName(), Level.INFO);
    LOG_LEVELS.put(RobotContainer.class.getName(), Level.INFO);
    LOG_LEVELS.put(MotorFactory.class.getName(), Level.INFO);

    // Subsystems
    LOG_LEVELS.put(Climb.class.getName(), Level.INFO);
    LOG_LEVELS.put(ColorSensor.class.getName(), Level.INFO);
    LOG_LEVELS.put(ControlPanel.class.getName(), Level.INFO);
    LOG_LEVELS.put(Drivetrain.class.getName(), Level.INFO);
    LOG_LEVELS.put(Feeder.class.getName(), Level.INFO);
    LOG_LEVELS.put(Intake.class.getName(), Level.INFO);
    LOG_LEVELS.put(NavX.class.getName(), Level.INFO);
    LOG_LEVELS.put(Shooter.class.getName(), Level.INFO);
    LOG_LEVELS.put(Turret.class.getName(), Level.INFO);
    LOG_LEVELS.put(Vision.class.getName(), Level.INFO);

    // Autonomous Commands

    // Commands
    LOG_LEVELS.put(DriveCommand.class.getName(), Level.INFO);
    LOG_LEVELS.put(ClimbExtend.class.getName(), Level.INFO);
    LOG_LEVELS.put(ClimbMove.class.getName(), Level.INFO);
    LOG_LEVELS.put(ClimbRetract.class.getName(), Level.INFO);
    LOG_LEVELS.put(ControlPanelExtend.class.getName(), Level.INFO);
    LOG_LEVELS.put(ControlPanelRetract.class.getName(), Level.INFO);
    LOG_LEVELS.put(ControlPanelTurn.class.getName(), Level.INFO);
    LOG_LEVELS.put(DriveCommand.class.getName(), Level.INFO);
    LOG_LEVELS.put(FeederAllOn.class.getName(), Level.INFO);
    LOG_LEVELS.put(FeederHorizontalOn.class.getName(), Level.INFO);
    LOG_LEVELS.put(FeederReverse.class.getName(), Level.INFO);
    LOG_LEVELS.put(FeederVerticalOn.class.getName(), Level.INFO);
    LOG_LEVELS.put(IntakeLower.class.getName(), Level.INFO);
    LOG_LEVELS.put(IntakeOn.class.getName(), Level.INFO);
    LOG_LEVELS.put(IntakeRaise.class.getName(), Level.INFO);
    LOG_LEVELS.put(IntakeReverse.class.getName(), Level.INFO);
    LOG_LEVELS.put(LockTarget.class.getName(), Level.INFO);
    LOG_LEVELS.put(ShooterOn.class.getName(), Level.INFO);
  }

  public enum Level{
    OFF(0),
    SEVERE(1),
    WARNING(2),
    INFO(3),
    DETAIL(4);

    private final int value;

    Level(int value){
      this.value = value;
    }

    public int value(){
      return value;
    }
  }

  private String name;

  public Logger(String name){
    this.name = name;
  }

  public void detail(String s, Object... args){
    log(name, Level.DETAIL, s, args);
  }

  public void info(String s, Object... args){
    log(name, Level.INFO, s, args);
  }

  public void warning(String s, Object... args){
    log(name, Level.WARNING, s, args);
  }

  public void severe(String s, Object... args){
    log(name, Level.SEVERE, s, args);
  }

  private void log(String name, Level level, String s, Object[] args){
    Level def = LOG_LEVELS.get(name);
    if (def == null) {
      def = Level.INFO;
    }
    if (def.value() >= level.value()){
      System.out.println(String.format("%s %s: %s", level.toString(), name, args != null && args.length > 0 ? String.format(s, args) : s));
    }
  }

  public void dashboard(String label, String s, Object... args){
    String msg = args != null && args.length > 0 ? String.format(s, args) : s;
    if (LOG_LEVELS.get(name).value() >= Level.INFO.value()){
      //System.out.println(String.format("%s %s %s: %s", Level.INFO.toString(), name, label, msg));
    }
    SmartDashboard.putString(label, msg);
  }

  public void dashboard(String label, int i) {
    if (LOG_LEVELS.get(name).value() >= Level.INFO.value()){
      //System.out.println(String.format("%s %s %s: %d", Level.INFO.toString(), name, label, i));
    }
    SmartDashboard.putNumber(label, i);
  }

  public void dashboard(String label, double d) {
    if (LOG_LEVELS.get(name).value() >= Level.INFO.value()){
      //System.out.println(String.format("%s %s %s: %f", Level.INFO.toString(), name, label, d));
    }
    SmartDashboard.putNumber(label, d);
  }
}