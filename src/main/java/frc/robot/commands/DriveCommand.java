package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Logger;

public class DriveCommand extends CommandBase {
  private static final Logger logger = new Logger(ClimbRetract.class.getName());

  private final Drivetrain drivetrain;
  private final XboxController controller;

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
    double moveSpeed = .7 * controller.getRawAxis(1);
    double rotateSpeed = -.7 * controller.getRawAxis(2);
    logger.detail("execute moveSpeed: %f  rotateSpeed: %f", moveSpeed, rotateSpeed);
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
