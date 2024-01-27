package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.util.Units;

public class ShooterIOSparkMax implements ShooterIO {
  private final CANSparkMax shooter0SparkMax;
  private final RelativeEncoder shooter0Encoder;
  private final CANSparkMax shooter1SparkMax;
  private final RelativeEncoder shooter1Encoder;

  public ShooterIOSparkMax() {
    shooter0SparkMax = new CANSparkMax(31, MotorType.kBrushless);
    shooter1SparkMax = new CANSparkMax(32, MotorType.kBrushless);

    shooter0SparkMax.restoreFactoryDefaults();
    shooter0SparkMax.setCANTimeout(250);
    shooter1SparkMax.restoreFactoryDefaults();
    shooter1SparkMax.setCANTimeout(250);

    shooter0SparkMax.setSmartCurrentLimit(20);
    shooter1SparkMax.setSmartCurrentLimit(20);

    shooter0Encoder = shooter0SparkMax.getEncoder();
    shooter0SparkMax.enableVoltageCompensation(12.0);
    shooter1Encoder = shooter1SparkMax.getEncoder();
    shooter1SparkMax.enableVoltageCompensation(12.0);

    shooter0Encoder.setMeasurementPeriod(50);

    shooter1SparkMax.follow(shooter0SparkMax);
    shooter1SparkMax.setInverted(true);

    shooter0SparkMax.burnFlash();
    shooter1SparkMax.burnFlash();
  }

  @Override
  public void updateInputs(ShooterIOInputs inputs) {
    // TODO Auto-generated method stub
    inputs.shooter0AppliedVolts =
        shooter0SparkMax.getAppliedOutput() * shooter0SparkMax.getBusVoltage();
    inputs.shooter0CurrentAmps = new double[] {shooter0SparkMax.getOutputCurrent()};

    inputs.shooter1VelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(shooter1Encoder.getVelocity());
    inputs.shooter1AppliedVolts =
        shooter1SparkMax.getAppliedOutput() * shooter1SparkMax.getBusVoltage();
    inputs.shooter1CurrentAmps = new double[] {shooter1SparkMax.getOutputCurrent()};

    inputs.shooter1VelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(shooter1Encoder.getVelocity());
  }

  @Override
  public void setShooterVoltage(double volts) {
    shooter0SparkMax.setVoltage(volts);
  }
}
