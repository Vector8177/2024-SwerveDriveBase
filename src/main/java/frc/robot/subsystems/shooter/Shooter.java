package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private final ShooterIO io;
  private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

  public Shooter(ShooterIO io) {
    this.io = io;
  }

  public void setShooterSpeed(double speed) {
    // TODO
  }

  public void setShooterPosition(double rad) {
    // TODO
  }

  public void setShooterIndexer(double speed) {
    // TODO
  }

  public double getShooterTopFixedVelocity() {
    return inputs.shooterTopFixedVelocityRadPerSec;
  }

  public double getShooterBottomFixedVelocity() {
    return inputs.shooterBottomFixedVelocityRadPerSec;
  }

  public double getShooterPositionVelocity() {
    return inputs.shooterPivotVelocityRadPerSec;
  }

  public double getShooterIndexerVelocity() {
    return inputs.shooterIndexerVelocityRadPerSec;
  }

  @Override
  public void periodic() {
    // TODO
  }

  public void stop() {
    io.stop();
  }
}
