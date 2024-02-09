package frc.robot.subsystems.climber;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants;

public class ClimberIOSparkMax implements ClimberIO {
  private final CANSparkMax climberLeftSparkMax;
  private final RelativeEncoder climberLeftEncoder;
  private final CANSparkMax climberRightSparkMax;
  private final RelativeEncoder climberRightEncoder;

  public ClimberIOSparkMax() {
    climberLeftSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);
    climberRightSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);

    climberLeftSparkMax.restoreFactoryDefaults();
    climberLeftSparkMax.setCANTimeout(250);
    climberRightSparkMax.restoreFactoryDefaults();
    climberRightSparkMax.setCANTimeout(250);

    climberLeftEncoder = climberLeftSparkMax.getEncoder();
    climberLeftSparkMax.enableVoltageCompensation(12d);
    climberRightEncoder = climberRightSparkMax.getEncoder();
    climberRightSparkMax.enableVoltageCompensation(12d);

    climberLeftEncoder.setMeasurementPeriod(50);

    climberRightSparkMax.follow(climberLeftSparkMax);
    climberRightSparkMax.setInverted(true);

    climberLeftSparkMax.burnFlash();
    climberRightSparkMax.burnFlash();
  }

  @Override
  public void updateInputs(ClimberIOInputs inputs) {
    inputs.climberLeftAppliedVolts =
        climberLeftSparkMax.getAppliedOutput() * climberLeftSparkMax.getBusVoltage();
    inputs.climberLeftVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(climberLeftEncoder.getVelocity());
    inputs.climberLeftCurrentAmps = new double[] {climberLeftSparkMax.getOutputCurrent()};

    inputs.climberRightAppliedVolts =
        climberRightSparkMax.getAppliedOutput() * climberRightSparkMax.getBusVoltage();
    inputs.climberRightVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(climberRightEncoder.getVelocity());
    inputs.climberRightCurrentAmps = new double[] {climberRightSparkMax.getOutputCurrent()};
  }

  @Override
  public void setClimberVoltage(double volts) {
    climberLeftSparkMax.setVoltage(volts);
  }

  @Override
  public void stop() {
    climberLeftSparkMax.setVoltage(0);
  }
}
