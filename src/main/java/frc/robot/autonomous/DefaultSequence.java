package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;

public class DefaultSequence extends SequentialCommandGroup {

  public DefaultSequence(Drivetrain drivetrain) {
    addCommands(
        // move forward 2 feet
        new MoveDistance(drivetrain, 2)

        // turn right 90 degrees
        // new TurnDegrees(drivetrain, navx, 90)
    );
    addRequirements(drivetrain);
  }
}