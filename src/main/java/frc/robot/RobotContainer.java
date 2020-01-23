package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ClimbMove;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import frc.robot.util.Logger;

public class RobotContainer {
  private static final Logger logger = new Logger(RobotContainer.class.getName());

  // Motorized subsystems
  private final Drivetrain drivetrain;
  private final Intake intake;
  private final Feeder feeder;
  private final Shooter shooter;
  private final ControlPanel controlPanel;
  private final Climb climb;

  // Sensor subsystems
  private final Vision vision;
  private final NavX navx;
  private final ColorSensor colorSensor;

  // Controllers
  private final XboxController driverController;
  private final XboxController auxController;

  SendableChooser<Command> chooser = new SendableChooser<>();

  public RobotContainer() {
    drivetrain = new Drivetrain();
    intake = new Intake();
    feeder = new Feeder();
    shooter = new Shooter();
    controlPanel = new ControlPanel();
    climb = new Climb();

    vision = new Vision();
    navx = new NavX();
    colorSensor = new ColorSensor();

    driverController = new XboxController(Constants.DRIVE_CONTROLLER_PORT);
    auxController = new XboxController(Constants.AUX_CONTROLLER_PORT);
    configureButtonBindings();

    drivetrain.setDefaultCommand(new DriveCommand(drivetrain, driverController));
    climb.setDefaultCommand(new ClimbMove(climb, auxController));

//    chooser.addOption("Autonomous Command 1", new AutonomousCommand());
//    Shuffleboard.getTab("Autonomous").add(chooser);
  }

  private void configureButtonBindings() {
//    new JoystickButton(driverController, Button.kA.value).whenPressed(new TestCommand());
  }

  public Command getAutonomousCommand() {
    return chooser.getSelected();
  }
}
