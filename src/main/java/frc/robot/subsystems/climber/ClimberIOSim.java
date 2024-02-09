package frc.robot.subsystems.climber;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class ClimberIOSim implements ClimberIO {
  private static final double LOOP_PERIOD_SECS = 0.02;

  private DCMotorSim climberLeftSim = new DCMotorSim(DCMotor.getNEO(1), 1, 0.025);
  private DCMotorSim climberRightSim = new DCMotorSim(DCMotor.getNEO(1), 1, 0.025);

  private double climberLeftAppliedVolts = 0d;
  private double climberRightAppliedVolts = 0d;

  @Override
  public void updateInputs(ClimberIOInputs inputs) {
    climberLeftSim.update(LOOP_PERIOD_SECS);
    climberRightSim.update(LOOP_PERIOD_SECS);

    inputs.climberLeftAppliedVolts = climberLeftAppliedVolts;
    inputs.climberLeftVelocityRadPerSec = climberLeftSim.getAngularVelocityRadPerSec();
    inputs.climberLeftCurrentAmps = new double[] {Math.abs(climberLeftSim.getCurrentDrawAmps())};

    inputs.climberRightAppliedVolts = climberRightAppliedVolts;
    inputs.climberRightVelocityRadPerSec = climberRightSim.getAngularVelocityRadPerSec();
    inputs.climberRightCurrentAmps = new double[] {Math.abs(climberRightSim.getCurrentDrawAmps())};
  }

  @Override
  public void setClimberVoltage(double volts) {
    climberLeftAppliedVolts = MathUtil.clamp(volts, -12.0, 12.0);
    climberRightAppliedVolts = MathUtil.clamp(volts, -12.0, 12.0);

    climberLeftSim.setInputVoltage(climberLeftAppliedVolts);
    climberRightSim.setInputVoltage(climberRightAppliedVolts);
  }

  @Override
  public void stop() {
    climberLeftAppliedVolts = 0;
    climberRightAppliedVolts = 0;

    climberLeftSim.setInputVoltage(climberLeftAppliedVolts);
    climberRightSim.setInputVoltage(climberRightAppliedVolts);
  }
}
