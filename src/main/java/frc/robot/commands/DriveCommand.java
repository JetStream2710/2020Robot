package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Logger;
import frc.robot.util.Logger.Level;

public class DriveCommand extends CommandBase {
  private static final Logger logger = new Logger(DriveCommand.class.getName(), Level.WARNING, false);

  private final Drivetrain drivetrain;
  private final XboxController controller;

  private int brakeCounter;

  public DriveCommand(Drivetrain drivetrain, XboxController controller) {
    logger.detail("constructor");
    this.drivetrain = drivetrain;
    this.controller = controller;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    logger.detail("initialize");
  }

  @Override
  public void execute() {
    double moveSpeed = 1 * controller.getRawAxis(1);
    double rotateSpeed = -1 * controller.getRawAxis(2);
    double currentSpeed = drivetrain.getSpeed();

    logger.info("execute moveSpeed: %f  rotateSpeed: %f  speed: %f", moveSpeed, rotateSpeed, currentSpeed);
    drivetrain.arcadeDrive(moveSpeed, rotateSpeed);

/*
    if (moveSpeed > 0.02 || moveSpeed < -0.02) {
      logger.info("execute moveSpeed: %f  rotateSpeed: %f  speed: %f", moveSpeed, rotateSpeed, currentSpeed);
      drivetrain.arcadeDrive(moveSpeed, rotateSpeed);
//      drivetrain.moveSpecific(moveSpeed, 3); // testing each motor individually
      brakeCounter = 0;
    } else {
      // First version of anti-skid algorithm. Need to factor in speed.
      //logger.info("braking speed: %f", drivetrain.getSpeed());
      drivetrain.arcadeDrive(0.0, 0.0);
      if (currentSpeed < 50) {
        drivetrain.setBrakeMode();
      } else {
        int brakingPower = 200;//(int) currentSpeed;//(int) Math.sqrt(currentSpeed);
        if (brakeCounter % brakingPower == 0) {
          drivetrain.setBrakeMode();
        } else {
          drivetrain.setCoastMode();
        }
        brakeCounter++;
      }
    }
*/
  }

  @Override
  public void end(boolean interrupted) {
    logger.detail("end");
    drivetrain.arcadeDrive(0, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
