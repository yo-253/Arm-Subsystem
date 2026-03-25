package frc.robot.subsystems.arm;

public class ArmConstants {
  public static final double debounceTime = .25;

  public static final int canId = 5;
  public static final int currentLimit = 50;

  public static final double armkP = 0.0;
  public static final double armkI = 0.0;
  public static final double armkD = 0.0;

  public static final double zeroAngleRad = 0; // radians
  public static final double ninetyAngleRad = Math.PI / 2; // radians
  public static final double oneeightyAngleRad = Math.PI; // radians
  public static final double twoseventyAngleRad = Math.PI * 3 / 2; // radians
}
