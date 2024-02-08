package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
  @AutoLog
  public static class IntakeIOInputs {
    double intakeLeftVelocityRadPerSec = 0d;
    double intakeRightVelocityRadPerSec = 0d;
    double intakeFeederVelocityRadPerSec = 0d;
    double intakeLeftAppliedVolts = 0d;
    double intakeRightAppliedVolts = 0d;
    double intakeFeederAppliedVolts = 0d;
    double[] intakeLeftCurrentAmps = new double[] {};
    double[] intakeRightCurrentAmps = new double[] {};
    double[] intakeFeederCurrentAmps = new double[] {};
  }

  default void updateInputs(IntakeIOInputs inputs) {}

  default void setIntakePositionVoltage(double volts) {}

  default void setIntakeFeederVoltage(double volts) {}

  default void stop() {}
}
