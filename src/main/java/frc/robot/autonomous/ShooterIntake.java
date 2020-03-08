package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.IntakeExtend;
import frc.robot.commands.IntakeQuickRetract;
import frc.robot.commands.IntakeRetract;
import frc.robot.subsystems.Intake;
import frc.robot.util.Logger;
import frc.robot.util.Logger.Level;

public class ShooterIntake extends SequentialCommandGroup {

  private static final Logger logger = new Logger(ShooterIntake.class.getName(), Level.INFO, false);

  public ShooterIntake(Intake intake) {
    addCommands(
//      new WaitCommand(2),
      new IntakeExtend(intake),
      new WaitCommand(1),
      new IntakeQuickRetract(intake)
      // new WaitCommand(1),
      // new IntakeExtend(intake),
      // new WaitCommand(1),
      // new IntakeRetract(intake)
    );
    logger.info("ShooterIntake executed");
 }
}
