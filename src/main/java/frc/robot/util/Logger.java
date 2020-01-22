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

  public void detail(String s){
    log(name, Level.DETAIL, s);
  }

  public void info(String s){
    log(name, Level.INFO, s);
  }

  public void warning(String s){
    log(name, Level.WARNING, s);
  }

  public void severe(String s){
    log(name, Level.SEVERE, s);
  }

  private void log(String name, Level level, String s){
    if (LOG_LEVELS.get(name).value() >= level.value()){
      SmartDashboard.putString(level.toString(), String.format("%s: %s", name, s));
    }
  }

  public void dashboard(String label, String s){
    if (LOG_LEVELS.get(name).value() >= Level.INFO.value()){
      System.out.println(String.format("%s %s: %s", Level.INFO.toString(), name, s));
    }
    SmartDashboard.putString(label, s);
  }

  public void dashboard(String label, int i){
    if (LOG_LEVELS.get(name).value() >= Level.INFO.value()){
      System.out.println(String.format("%s %s: %d", Level.INFO.toString(), name, i));
    }
    SmartDashboard.putNumber(label, i);
  }

  public void dashboard(String label, double d){
    if (LOG_LEVELS.get(name).value() >= Level.INFO.value()){
      System.out.println(String.format("%s %s: %f", Level.INFO.toString(), name, d));
    }
    SmartDashboard.putNumber(label, d);
  }
}