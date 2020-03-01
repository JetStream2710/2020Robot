package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.IntakeOn;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

public class DoubleShoot extends SequentialCommandGroup {

  public DoubleShoot(Vision vision, Shooter shooter, Turret turret, Feeder feeder, Drivetrain drivetrain) {
    addCommands(
      new AutoShoot(vision, shooter, turret, feeder, 2000),
      new MoveDistance(drivetrain, -5));
  }
}
