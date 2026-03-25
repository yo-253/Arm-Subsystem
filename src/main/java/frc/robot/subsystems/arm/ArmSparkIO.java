package frc.robot.subsystems.arm;

import static frc.robot.subsystems.arm.ArmConstants.*;
import static frc.robot.util.SparkUtil.*;

import java.util.function.DoubleSupplier;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.filter.Debouncer;

public class ArmSparkIO implements ArmIO {
  private final SparkBase masterNEO;
  private final RelativeEncoder masterRelativeEncoder;
  private final SparkClosedLoopController masterController; 

  private final Debouncer masterNEODebouncer = new Debouncer(debounceTime, Debouncer.DebounceType.kFalling);

  public ArmSparkIO() {
    masterNEO = new SparkMax(canId, MotorType.kBrushless);
    masterRelativeEncoder = masterNEO.getEncoder();
    masterController = masterNEO.getClosedLoopController();

    SparkMaxConfig sparkMaxConfig = new SparkMaxConfig();
    sparkMaxConfig
        .idleMode(IdleMode.kBrake)
        .inverted(false)
        .smartCurrentLimit(currentLimit);
    sparkMaxConfig
        .closedLoop
        .pid(armkP, armkI, armkD);

    tryUntilOk(
      masterNEO,
      5,
      () -> masterNEO.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters));
  }

  @Override
  public void updateInputs(ArmIOInputs inputs) {
    sparkStickyFault = false;

    ifOk(
      masterNEO, 
      masterRelativeEncoder::getPosition, 
      (value) -> inputs.motorPositionRads = value);
    ifOk(
      masterNEO, 
      new DoubleSupplier[] {masterNEO::getAppliedOutput, masterNEO::getBusVoltage},
      (values) -> inputs.motorAppliedVolts = values[0] * values[1]);
    ifOk(
      masterNEO, 
      masterNEO::getOutputCurrent, 
      (value) -> inputs.motorSupplyCurrentAmps = value);

    sparkStickyFault = masterNEODebouncer.calculate(!sparkStickyFault);
  }

  @Override
  public void applyOutputs(ArmIOOutputs outputs) {
    double setpoint = outputs.voltage;

    switch (outputs.mode) {
      case CLOSED_LOOP:
        masterController.setSetpoint(setpoint, ControlType.kPosition);
        break;
      case IDLE:
        masterNEO.stopMotor();
        break;
    }
  }

  @Override
  public void closedLoop(double setpoint) {
    masterController.setSetpoint(setpoint, ControlType.kPosition);
  }
}
