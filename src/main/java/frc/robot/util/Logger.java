package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Logger{

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

  private final String name;
  private Level logLevel;
  private boolean logDashboard;

  public Logger(String name) {
    this(name, Level.WARNING, false);
  }

  public Logger(String name, Level logLevel, boolean logDashboard) {
    this.name = name;
    this.logLevel = logLevel;
    this.logDashboard = logDashboard;
  }

  public void detail(String s, Object... args) {
    log(name, Level.DETAIL, s, args);
  }

  public void info(String s, Object... args) {
    log(name, Level.INFO, s, args);
  }

  public void warning(String s, Object... args) {
    log(name, Level.WARNING, s, args);
  }

  public void severe(String s, Object... args) {
    log(name, Level.SEVERE, s, args);
  }

  private void log(String name, Level level, String s, Object[] args) {
    if (logLevel.value() >= level.value()){
      System.out.println(String.format("%s %s: %s", level.toString(), name, args != null && args.length > 0 ? String.format(s, args) : s));
    }
  }

  public void dashboard(String label, String s, Object... args) {
    String msg = args != null && args.length > 0 ? String.format(s, args) : s;
    if (logDashboard){
      System.out.println(String.format("%s %s %s: %s", Level.INFO.toString(), name, label, msg));
    }
    //SmartDashboard.putString(label, msg);
  }

  public void dashboard(String label, int i) {
    if (logDashboard){
      System.out.println(String.format("%s %s %s: %d", Level.INFO.toString(), name, label, i));
    }
    //SmartDashboard.putNumber(label, i);
  }

  public void dashboard(String label, double d) {
    if (logDashboard){
      System.out.println(String.format("%s %s %s: %f", Level.INFO.toString(), name, label, d));
    }
    //SmartDashboard.putNumber(label, d);
  }
}