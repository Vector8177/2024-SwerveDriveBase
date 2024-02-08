package frc.robot.subsystems.hood;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants;

public class HoodIOSparkMax implements HoodIO {
  private final CANSparkMax hoodPivotSparkMax;
  private final RelativeEncoder hoodPivotEncoder;
  private final CANSparkMax hoodRollerSparkMax;
  private final RelativeEncoder hoodRollerEncoder;

  public HoodIOSparkMax() {
    hoodPivotSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);
    hoodRollerSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);

    hoodPivotSparkMax.restoreFactoryDefaults();
    hoodPivotSparkMax.setCANTimeout(250);
    hoodRollerSparkMax.restoreFactoryDefaults();
    hoodRollerSparkMax.setCANTimeout(250);

    hoodPivotEncoder = hoodPivotSparkMax.getEncoder();
    hoodPivotSparkMax.enableVoltageCompensation(12d);
    hoodRollerEncoder = hoodRollerSparkMax.getEncoder();
    hoodRollerSparkMax.enableVoltageCompensation(12d);

    hoodPivotEncoder.setMeasurementPeriod(50);
    hoodRollerEncoder.setMeasurementPeriod(50);

    hoodPivotSparkMax.burnFlash();
    hoodRollerSparkMax.burnFlash();
  }

  @Override
  public void updateInputs(HoodIOInputs inputs) {
    inputs.hoodPivotAppliedVolts =
        hoodPivotSparkMax.getAppliedOutput() * hoodPivotSparkMax.getBusVoltage();
    inputs.hoodPivotVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(hoodPivotEncoder.getVelocity());
    inputs.hoodPivotCurrentAmps = new double[] {hoodPivotSparkMax.getOutputCurrent()};

    inputs.hoodRollerAppliedVolts =
        hoodRollerSparkMax.getAppliedOutput() * hoodRollerSparkMax.getBusVoltage();
    inputs.hoodRollerVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(hoodRollerEncoder.getVelocity());
    inputs.hoodRollerCurrentAmps = new double[] {hoodRollerSparkMax.getOutputCurrent()};
  }

  @Override
  public void setHoodPivotVoltage(double volts) {
    hoodPivotSparkMax.setVoltage(volts);
  }

  @Override
  public void setHoodRollerVoltage(double volts) {
    hoodRollerSparkMax.setVoltage(volts);
  }

  @Override
  public void stop() {
    hoodPivotSparkMax.setVoltage(0);
    hoodRollerSparkMax.setVoltage(0);
  }
}
