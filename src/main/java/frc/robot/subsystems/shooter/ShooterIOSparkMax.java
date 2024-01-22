package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.util.Units;

public class ShooterIOSparkMax implements ShooterIO {
  private final CANSparkMax shooterSparkMax;
  private final RelativeEncoder shooterEncoder;

  public ShooterIOSparkMax() {
    shooterSparkMax = new CANSparkMax(20, MotorType.kBrushless);

    shooterSparkMax.restoreFactoryDefaults();
    shooterSparkMax.setCANTimeout(250);

    shooterSparkMax.setSmartCurrentLimit(20);

    shooterEncoder = shooterSparkMax.getEncoder();
    shooterSparkMax.enableVoltageCompensation(12.0);

    shooterEncoder.setMeasurementPeriod(50);

    shooterSparkMax.burnFlash();
  }

  @Override
  public void updateInputs(ShooterIOInputs inputs) {
    // TODO Auto-generated method stub
    inputs.shooterAppliedVolts =
        shooterSparkMax.getAppliedOutput() * shooterSparkMax.getBusVoltage();
    inputs.shooterCurrentAmps = new double[] {shooterSparkMax.getOutputCurrent()};

    inputs.shooterVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(shooterEncoder.getVelocity());
  }

  @Override
  public void setShooterVoltage(double volts) {
    shooterSparkMax.setVoltage(volts);
  }
}
