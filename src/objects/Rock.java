package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class Rock extends Powder{

	public Rock(Vector2f pos, Vector2f accl) {
		super(pos, accl);
		init();
	}

	@Override
	public void init() {
		density = 3;
		mass = density;
		friction = 0.4f;
		mmntm = new Vector2f(mass*vel.getX(), mass*vel.getY());
		color = Color.green;
		state = Powder.STATE_SOLID;
	}

	@Override
	public void interaction(Powder[][] grid) {
		
	}

}
