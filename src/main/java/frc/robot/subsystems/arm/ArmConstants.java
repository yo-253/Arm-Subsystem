package frc.robot.subsystems.arm;

import edu.wpi.first.math.util.Units;

public class ArmConstants {
  public static final double debounceTime = .25;

  public static final int canId = 5;
  public static final int currentLimit = 50;

  public static final double positionConversionFactor = Math.PI;
  public static final double velocityConversionFactor = Math.PI;

  public static final double armkP = 0.0;
  public static final double armkI = 0.0;
  public static final double armkD = 0.0;

  public static final double zeroAngleRad = Units.degreesToRotations(0);
  public static final double ninetyAngleRad = Units.degreesToRotations(90);
  public static final double oneeightyAngleRad = Units.degreesToRotations(180);
  public static final double twoseventyAngleRad = Units.degreesToRotations(270);
}
