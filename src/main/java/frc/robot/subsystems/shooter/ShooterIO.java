package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.AutoLog;

public interface ShooterIO {
  @AutoLog
  public static class ShooterIOInputs {
    public double shooterTopFixedVelocityRadPerSec = 0d;
    public double shooterBottomFixedVelocityRadPerSec = 0d;
    public double shooterPivotVelocityRadPerSec = 0d;
    public double shooterFeederVelocityRadPerSec = 0d;
    public double shooterTopFixedAppliedVolts = 0d;
    public double shooterBottomFixedAppliedVolts = 0d;
    public double shooterPivotAppliedVolts = 0d;
    public double shooterFeederAppliedVolts = 0d;
    public double[] shooterTopFixedCurrentAmps = new double[] {};
    public double[] shooterBottomFixedCurrentAmps = new double[] {};
    public double[] shooterPivotCurrentAmps = new double[] {};
    public double[] shooterFeederCurrentAmps = new double[] {};
    
  }

  public default void updateInputs(ShooterIOInputs inputs) {}

  public default void setShooterSpeedVoltage(double topVolts, double bottomVolts) {}

  public default void setShooterPositionVoltage(double volts) {}

  public default void setShooterFeederVoltage(double volts) {}

  public default void stop() {}
}
