package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.shooter.Shooter;
import java.util.function.DoubleSupplier;

public class ShooterCommands {
  public ShooterCommands() {}

  public static Command triggerShoot(
      Shooter shooter, DoubleSupplier lTrigger, DoubleSupplier rTrigger) {
    return Commands.run(
        () -> {
          shooter.setMotor(rTrigger.getAsDouble() * 11 - lTrigger.getAsDouble() * 11);
        },
        shooter);
  }
}
