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
  private final CANSparkMax intakeFeederSparkMax;
  private final RelativeEncoder intakeFeederEncoder;

  public IntakeIOSparkMax() {
    intakeLeftSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);
    intakeRightSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);
    intakeFeederSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);

    intakeLeftSparkMax.restoreFactoryDefaults();
    intakeLeftSparkMax.setCANTimeout(250);
    intakeRightSparkMax.restoreFactoryDefaults();
    intakeRightSparkMax.setCANTimeout(250);
    intakeFeederSparkMax.restoreFactoryDefaults();
    intakeFeederSparkMax.setCANTimeout(250);

    intakeLeftSparkMax.setSmartCurrentLimit(20);
    intakeRightSparkMax.setSmartCurrentLimit(20);
    intakeFeederSparkMax.setSmartCurrentLimit(20);

    intakeLeftEncoder = intakeLeftSparkMax.getEncoder();
    intakeLeftSparkMax.enableVoltageCompensation(12d);
    intakeRightEncoder = intakeLeftSparkMax.getEncoder();
    intakeRightSparkMax.enableVoltageCompensation(12d);
    intakeFeederEncoder = intakeLeftSparkMax.getEncoder();
    intakeFeederSparkMax.enableVoltageCompensation(12d);

    intakeLeftEncoder.setMeasurementPeriod(50);
    intakeFeederEncoder.setMeasurementPeriod(50);

    intakeRightSparkMax.follow(intakeLeftSparkMax);
    intakeRightSparkMax.setInverted(true);

    intakeLeftSparkMax.burnFlash();
    intakeRightSparkMax.burnFlash();
    intakeFeederSparkMax.burnFlash();
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

    inputs.intakeFeederAppliedVolts =
        intakeFeederSparkMax.getAppliedOutput() * intakeFeederSparkMax.getBusVoltage();
    inputs.intakeFeederVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(intakeFeederEncoder.getVelocity());
    inputs.intakeFeederCurrentAmps = new double[] {intakeFeederSparkMax.getOutputCurrent()};
  }

  @Override
  public void setIntakePositionVoltage(double volts) {
    intakeLeftSparkMax.setVoltage(volts);
  }

  @Override
  public void setIntakeFeederVoltage(double volts) {
    intakeFeederSparkMax.setVoltage(volts);
  }

  @Override
  public void stop() {
    intakeLeftSparkMax.setVoltage(0);
    intakeFeederSparkMax.setVoltage(0);
  }
}
