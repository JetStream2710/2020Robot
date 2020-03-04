package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.IntakeExtend;
import frc.robot.commands.IntakeRetract;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

public class DoubleShoot extends SequentialCommandGroup {

  public DoubleShoot(Vision vision, Shooter shooter, Turret turret, Feeder feeder, Drivetrain drivetrain, Intake intake, NavX navx) {
    addCommands(
      new AutoShoot(vision, shooter, turret, feeder, 1000),
      new IntakeExtend(intake),
      new AutoShoot(vision, shooter, turret, feeder, 1000),
      new IntakeRetract(intake),
      new AutoShoot(vision, shooter, turret, feeder, 2000),
      new TurnDegrees(drivetrain, navx, 168),
      new IntakeExtend(intake),
      new MoveDistance(drivetrain, 13),
      new IntakeRetract(intake),
      new TurnDegrees(drivetrain, navx, 174),
      new MoveDistance(drivetrain, 10),
      new AutoShoot(vision, shooter, turret, feeder, 1000),
      new IntakeExtend(intake),
      new AutoShoot(vision, shooter, turret, feeder, 1000),
      new IntakeRetract(intake),
      new AutoShoot(vision, shooter, turret, feeder, 2000));
  }
}
