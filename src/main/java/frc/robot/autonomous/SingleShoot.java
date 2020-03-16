package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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

public class SingleShoot extends SequentialCommandGroup {
  private static final Logger logger = new Logger(DoubleShoot.class.getName(), Level.INFO, false);
  
  public SingleShoot(Vision vision, Shooter shooter, Turret turret, Feeder feeder, Drivetrain drivetrain, Intake intake, NavX navx) {
    logger.info("DoubleShoot beginning...");
    addCommands(
      new TimedShoot(vision, shooter, turret, feeder, intake, 10000),
      new TurnDegrees(drivetrain, navx, 180),
      new MoveDistance(drivetrain, 4, 0.6)
    );
    logger.info("... DoubleShoot executed");
    }
}
