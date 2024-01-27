package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.AutoLog;

public interface ShooterIO {
  @AutoLog
  public static class ShooterIOInputs {
    public double shooter0VelocityRadPerSec = 0.0;
    public double shooter1VelocityRadPerSec = 0.0;
    public double shooter0AppliedVolts = 0.0;
    public double shooter1AppliedVolts = 0.0;
    public double[] shooter0CurrentAmps = new double[] {};
    public double[] shooter1CurrentAmps = new double[] {};
  }

  public default void updateInputs(ShooterIOInputs inputs) {}

  public default void setShooterVoltage(double volts) {}
}
