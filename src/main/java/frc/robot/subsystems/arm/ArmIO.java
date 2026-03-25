package frc.robot.subsystems.arm;

import org.littletonrobotics.junction.AutoLog;

public interface ArmIO {
  @AutoLog
  public class ArmIOInputs {
    public boolean motorConnected = true;
    public double motorPositionRads = 0.0;
    public double motorAppliedVolts = 0.0;
    public double motorSupplyCurrentAmps = 0.0;
  }

  public static enum ArmIOOutputMode {
    IDLE,
    CLOSED_LOOP
  }

  public static class ArmIOOutputs {
    public ArmIOOutputMode mode = ArmIOOutputMode.IDLE;
    public double voltage = 0.0;
  }

  public default void updateInputs(ArmIOInputs inputs) {}

  public default void applyOutputs(ArmIOOutputs outputs) {}

  public default void closedLoop(double setpoint) {}

  public default void rezero() {}
}
