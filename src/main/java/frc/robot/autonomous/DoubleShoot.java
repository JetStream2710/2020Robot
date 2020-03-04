package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.IntakeExtend;
import frc.robot.commands.IntakeRetract;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;
import frc.robot.util.Logger;
import frc.robot.util.Logger.Level;

public class DoubleShoot extends SequentialCommandGroup {

  private static final Logger logger = new Logger(DoubleShoot.class.getName(), Level.INFO, false);

  public DoubleShoot(Vision vision, Shooter shooter, Turret turret, Feeder feeder, Drivetrain drivetrain, Intake intake, NavX navx) {
    logger.info("DoubleShoot beginning...");
    addCommands(
      new TimedShoot(vision, shooter, turret, feeder, intake),
      new TurnDegrees(drivetrain, navx, 168),
      new IntakeExtend(intake),
      new MoveDistance(drivetrain, 13),
      new IntakeRetract(intake),
      new TurnDegrees(drivetrain, navx, 174),
      new MoveDistance(drivetrain, 10),
      new TimedShoot(vision, shooter, turret, feeder, intake)
    );
    logger.info("... DoubleShoot executed");
    }
}
