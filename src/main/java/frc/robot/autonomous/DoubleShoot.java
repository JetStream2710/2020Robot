package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.IntakeExtend;
import frc.robot.commands.IntakeQuickRetract;
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
//      new TimedShoot(vision, shooter, turret, feeder, intake, 4000),
      new AutoShoot(vision, shooter, turret, feeder, 3000, 0.8, 1000),
      new TurnDegrees(drivetrain, navx, 183),
      new IntakeExtend(intake),
      new MoveDistance(drivetrain, 14, 0.4),
      new IntakeQuickRetract(intake),
//      new WaitCommand(0.5),
      new TurnDegrees(drivetrain, navx, 183),
      new MoveDistance(drivetrain, 12, 1),
      new TimedShoot(vision, shooter, turret, feeder, intake, 10000)
    );
    logger.info("... DoubleShoot executed");
    }
}
