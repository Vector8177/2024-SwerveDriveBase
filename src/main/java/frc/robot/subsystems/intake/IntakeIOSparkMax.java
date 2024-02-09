package frc.robot.subsystems.intake;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants;

public class IntakeIOSparkMax implements IntakeIO {
  private final CANSparkMax intakeLeftSparkMax;
  private final RelativeEncoder intakeLeftEncoder;
  private final CANSparkMax intakeRightSparkMax;
  private final RelativeEncoder intakeRightEncoder;
  private final CANSparkMax indexerSparkMax;
  private final RelativeEncoder indexerEncoder;

  public IntakeIOSparkMax() {
    intakeLeftSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);
    intakeRightSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);
    indexerSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);

    intakeLeftSparkMax.restoreFactoryDefaults();
    intakeLeftSparkMax.setCANTimeout(250);
    intakeRightSparkMax.restoreFactoryDefaults();
    intakeRightSparkMax.setCANTimeout(250);
    indexerSparkMax.restoreFactoryDefaults();
    indexerSparkMax.setCANTimeout(250);

    intakeLeftSparkMax.setSmartCurrentLimit(20);
    intakeRightSparkMax.setSmartCurrentLimit(20);
    indexerSparkMax.setSmartCurrentLimit(20);

    intakeLeftEncoder = intakeLeftSparkMax.getEncoder();
    intakeLeftSparkMax.enableVoltageCompensation(12d);
    intakeRightEncoder = intakeLeftSparkMax.getEncoder();
    intakeRightSparkMax.enableVoltageCompensation(12d);
    indexerEncoder = intakeLeftSparkMax.getEncoder();
    indexerSparkMax.enableVoltageCompensation(12d);

    intakeLeftEncoder.setMeasurementPeriod(50);
    indexerEncoder.setMeasurementPeriod(50);

    intakeRightSparkMax.follow(intakeLeftSparkMax);
    intakeRightSparkMax.setInverted(true);

    intakeLeftSparkMax.burnFlash();
    intakeRightSparkMax.burnFlash();
    indexerSparkMax.burnFlash();
  }

  @Override
  public void updateInputs(IntakeIOInputs inputs) {
    inputs.intakeLeftAppliedVolts =
        intakeLeftSparkMax.getAppliedOutput() * intakeLeftSparkMax.getBusVoltage();
    inputs.intakeLeftVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(intakeLeftEncoder.getVelocity());
    inputs.intakeLeftCurrentAmps = new double[] {intakeLeftSparkMax.getOutputCurrent()};

    inputs.intakeRightAppliedVolts =
        intakeRightSparkMax.getAppliedOutput() * intakeRightSparkMax.getBusVoltage();
    inputs.intakeRightVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(intakeRightEncoder.getVelocity());
    inputs.intakeRightCurrentAmps = new double[] {intakeRightSparkMax.getOutputCurrent()};

    inputs.indexerAppliedVolts =
        indexerSparkMax.getAppliedOutput() * indexerSparkMax.getBusVoltage();
    inputs.indexerVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(indexerEncoder.getVelocity());
    inputs.indexerCurrentAmps = new double[] {indexerSparkMax.getOutputCurrent()};
  }

  @Override
  public void setIntakeVoltage(double volts) {
    intakeLeftSparkMax.setVoltage(volts);
  }

  @Override
  public void setIndexerVoltage(double volts) {
    indexerSparkMax.setVoltage(volts);
  }

  @Override
  public void stop() {
    intakeLeftSparkMax.setVoltage(0);
    indexerSparkMax.setVoltage(0);
  }
}
