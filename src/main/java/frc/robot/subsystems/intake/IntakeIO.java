package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
  @AutoLog
  public static class IntakeIOInputs {
    double intakeLeftVelocityRadPerSec = 0d;
    double intakeRightVelocityRadPerSec = 0d;
    double indexerVelocityRadPerSec = 0d;
    double intakeLeftAppliedVolts = 0d;
    double intakeRightAppliedVolts = 0d;
    double indexerAppliedVolts = 0d;
    double[] intakeLeftCurrentAmps = new double[] {};
    double[] intakeRightCurrentAmps = new double[] {};
    double[] indexerCurrentAmps = new double[] {};
  }

  default void updateInputs(IntakeIOInputs inputs) {}

  default void setIntakeVoltage(double volts) {}

  default void setIndexerVoltage(double volts) {}

  default void stop() {}
}
