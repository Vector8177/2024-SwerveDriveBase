package frc.robot.subsystems.hood;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants;

public class HoodIOSparkMax implements HoodIO {
  private final CANSparkMax hoodSparkMax;
  private final RelativeEncoder hoodEncoder;

  public HoodIOSparkMax() {
    hoodSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);

    hoodSparkMax.restoreFactoryDefaults();
    hoodSparkMax.setCANTimeout(250);

    hoodEncoder = hoodSparkMax.getEncoder();
    hoodSparkMax.enableVoltageCompensation(12d);

    hoodEncoder.setMeasurementPeriod(50);

    hoodSparkMax.burnFlash();
  }

  @Override
  public void updateInputs(HoodIOInputs inputs) {
    inputs.hoodPivotAppliedVolts =
        hoodSparkMax.getAppliedOutput() * hoodSparkMax.getBusVoltage();
    inputs.hoodPivotVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(hoodEncoder.getVelocity());
    inputs.hoodPivotCurrentAmps = new double[] {hoodSparkMax.getOutputCurrent()};
  }

  @Override
  public void setHoodPivotVoltage(double volts) {
    hoodSparkMax.setVoltage(volts);
  }

  @Override
  public void stop() {
    hoodSparkMax.setVoltage(0);
  }
}
