package util;

public class Physics {

	public static final float GRAVITY = (0.32f);
	public static final float MAX_VELOCITY_X = 50f;
	public static final float MAX_VELOCITY_Y = 50f;
	public static final float MAX_ACCELERATION_X = 10f;
	public static final float MAX_ACCELERATION_Y = 10f;
	
	public static float calculateCollisionVelcoity1(float m1, float m2, float v1) {
		m1*=3;
		m2*=2;
		return ((m1 - m2)/(m1 + m2))*v1;
	}
	
	public static float calculateCollisionVelcoity2(float m1, float m2, float v1) {
		m1*=2;
		m2*=3;
		return ((2*m1)/(m1 + m2))*v1;
	}
	
}
