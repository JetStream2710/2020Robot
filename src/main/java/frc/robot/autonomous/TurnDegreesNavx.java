package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NavX;
import frc.robot.util.Logger;

public class TurnDegreesNavx extends CommandBase {
  private static final Logger logger = new Logger(TurnDegreesNavx.class.getName(), Logger.Level.DETAIL, false);

  private static final double MAX_OUTPUT = 0.5;
  private static final double MIN_OUTPUT = 0.2;
  private static final double OUTPUT_PER_DEGREE = 0.3 / 180;

  private final Drivetrain drivetrain;
  private final NavX navx;
  private final double targetAngle;
  private final boolean isTurningRight;

  private double output;
 
  public TurnDegreesNavx(Drivetrain drivetrain, NavX navx, double distanceInDegrees) {
    logger.detail("constructor");
    this.drivetrain = drivetrain;
    this.navx = navx;
    targetAngle = navx.getAngle() + distanceInDegrees;
    isTurningRight = distanceInDegrees > 0;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    logger.detail("initialize");

    if (isTurningRight) {
      drivetrain.arcadeDrive(0, MAX_OUTPUT);
    } else {
      drivetrain.arcadeDrive(0, -MAX_OUTPUT);
    }
  }

  @Override
  public void execute() {
    logger.detail("execute");

    if (isFinished()) {
      drivetrain.arcadeDrive(0, 0);
      return;
    }

    if (isTurningRight) {
      output = (targetAngle - navx.getAngle()) * OUTPUT_PER_DEGREE;
        // Check if we should decelerate
    } else {
      output = (navx.getAngle() - targetAngle) * OUTPUT_PER_DEGREE;
      // Check if we should decelerate
    }
    drivetrain.arcadeDrive(0, -output);
  }

  @Override
  public void end(boolean interrupted) {
    logger.info("end");
    drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isTurningRight
        ? (navx.getAngle() >= targetAngle)
        : (navx.getAngle() <= targetAngle);
  }
}
