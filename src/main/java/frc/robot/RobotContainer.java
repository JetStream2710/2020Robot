package frc.robot;

import java.util.HashSet;
import java.util.Set;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ClimbMove;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.LockTarget;
import frc.robot.commands.ShooterOff;
import frc.robot.commands.ShooterOn;
import frc.robot.commands.TurretCommand;
import frc.robot.autonomous.DefaultSequence;
import frc.robot.autonomous.MoveDistance;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;
import frc.robot.util.CANDeviceFinder;
import frc.robot.util.Logger;

public class RobotContainer {
  private static final Logger logger = new Logger(RobotContainer.class.getName());

  // Motorized subsystems
  private final Drivetrain drivetrain;
  
//  private final Intake intake;
  private final Feeder feeder;
  private final Shooter shooter;
  private final Turret turret;
//  private final ControlPanel controlPanel;
//  private final Climb climb;

  // Sensor subsystems
  private final Vision vision;
//  private final NavX navx;
//  private final ColorSensor colorSensor;


  // Controllers
  private final XboxController driverController;
  private final XboxController auxController;

  SendableChooser<Command> chooser = new SendableChooser<>();

  public RobotContainer() {
    drivetrain = new Drivetrain();
    
//    intake = new Intake();
    feeder = new Feeder();
    shooter = new Shooter();
    turret = new Turret();
//    controlPanel = new ControlPanel();
//    climb = new Climb();

    vision = new Vision();
//    navx = new NavX();
//    colorSensor = new ColorSensor();
    
    driverController = new XboxController(Constants.DRIVE_CONTROLLER_PORT);
    auxController = new XboxController(Constants.AUX_CONTROLLER_PORT);
    configureButtonBindings();

    drivetrain.setDefaultCommand(new DriveCommand(drivetrain, driverController));
    drivetrain.setCoastMode();
    //climb.setDefaultCommand(new ClimbMove(climb, auxController));
    turret.setDefaultCommand(new TurretCommand(turret, auxController));

    /*
    chooser.addOption("Coast Mode", new Command(){
      @Override
      public Set<Subsystem> getRequirements() {
        Set<Subsystem> subsystems = new HashSet<>();
        subsystems.add(drivetrain);
        return subsystems;
      }
      @Override
      public void initialize() {
        logger.dashboard("Brake Mode", "COAST");
        setCoastMode();
      }
    });

    chooser.addOption("Brake Mode", new Command(){
      @Override
      public Set<Subsystem> getRequirements() {
        Set<Subsystem> subsystems = new HashSet<>();
        subsystems.add(drivetrain);
        return subsystems;
      }
      @Override
      public void initialize() {
        logger.dashboard("Brake Mode", "BRAKE");
        setCoastMode();
      }
    });
    */

    //chooser.addOption("Autonomous Command 1", new MoveDistance(drivetrain, 1));
    //Shuffleboard.getTab("Autonomous").add(chooser);

    CANDeviceFinder can = new CANDeviceFinder();
    can.debug();
  }

  private void configureButtonBindings() {
    new JoystickButton(driverController, Button.kA.value).whileHeld(new ShooterOn(shooter, feeder));
//    // new JoystickButton(driverController, Button.kB.value).whileHeld(new LockTarget(vision, shooter, turret));
    new JoystickButton(driverController, Button.kB.value).whileHeld(new LockTarget(vision, shooter, turret));
  }

  public Command getAutonomousCommand() {
    return new MoveDistance(drivetrain, 10);
  }

  public void setCoastMode() {
    drivetrain.setCoastMode();
  }

  public void setBrakeMode() {
    drivetrain.setBrakeMode();
  }
}
