package frc.robot.subsystems.climber;

import org.littletonrobotics.junction.AutoLog;

public interface ClimberIO {
  @AutoLog
  public static class ClimberIOInputs {
    double climberLeftVelocityRadPerSec = 0d;
    double climberRightVelocityRadPerSec = 0d;
    double climberLeftAppliedVolts = 0d;
    double climberRightAppliedVolts = 0d;
    double[] climberLeftCurrentAmps = new double[] {};
    double[] climberRightCurrentAmps = new double[] {};
  }

  default void updateInputs(ClimberIOInputs inputs) {}

  default void setClimberVoltage(double volts) {}

  default void stop() {}
}
