package frc.robot.subsystems.intake;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class IntakeIOSim implements IntakeIO {
  private static final double LOOP_PERIOD_SECS = 0.02;

  private DCMotorSim intakeLeftSim = new DCMotorSim(DCMotor.getNeo550(1), 1, 0.025);
  private DCMotorSim intakeRightSim = new DCMotorSim(DCMotor.getNeo550(1), 1, 0.025);
  private DCMotorSim indexerSim = new DCMotorSim(DCMotor.getNEO(1), 1, 0.004);

  private double intakeLeftAppliedVolts = 0d;
  private double intakeRightAppliedVolts = 0d;
  private double indexerAppliedVolts = 0d;

  @Override
  public void updateInputs(IntakeIOInputs inputs) {
    intakeLeftSim.update(LOOP_PERIOD_SECS);
    intakeRightSim.update(LOOP_PERIOD_SECS);
    indexerSim.update(LOOP_PERIOD_SECS);

    inputs.intakeLeftAppliedVolts = intakeLeftAppliedVolts;
    inputs.intakeLeftVelocityRadPerSec = intakeLeftSim.getAngularVelocityRadPerSec();
    inputs.intakeLeftCurrentAmps = new double[] {Math.abs(intakeLeftSim.getCurrentDrawAmps())};

    inputs.intakeRightAppliedVolts = intakeRightAppliedVolts;
    inputs.intakeRightVelocityRadPerSec = intakeRightSim.getAngularVelocityRadPerSec();
    inputs.intakeRightCurrentAmps = new double[] {Math.abs(intakeRightSim.getCurrentDrawAmps())};

    inputs.indexerAppliedVolts = indexerAppliedVolts;
    inputs.indexerVelocityRadPerSec = indexerSim.getAngularVelocityRadPerSec();
    inputs.indexerCurrentAmps = new double[] {Math.abs(indexerSim.getCurrentDrawAmps())};
  }

  @Override
  public void setIntakeVoltage(double volts) {
    intakeLeftAppliedVolts = MathUtil.clamp(volts, -12.0, 12.0);
    intakeRightAppliedVolts = MathUtil.clamp(volts, -12.0, 12.0);

    intakeLeftSim.setInputVoltage(intakeLeftAppliedVolts);
    intakeRightSim.setInputVoltage(intakeRightAppliedVolts);
  }

  @Override
  public void setIndexerVoltage(double volts) {
    indexerAppliedVolts = MathUtil.clamp(volts, -12.0, 12.0);
    indexerSim.setInputVoltage(indexerAppliedVolts);
  }

  @Override
  public void stop() {
    intakeLeftAppliedVolts = 0;
    intakeRightAppliedVolts = 0;
    indexerAppliedVolts = 0;

    intakeLeftSim.setInputVoltage(intakeLeftAppliedVolts);
    intakeRightSim.setInputVoltage(intakeRightAppliedVolts);
    indexerSim.setInputVoltage(indexerAppliedVolts);
  }
}
