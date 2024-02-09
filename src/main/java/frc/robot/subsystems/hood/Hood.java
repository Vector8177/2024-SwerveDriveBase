package frc.robot.subsystems.hood;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hood extends SubsystemBase {
  private final HoodIO io;
  private final HoodIOInputsAutoLogged inputs = new HoodIOInputsAutoLogged();

  public Hood(HoodIO io) {
    this.io = io;
  }

  public void setHoodPosition(double rad) {
    // TODO
  }

  public double getPivotVelocity() {
    return inputs.hoodPivotVelocityRadPerSec;
  }

  @Override
  public void periodic() {
    // TODO
  }

  public void stop() {
    io.stop();
  }
}
