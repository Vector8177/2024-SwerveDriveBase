package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.AutoLog;

public interface ShooterIO {
  @AutoLog
  public static class ShooterIOInputs {
    public double shooterVelocityRadPerSec = 0.0;
    public double shooterAppliedVolts = 0.0;
    public double[] shooterCurrentAmps = new double[] {};
  }

  public default void updateInputs(ShooterIOInputs inputs) {}

  public default void setShooterVoltage(double volts) {}
}
