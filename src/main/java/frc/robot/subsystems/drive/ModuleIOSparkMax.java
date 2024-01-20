// Copyright 2021-2024 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.subsystems.drive;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

/**
 * Module IO implementation for SparkMax drive motor controller, SparkMax turn motor controller (NEO
 * or NEO 550), and analog absolute encoder connected to the RIO
 *
 * <p>NOTE: This implementation should be used as a starting point and adapted to different hardware
 * configurations (e.g. If using a CANcoder, copy from "ModuleIOTalonFX")
 *
 * <p>To calibrate the absolute encoder offsets, point the modules straight (such that forward
 * motion on the drive motor will propel the robot forward) and copy the reported values from the
 * absolute encoders using AdvantageScope. These values are logged under
 * "/Drive/ModuleX/TurnAbsolutePositionRad"
 */
public class ModuleIOSparkMax implements ModuleIO {
  // Gear ratios for SDS MK4i L2, adjust as necessary
  private static final double DRIVE_GEAR_RATIO = (50.0 / 14.0) * (17.0 / 27.0) * (45.0 / 15.0);
  private static final double TURN_GEAR_RATIO = 150.0 / 7.0;

  private final CANSparkMax driveSparkMax;
  private final CANSparkMax turnSparkMax;

  private final RelativeEncoder driveEncoder;
  private final RelativeEncoder turnRelativeEncoder;
  private final SparkAbsoluteEncoder turnAbsoluteEncoder;

  private final boolean isTurnMotorInverted = false;
  private final Rotation2d absoluteEncoderOffset;

  public ModuleIOSparkMax(int index) {
    switch (index) {
      case 0:
        driveSparkMax = new CANSparkMax(11, MotorType.kBrushless);
        turnSparkMax = new CANSparkMax(1, MotorType.kBrushless);
        turnAbsoluteEncoder = turnSparkMax.getAbsoluteEncoder(Type.kDutyCycle);
        absoluteEncoderOffset = new Rotation2d(0); // MUST BE CALIBRATED
        driveSparkMax.setInverted(false);
        break;
      case 1:
        driveSparkMax = new CANSparkMax(14, MotorType.kBrushless);
        turnSparkMax = new CANSparkMax(4, MotorType.kBrushless);
        turnAbsoluteEncoder = turnSparkMax.getAbsoluteEncoder(Type.kDutyCycle);
        absoluteEncoderOffset = new Rotation2d(0); // MUST BE CALIBRATED
        driveSparkMax.setInverted(true);
        break;
      case 2:
        driveSparkMax = new CANSparkMax(12, MotorType.kBrushless);
        turnSparkMax = new CANSparkMax(2, MotorType.kBrushless);
        turnAbsoluteEncoder = turnSparkMax.getAbsoluteEncoder(Type.kDutyCycle);
        absoluteEncoderOffset = new Rotation2d(0); // MUST BE CALIBRATED
        driveSparkMax.setInverted(true);
        break;
      case 3:
        driveSparkMax = new CANSparkMax(13, MotorType.kBrushless);
        turnSparkMax = new CANSparkMax(3, MotorType.kBrushless);
        turnAbsoluteEncoder = turnSparkMax.getAbsoluteEncoder(Type.kDutyCycle);
        absoluteEncoderOffset = new Rotation2d(0); // MUST BE CALIBRATED
        driveSparkMax.setInverted(false);
        break;
      default:
        throw new RuntimeException("Invalid module index");
    }

    // turnSparkMax.setInverted(true);

    driveSparkMax.restoreFactoryDefaults();
    turnSparkMax.restoreFactoryDefaults();

    driveSparkMax.setCANTimeout(250);
    turnSparkMax.setCANTimeout(250);

    driveEncoder = driveSparkMax.getEncoder();
    turnRelativeEncoder = turnSparkMax.getEncoder();

    turnSparkMax.setInverted(isTurnMotorInverted);
    driveSparkMax.setSmartCurrentLimit(40);
    turnSparkMax.setSmartCurrentLimit(20);
    driveSparkMax.enableVoltageCompensation(12.0);
    turnSparkMax.enableVoltageCompensation(12.0);

    driveEncoder.setPosition(0.0);
    driveEncoder.setMeasurementPeriod(10);
    driveEncoder.setAverageDepth(2);

    turnRelativeEncoder.setPosition(turnAbsoluteEncoder.getPosition() * 2 * Math.PI);
    turnRelativeEncoder.setMeasurementPeriod(10);
    turnRelativeEncoder.setAverageDepth(2);
    // turnRelativeEncoder.setInverted(true);

    driveSparkMax.setCANTimeout(0);
    turnSparkMax.setCANTimeout(0);

    driveSparkMax.burnFlash();
    turnSparkMax.burnFlash();
  }

  @Override
  public void updateInputs(ModuleIOInputs inputs) {
    inputs.drivePositionRad =
        Units.rotationsToRadians(driveEncoder.getPosition()) / DRIVE_GEAR_RATIO;
    inputs.driveVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(driveEncoder.getVelocity()) / DRIVE_GEAR_RATIO;
    inputs.driveAppliedVolts = driveSparkMax.getAppliedOutput() * driveSparkMax.getBusVoltage();
    inputs.driveCurrentAmps = new double[] {driveSparkMax.getOutputCurrent()};

    inputs.turnAbsolutePosition =
        new Rotation2d((turnAbsoluteEncoder.getPosition()) * 2.0 * Math.PI)
            .minus(absoluteEncoderOffset);
    // inputs.turnPosition =
    //     new Rotation2d(
    //         Rotation2d.fromRotations(turnRelativeEncoder.getPosition() / TURN_GEAR_RATIO)
    //                 .getRadians()
    //             % Math.PI);
    inputs.turnPosition =
        Rotation2d.fromRotations(turnRelativeEncoder.getPosition() / TURN_GEAR_RATIO);
    inputs.turnVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(turnRelativeEncoder.getVelocity())
            / TURN_GEAR_RATIO;
    inputs.turnAppliedVolts = turnSparkMax.getAppliedOutput() * turnSparkMax.getBusVoltage();
    inputs.turnCurrentAmps = new double[] {turnSparkMax.getOutputCurrent()};
  }

  @Override
  public void setDriveVoltage(double volts) {
    driveSparkMax.setVoltage(volts);
  }

  @Override
  public void setTurnVoltage(double volts) {
    turnSparkMax.setVoltage(volts);
  }

  @Override
  public void setDriveBrakeMode(boolean enable) {
    driveSparkMax.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
  }

  @Override
  public void setTurnBrakeMode(boolean enable) {
    turnSparkMax.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
  }
}
