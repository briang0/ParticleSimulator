package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class Block extends Powder{

	Vector2f permPos;
	
	public Block(Vector2f pos, Vector2f accl) {
		super(pos, accl);
		this.pos = pos;
		permPos = pos;
		this.accl = accl;
		init();
	}

	@Override
	public void init() {
		density = 1;
		mass = density;
		friction = 0.05f;
		mmntm = new Vector2f(mass * vel.getX(), mass * vel.getY());
		color = Color.gray;
		state = Powder.STATE_SOLID;
	}

	@Override
	public void interaction(Powder[][] grid) {
		pos = permPos;
		accl = new Vector2f(0, 0);
		vel = new Vector2f(0, 0);
	}

}
