package geom;

import org.newdawn.slick.Graphics;

import characters.Player;

public class PlayerModel {

	private final int HEIGHT = 64;
	private final int HEAD_RADIUS = 16;
	private final int BODY_LENGTH = 32;
	private final int ARM_LENGTH = 16;
	private final int LEG_LENGTH = HEIGHT - BODY_LENGTH - HEAD_RADIUS;

	private float leftArmAngle = 45f;
	private float rightArmAngle = 45f;
	private float leftLegAngle = 45f;
	private float rightLegAngle = 45f;

	private int legAnimationStep = 0;
	private int bodyAnimationStep = 0;

	private Player p;

	public PlayerModel(Player p) {
		this.p = p;
	}

	public void render(Graphics g) {
		float pX = p.getPos().getX();
		float pY = p.getPos().getY();
	
		g.setColor(p.getColor());
		// head
		g.drawOval(pX + HEAD_RADIUS / 2, pY + HEIGHT - HEAD_RADIUS, HEAD_RADIUS, HEAD_RADIUS);
		// body
		g.drawLine(pX + HEAD_RADIUS, pY + HEIGHT + HEAD_RADIUS + BODY_LENGTH / 3, pX + HEAD_RADIUS,
				pY + HEAD_RADIUS * 2 + BODY_LENGTH);
		// right arm
		g.drawLine((int)(pX-Math.sin(rightArmAngle)*ARM_LENGTH) + ARM_LENGTH + HEAD_RADIUS, pY + BODY_LENGTH * 2 + BODY_LENGTH / 8, pX + HEAD_RADIUS - ARM_LENGTH,
				(int)(pY-Math.cos(rightArmAngle)*ARM_LENGTH) + BODY_LENGTH * 2 + BODY_LENGTH / 8);
		//left arm
		g.drawLine(pX + HEAD_RADIUS, pY + BODY_LENGTH * 2 + BODY_LENGTH / 8, (int)(pX+(Math.sin(leftArmAngle)*ARM_LENGTH)) + HEAD_RADIUS - ARM_LENGTH,
				(int)(pY+(Math.cos(leftArmAngle)*ARM_LENGTH)) + BODY_LENGTH * 2 + BODY_LENGTH / 8);

	}

	public float getLeftArmAngle() {
		return leftArmAngle;
	}

	public void setLeftArmAngle(float leftArmAngle) {
		this.leftArmAngle = leftArmAngle;
	}

	public float getRightArmAngle() {
		return rightArmAngle;
	}

	public void setRightArmAngle(float rightArmAngle) {
		this.rightArmAngle = rightArmAngle;
	}

	public float getLeftLegAngle() {
		return leftLegAngle;
	}

	public void setLeftLegAngle(float leftLegAngle) {
		this.leftLegAngle = leftLegAngle;
	}

	public float getRightLegAngle() {
		return rightLegAngle;
	}

	public void setRightLegAngle(float rightLegAngle) {
		this.rightLegAngle = rightLegAngle;
	}

	public int getLegAnimationStep() {
		return legAnimationStep;
	}

	public void setLegAnimationStep(int legAnimationStep) {
		this.legAnimationStep = legAnimationStep;
	}

	public int getBodyAnimationStep() {
		return bodyAnimationStep;
	}

	public void setBodyAnimationStep(int bodyAnimationStep) {
		this.bodyAnimationStep = bodyAnimationStep;
	}

}
