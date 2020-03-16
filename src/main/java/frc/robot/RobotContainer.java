package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.ClimbExtendForward;
import frc.robot.commands.ClimbRetract;
import frc.robot.commands.ClimbReverse;
import frc.robot.commands.ControlPanelExtend;
import frc.robot.commands.ControlPanelRetract;
import frc.robot.commands.ControlPanelStage1;
import frc.robot.commands.ControlPanelTurn;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.FeederReverse;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.IntakeExtend;
import frc.robot.commands.IntakeJiggle;
import frc.robot.commands.IntakeRetract;
import frc.robot.commands.JustShoot;
import frc.robot.commands.TurretCommand;
import frc.robot.autonomous.DefaultSequence;
import frc.robot.autonomous.DoubleShoot;
import frc.robot.autonomous.SingleShoot;
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
  public final ControlPanel controlPanel;
  private final Climb climb;

  // Sensor subsystems
  private final Vision vision;
  public final NavX navx;
//  private final ColorSensor colorSensor;
  private final DigitalInput turretLimitSwitch;

  // Controllers
  private final XboxController driverController;
  private final XboxController auxController;

  SendableChooser<Command> autoChooser = new SendableChooser<Command>();
  SendableChooser<Double> speedChooser = new SendableChooser<Double>();

  public RobotContainer() {
    drivetrain = new Drivetrain();
    
    intake = new Intake();
    feeder = new Feeder();
    shooter = new Shooter();
    turret = new Turret();
    controlPanel = new ControlPanel();
    climb = new Climb();

    vision = new Vision();
    // vision.turnOffCamLeds();
    navx = new NavX();
    navx.reset();
//    colorSensor = new ColorSensor();
    turretLimitSwitch = new DigitalInput(0);
    
    driverController = new XboxController(Constants.DRIVE_CONTROLLER_PORT);
    auxController = new XboxController(Constants.AUX_CONTROLLER_PORT);
    configureButtonBindings();

    drivetrain.setDefaultCommand(new DriveCommand(drivetrain, driverController));
    climb.setDefaultCommand(new ClimbCommand(climb, auxController));
    turret.setDefaultCommand(new TurretCommand(turret, auxController, turretLimitSwitch));
    intake.setDefaultCommand(new IntakeCommand(intake, feeder, auxController));
    intake.off();
    feeder.allOff();
    shooter.allOff();

    speedChooser.addOption("0.6", 0.6);
    speedChooser.addOption("0.7", 0.7);
    speedChooser.addDefault("0.8", 0.8);
    speedChooser.addOption("0.9", 0.9);
    speedChooser.addOption("1.0", 1.0);
    SmartDashboard.putData("Shooter Speed", speedChooser);

    autoChooser.addOption("DoubleShoot", new DoubleShoot(vision, shooter, turret, feeder, drivetrain, intake, navx));
    autoChooser.addOption("SingleShoot", new SingleShoot(vision, shooter, turret, feeder, drivetrain, intake, navx));
    SmartDashboard.putData("Autonomous", autoChooser);
  
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
 * Raw Axis 0 = Left Joystick X Axis
 * Raw Axis 1 = Left Joystick Y Axis
 * Raw Axis 2 = Right Joystick X Axis
 * Raw Axis 3 = Right Joystick Y Axis
 * 11 = left stick
 * 12 = right stick 
 */

  private void configureButtonBindings() {
    // Driver buttons
    new JoystickButton(driverController, Button.kBumperLeft.value).whileHeld(new AutoShoot(vision, shooter, turret, feeder, -1, 0.8, 1000));
    new JoystickButton(driverController, Button.kBumperRight.value).whileHeld(new AutoShoot(vision, shooter, turret, feeder, -1, 0.9, 500));
    new JoystickButton(driverController, Button.kBack.value).whileHeld(new JustShoot(feeder, shooter));

    new JoystickButton(driverController, Button.kA.value).whileHeld(new ClimbExtendForward(climb));
    new JoystickButton(driverController, Button.kB.value).whileHeld(new ClimbReverse(climb));
    new JoystickButton(driverController, Button.kX.value).whileHeld(new ClimbRetract(climb));

    // Aux buttons
    new JoystickButton(auxController, Button.kBumperLeft.value).whenPressed(new IntakeExtend(intake));
    new JoystickButton(auxController, Button.kBack.value).whenPressed(new IntakeRetract(intake));

    new JoystickButton(auxController, Button.kBumperRight.value).whenPressed(new ControlPanelExtend(controlPanel));
    new JoystickButton(auxController, Button.kStart.value).whenPressed(new ControlPanelRetract(controlPanel));
    new JoystickButton(auxController, Button.kB.value).whileHeld(new ControlPanelTurn(controlPanel));
    new JoystickButton(auxController, Button.kX.value).whenPressed(new ControlPanelStage1(controlPanel));
    //new JoystickButton(auxController, Button.kStickRight.value).whenPressed(new ControlPanelStage2(controlPanel, colorSensor));

    new JoystickButton(auxController, Button.kA.value).whileHeld(new FeederReverse(feeder));
    new JoystickButton(auxController, Button.kY.value).whenPressed(new IntakeJiggle(intake, feeder));
  }

  public Command getAutonomousCommand() {
    Command cmd = autoChooser.getSelected();
    if (cmd == null) {
      return new SingleShoot(vision, shooter, turret, feeder, drivetrain, intake, navx);
    }
    return cmd;
  }

  public void updateShooterSpeed() {
    shooter.setSpeed((Double) speedChooser.getSelected());
  }

  public void setCoastMode() {
    drivetrain.setCoastMode();
    climb.setCoastMode();
  }

  public void setBrakeMode() {
    drivetrain.setBrakeMode();
    climb.setBrakeMode();
  }

  public void retractIntake() {
    intake.retract();
    intake.off();
  }

  public void visionOff(){
    vision.turnOffCamLeds();
  }

  public void visionOn(){
    vision.turnOnCamLeds();
  }

  public void climbOff(){
    climb.stop();
  }
} 
