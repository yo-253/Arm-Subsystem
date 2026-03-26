package frc.robot.subsystems.arm;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import lombok.RequiredArgsConstructor;
import frc.robot.subsystems.arm.ArmIO.ArmIOOutputMode;
import frc.robot.subsystems.arm.ArmIO.ArmIOOutputs;
import frc.robot.util.FullSubsystem;

public class ArmSubsystem extends FullSubsystem {
  private final ArmIO io;
  private final ArmIOInputsAutoLogged inputs = new ArmIOInputsAutoLogged();
  private final ArmIOOutputs outputs = new ArmIOOutputs();

  @RequiredArgsConstructor
  public enum Goal {
    IDLE(() -> 0.0),
    ZERO(() -> ArmConstants.zeroAngleRad),
    NINETY(() -> ArmConstants.ninetyAngleRad),
    ONEEIGHTY(() -> ArmConstants.oneeightyAngleRad),
    TWOSEVENTY(() -> ArmConstants.twoseventyAngleRad);

    private final DoubleSupplier angleRads;

    private double getGoal() {
      return angleRads.getAsDouble();
    }
  }

  private Goal currentGoal = Goal.IDLE;

  public ArmSubsystem(ArmIO io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Arm", inputs);

    if (DriverStation.isDisabled()) {
      setGoal(Goal.IDLE);
    }

    if (currentGoal == Goal.IDLE) {
      stop();
    } else {
      runAngular(currentGoal.getGoal());
    }
  }

  @Override
  public void periodicAfterScheduler() {
    Logger.recordOutput("Arm/Mode", outputs.mode);
    io.applyOutputs(outputs);
  }

  private void setGoal(Goal desiredGoal) {
    currentGoal = desiredGoal;
  }

  private void runAngular(double angle) {
    outputs.mode = ArmIOOutputMode.CLOSED_LOOP;
    outputs.angleDeg = angle;
  }

  private void stop() {
    outputs.mode = ArmIOOutputMode.IDLE;
  }

  private void rezero() {
    io.rezero();
    setGoal(Goal.IDLE);
  }

  public Command zeroDegreesCommand() {
    return startEnd(() -> setGoal(Goal.ZERO), () -> setGoal(Goal.IDLE));
  }

  public Command ninetyDegreesCommand() {
    return startEnd(() -> setGoal(Goal.NINETY), () -> setGoal(Goal.IDLE));
  }
  
  public Command oneeightyDegreesCommand() {
    return startEnd(() -> setGoal(Goal.ONEEIGHTY), () -> setGoal(Goal.IDLE));
  }

  public Command twoseventyDegreesCommand() {
    return startEnd(() -> setGoal(Goal.TWOSEVENTY), () -> setGoal(Goal.IDLE));
  }

  public Command rezeroCommand() {
    return runOnce(() -> rezero());
  }

  public Command stopCommand() {
    return runOnce(this::stop);
  }
}
