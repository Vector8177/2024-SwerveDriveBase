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
  private final CANSparkMax shooterLeftPivotSparkMax;
  private final RelativeEncoder shooterLeftPivotEncoder;
  private final CANSparkMax shooterRightPivotSparkMax;
  private final RelativeEncoder shooterRightPivotEncoder;
  private final CANSparkMax shooterFeederSparkMax;
  private final RelativeEncoder shooterFeederEncoder;

  public ShooterIOSparkMax() {
    shooterTopFixedSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);
    shooterBottomFixedSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);
    shooterLeftPivotSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);
    shooterRightPivotSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);
    shooterFeederSparkMax = new CANSparkMax(Constants.placeHolderMotorID, MotorType.kBrushless);

    shooterTopFixedSparkMax.restoreFactoryDefaults();
    shooterTopFixedSparkMax.setCANTimeout(250);
    shooterBottomFixedSparkMax.restoreFactoryDefaults();
    shooterBottomFixedSparkMax.setCANTimeout(250);
    shooterLeftPivotSparkMax.restoreFactoryDefaults();
    shooterLeftPivotSparkMax.setCANTimeout(250);
    shooterRightPivotSparkMax.restoreFactoryDefaults();
    shooterRightPivotSparkMax.setCANTimeout(250);
    shooterFeederSparkMax.restoreFactoryDefaults();
    shooterFeederSparkMax.setCANTimeout(250);

    shooterTopFixedSparkMax.setSmartCurrentLimit(20);
    shooterBottomFixedSparkMax.setSmartCurrentLimit(20);
    shooterLeftPivotSparkMax.setSmartCurrentLimit(20);
    shooterRightPivotSparkMax.setSmartCurrentLimit(20);
    shooterFeederSparkMax.setSmartCurrentLimit(20);

    shooterTopFixedEncoder = shooterTopFixedSparkMax.getEncoder();
    shooterTopFixedSparkMax.enableVoltageCompensation(12d);
    shooterBottomFixedEncoder = shooterBottomFixedSparkMax.getEncoder();
    shooterBottomFixedSparkMax.enableVoltageCompensation(12d);
    shooterLeftPivotEncoder = shooterLeftPivotSparkMax.getEncoder();
    shooterLeftPivotSparkMax.enableVoltageCompensation(12d);
    shooterRightPivotEncoder = shooterRightPivotSparkMax.getEncoder();
    shooterRightPivotSparkMax.enableVoltageCompensation(12d);
    shooterFeederEncoder = shooterFeederSparkMax.getEncoder();
    shooterFeederSparkMax.enableVoltageCompensation(12d);

    shooterTopFixedEncoder.setMeasurementPeriod(50);
    shooterBottomFixedEncoder.setMeasurementPeriod(50);
    shooterLeftPivotEncoder.setMeasurementPeriod(50);
    shooterRightPivotEncoder.setMeasurementPeriod(50);
    shooterFeederEncoder.setMeasurementPeriod(50);

    shooterRightPivotSparkMax.follow(shooterLeftPivotSparkMax);
    shooterRightPivotSparkMax.setInverted(true);

    shooterTopFixedSparkMax.burnFlash();
    shooterBottomFixedSparkMax.burnFlash();
    shooterLeftPivotSparkMax.burnFlash();
    shooterRightPivotSparkMax.burnFlash();
    shooterFeederSparkMax.burnFlash();
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
    inputs.shooterBottomFixedCurrentAmps = new double[] {shooterBottomFixedSparkMax.getOutputCurrent()};

    inputs.shooterLeftPivotAppliedVolts =
        shooterLeftPivotSparkMax.getAppliedOutput() * shooterLeftPivotSparkMax.getBusVoltage();
    inputs.shooterLeftPivotVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(shooterLeftPivotEncoder.getVelocity());
    inputs.shooterLeftPivotCurrentAmps = new double[] {shooterLeftPivotSparkMax.getOutputCurrent()};

    inputs.shooterRightPivotAppliedVolts =
        shooterRightPivotSparkMax.getAppliedOutput() * shooterRightPivotSparkMax.getBusVoltage();
    inputs.shooterRightPivotVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(shooterRightPivotEncoder.getVelocity());
    inputs.shooterRightPivotCurrentAmps = new double[] {shooterRightPivotSparkMax.getOutputCurrent()};

    inputs.shooterFeederAppliedVolts =
        shooterFeederSparkMax.getAppliedOutput() * shooterFeederSparkMax.getBusVoltage();
    inputs.shooterFeederVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(shooterFeederEncoder.getVelocity());
    inputs.shooterFeederCurrentAmps = new double[] {shooterFeederSparkMax.getOutputCurrent()};
  }

  @Override
  public void setShooterSpeedVoltage(double topVolts, double bottomVolts){
    shooterTopFixedSparkMax.setVoltage(topVolts);
    shooterBottomFixedSparkMax.setVoltage(bottomVolts);
  }

  @Override
  public void setShooterPositionVoltage(double volts){
    shooterLeftPivotSparkMax.setVoltage(volts);
  }

  @Override
  public void setShooterFeederVoltage(double volts){
    shooterFeederSparkMax.setVoltage(volts);
  }

  @Override
  public void stop(){
    shooterTopFixedSparkMax.setVoltage(0d);
    shooterBottomFixedSparkMax.setVoltage(0d);
    shooterLeftPivotSparkMax.setVoltage(0d);
    shooterFeederSparkMax.setVoltage(0d);
  }
}
