package frc.robot;

import java.util.HashSet;
import java.util.Set;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.ClimbMove;
import frc.robot.commands.ControlPanelExtend;
import frc.robot.commands.ControlPanelRetract;
import frc.robot.commands.ControlPanelTurn;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.IntakeLower;
import frc.robot.commands.IntakeOn;
import frc.robot.commands.IntakeRaise;
import frc.robot.commands.LockTarget;
import frc.robot.commands.ShooterOff;
import frc.robot.commands.ShooterOn;
import frc.robot.commands.ShooterTrigger;
import frc.robot.commands.TurretCommand;
import frc.robot.autonomous.DefaultSequence;
import frc.robot.autonomous.MoveDistance;
import frc.robot.autonomous.TurnDegrees;
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
  
  private final Intake intake;
  private final Feeder feeder;
  private final Shooter shooter;
  private final Turret turret;
  private final ControlPanel controlPanel;
  //private final Climb climb;

  // Sensor subsystems
  private final Vision vision;
  private final NavX navx;
//  private final ColorSensor colorSensor;
  private final DigitalInput turretLimitSwitch;


  // Controllers
  private final XboxController driverController;
  private final XboxController auxController;

  SendableChooser<Command> chooser = new SendableChooser<>();

  public RobotContainer() {
    drivetrain = new Drivetrain();
    
    intake = new Intake();
    feeder = new Feeder();
    shooter = new Shooter();
    turret = new Turret();
    controlPanel = new ControlPanel();
    //climb = new Climb();

    vision = new Vision();
    navx = new NavX();
//    colorSensor = new ColorSensor();
    turretLimitSwitch = new DigitalInput(0);
    
    driverController = new XboxController(Constants.DRIVE_CONTROLLER_PORT);
    auxController = new XboxController(Constants.AUX_CONTROLLER_PORT);
    configureButtonBindings();

    drivetrain.setDefaultCommand(new DriveCommand(drivetrain, driverController));
    drivetrain.setCoastMode();
    //climb.setDefaultCommand(new ClimbMove(climb, auxController));
    turret.setDefaultCommand(new TurretCommand(turret, auxController, turretLimitSwitch));

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

/***
 * Button names and Controller
 * kA = X
 * kB = A
 * kX = B
 * kY = Y
 * kBumperLeft = left bumper
 * kBumperRight = right bumper
 * kBack = left trigger
 * kStart = right trigger
 * kStickLeft = back
 * kStickRight = start
 * 11 = left stick
 * 12 = right stick 
 */

  private void configureButtonBindings() {
//    new JoystickButton(driverController, Button.kA.value).whileHeld(new ShooterOn(shooter, feeder));
//    // new JoystickButton(driverController, Button.kB.value).whileHeld(new LockTarget(vision, shooter, turret));
    new JoystickButton(driverController, Button.kB.value).whileHeld(new LockTarget(vision, shooter, turret));
//    new JoystickButton(driverController, Button.kX.value).whileHeld(new ShooterTrigger(shooter));
    new JoystickButton(driverController, Button.kBumperLeft.value).whileHeld(new ShooterOn(shooter, feeder));
    new JoystickButton(driverController, Button.kBack.value)
      .whenPressed(new AutoShoot(vision, shooter, turret, feeder))
      .whenReleased(new ShooterOff(shooter));

    new JoystickButton(auxController, Button.kB.value).whileHeld(new IntakeOn(intake));
    new JoystickButton(auxController, Button.kBumperLeft.value).whenPressed(new IntakeRaise(intake));
    new JoystickButton(auxController, Button.kBack.value).whenPressed(new IntakeLower(intake));
    new JoystickButton(auxController, Button.kBumperRight.value).whenPressed(new ControlPanelExtend(controlPanel));
    new JoystickButton(auxController, Button.kStart.value).whenPressed(new ControlPanelRetract(controlPanel));
    new JoystickButton(auxController, Button.kX.value).whileHeld(new ControlPanelTurn(controlPanel));
  }

  public Command getAutonomousCommand() {
    return null;//new TurnDegrees(drivetrain, 180);
  }

  public void setCoastMode() {
    drivetrain.setCoastMode();
  }

  public void setBrakeMode() {
    drivetrain.setBrakeMode();
  }
} 
