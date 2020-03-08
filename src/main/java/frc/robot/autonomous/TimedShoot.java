package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.AutoShoot;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;
import frc.robot.util.Logger;
import frc.robot.util.Logger.Level;

public class TimedShoot extends ParallelCommandGroup {

  private static final Logger logger = new Logger(TimedShoot.class.getName(), Level.INFO, false);

  public TimedShoot(Vision vision, Shooter shooter, Turret turret, Feeder feeder, Intake intake, long time) {
    addCommands(
      new AutoShoot(vision, shooter, turret, feeder, time, 0.8),
      new ShooterIntake(intake)
    );
    logger.info("TimedShoot executed");
  }
}
