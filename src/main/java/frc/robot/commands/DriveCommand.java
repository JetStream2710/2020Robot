package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Logger;

public class DriveCommand extends CommandBase {

  private static final Logger logger = new Logger(ClimbRetract.class.getName());

  private final Drivetrain drivetrain;

  public DriveCommand(Drivetrain drivetrain) {
    logger.detail("constructor");
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    logger.detail("initialize");
  }

  @Override
  public void execute() {
    double moveSpeed = -1 * RobotContainer.joystick.getRawAxis(Constants.DRIVER_MOVE_AXIS);
    double rotateSpeed = -1* RobotContainer.joystick.getRawAxis(Constants.DRIVER_ROTATE_AXIS);
    logger.detail("execute moveSpeed: " + moveSpeed + " rotateSpeed: " rotateSpeed);
    drivetrain.arcadeDrive(moveSpeed, rotateSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    logger.info("end");
    drivetrain.arcadeDrive(0, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
