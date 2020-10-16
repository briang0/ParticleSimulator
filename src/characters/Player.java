package characters;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import geom.PlayerModel;

public class Player {

	private Vector2f pos;
	
	private Color color;
	
	private float vX=0;
	private float vY=0;
	private float aX=0;
	private float aY=0;
	
	private PlayerModel pm;
	
	public Player(Vector2f pos) {
		this.pos = pos;
		color = Color.red;
		pm = new PlayerModel(this);
	}

	public Vector2f getPos() {
		return pos;
	}

	public void setPos(Vector2f pos) {
		this.pos = pos;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getvX() {
		return vX;
	}

	public void setvX(float vX) {
		this.vX = vX;
	}

	public float getvY() {
		return vY;
	}

	public void setvY(float vY) {
		this.vY = vY;
	}

	public float getaX() {
		return aX;
	}

	public void setaX(float aX) {
		this.aX = aX;
	}

	public float getaY() {
		return aY;
	}

	public void setaY(float aY) {
		this.aY = aY;
	}

	public PlayerModel getPm() {
		return pm;
	}

	public void setPm(PlayerModel pm) {
		this.pm = pm;
	}

	
}
