package frc.robot.subsystems.arm;

public class ArmConstants {
  public static final double debounceTime = .25;

  public static final int canId = 5;
  public static final int currentLimit = 50;

  public static final double armkP = 0.0;
  public static final double armkI = 0.0;
  public static final double armkD = 0.0;

  public static final double zeroAngle = 0; // radians
  public static final double ninetyAngle = Math.PI / 2; // radians
  public static final double oneeightyAngle = Math.PI; // radians
  public static final double twoseventyAngle = Math.PI * 3 / 2; // radians
}
