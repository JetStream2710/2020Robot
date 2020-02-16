package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;
import frc.robot.util.Logger;

public class AutoShoot extends SequentialCommandGroup {

  public AutoShoot(Vision vision, Shooter shooter, Turret turret, Feeder feeder) {
    super(new LockTarget(vision, shooter, turret), new ShooterOn(shooter, feeder));
  }
}
