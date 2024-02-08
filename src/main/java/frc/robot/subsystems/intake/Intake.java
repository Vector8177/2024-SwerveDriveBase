package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private final IntakeIO io;
  private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

  public Intake(IntakeIO io) {
    this.io = io;
  }

  public void setIntakePosition(double rad) {
    // TODO
  }

  public void setInakeFeederSpeed(double speed) {
    // TODO
  }

  public double getPositionVelocity() {
    return (inputs.intakeLeftVelocityRadPerSec + inputs.intakeRightVelocityRadPerSec) / 2d;
  }

  public double getFeederVelocity() {
    return inputs.intakeFeederVelocityRadPerSec;
  }

  @Override
  public void periodic() {
    // TODO
  }

  public void stop(){
    io.stop();
}
}
