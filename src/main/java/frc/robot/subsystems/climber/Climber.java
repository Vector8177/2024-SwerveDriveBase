package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private final ClimberIO io;
  private final ClimberIOInputsAutoLogged inputs = new ClimberIOInputsAutoLogged();

  public Climber(ClimberIO io) {
    this.io = io;
  }

  public void setClimberPosition(double meters) {
    // TODO
  }

  public double[] getClimberVelocity() {
    return new double[] {inputs.climberLeftVelocityRadPerSec, inputs.climberRightVelocityRadPerSec};
  }

  @Override
  public void periodic() {
    // TODO
  }

  public void stop() {
    io.stop();
  }
}
