package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants;

public class ShooterIOSparkMax implements ShooterIO {
  private final CANSparkMax shooterTopFixedSparkMax;
  private final RelativeEncoder shooterTopFixedEncoder;
  private final CANSparkMax shooterBottomFixedSparkMax;
  private final RelativeEncoder shooterBottomFixedEncoder;
  private final CANSparkMax shooterPivotSparkMax;
  private final RelativeEncoder shooterPivotEncoder;
  private final CANSparkMax shooterIndexerSparkMax;
  private final RelativeEncoder shooterIndexerEncoder;

  public ShooterIOSparkMax() {
    shooterTopFixedSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);
    shooterBottomFixedSparkMax =
        new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);
    shooterPivotSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);
    shooterIndexerSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);

    shooterTopFixedSparkMax.restoreFactoryDefaults();
    shooterTopFixedSparkMax.setCANTimeout(250);
    shooterBottomFixedSparkMax.restoreFactoryDefaults();
    shooterBottomFixedSparkMax.setCANTimeout(250);
    shooterPivotSparkMax.restoreFactoryDefaults();
    shooterPivotSparkMax.setCANTimeout(250);
    shooterIndexerSparkMax.restoreFactoryDefaults();
    shooterIndexerSparkMax.setCANTimeout(250);

    shooterTopFixedSparkMax.setSmartCurrentLimit(20);
    shooterBottomFixedSparkMax.setSmartCurrentLimit(20);
    shooterPivotSparkMax.setSmartCurrentLimit(20);
    shooterIndexerSparkMax.setSmartCurrentLimit(20);

    shooterTopFixedEncoder = shooterTopFixedSparkMax.getEncoder();
    shooterTopFixedSparkMax.enableVoltageCompensation(12d);
    shooterBottomFixedEncoder = shooterBottomFixedSparkMax.getEncoder();
    shooterBottomFixedSparkMax.enableVoltageCompensation(12d);
    shooterPivotEncoder = shooterPivotSparkMax.getEncoder();
    shooterPivotSparkMax.enableVoltageCompensation(12d);
    shooterIndexerEncoder = shooterIndexerSparkMax.getEncoder();
    shooterIndexerSparkMax.enableVoltageCompensation(12d);

    shooterTopFixedEncoder.setMeasurementPeriod(50);
    shooterBottomFixedEncoder.setMeasurementPeriod(50);
    shooterPivotEncoder.setMeasurementPeriod(50);
    shooterIndexerEncoder.setMeasurementPeriod(50);

    shooterTopFixedSparkMax.burnFlash();
    shooterBottomFixedSparkMax.burnFlash();
    shooterPivotSparkMax.burnFlash();
    shooterIndexerSparkMax.burnFlash();
  }

  @Override
  public void updateInputs(ShooterIOInputs inputs) {
    inputs.shooterTopFixedAppliedVolts =
        shooterTopFixedSparkMax.getAppliedOutput() * shooterTopFixedSparkMax.getBusVoltage();
    inputs.shooterTopFixedVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(shooterTopFixedEncoder.getVelocity());
    inputs.shooterTopFixedCurrentAmps = new double[] {shooterTopFixedSparkMax.getOutputCurrent()};

    inputs.shooterBottomFixedAppliedVolts =
        shooterBottomFixedSparkMax.getAppliedOutput() * shooterBottomFixedSparkMax.getBusVoltage();
    inputs.shooterBottomFixedVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(shooterBottomFixedEncoder.getVelocity());
    inputs.shooterBottomFixedCurrentAmps =
        new double[] {shooterBottomFixedSparkMax.getOutputCurrent()};

    inputs.shooterPivotAppliedVolts =
        shooterPivotSparkMax.getAppliedOutput() * shooterPivotSparkMax.getBusVoltage();
    inputs.shooterPivotVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(shooterPivotEncoder.getVelocity());
    inputs.shooterPivotCurrentAmps = new double[] {shooterPivotSparkMax.getOutputCurrent()};

    inputs.shooterIndexerAppliedVolts =
        shooterIndexerSparkMax.getAppliedOutput() * shooterIndexerSparkMax.getBusVoltage();
    inputs.shooterIndexerVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(shooterIndexerEncoder.getVelocity());
    inputs.shooterIndexerCurrentAmps = new double[] {shooterIndexerSparkMax.getOutputCurrent()};
  }

  @Override
  public void setShooterSpeedVoltage(double topVolts, double bottomVolts) {
    shooterTopFixedSparkMax.setVoltage(topVolts);
    shooterBottomFixedSparkMax.setVoltage(bottomVolts);
  }

  @Override
  public void setShooterPositionVoltage(double volts) {
    shooterPivotSparkMax.setVoltage(volts);
  }

  @Override
  public void setShooterIndexerVoltage(double volts) {
    shooterIndexerSparkMax.setVoltage(volts);
  }

  @Override
  public void stop() {
    shooterTopFixedSparkMax.setVoltage(0d);
    shooterBottomFixedSparkMax.setVoltage(0d);
    shooterPivotSparkMax.setVoltage(0d);
    shooterIndexerSparkMax.setVoltage(0d);
  }
}
