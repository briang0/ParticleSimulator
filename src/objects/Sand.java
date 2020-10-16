package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class Sand extends Powder{

	
	
	public Sand(Vector2f pos, Vector2f accl) {
		super(pos, accl);
		init();
	}

	@Override
	public void init() {
		density = 1;
		mass = density;
		friction = 0.25f;
		mmntm = new Vector2f(mass*vel.getX(), mass*vel.getY());
		color = Color.yellow;
		state = Powder.STATE_SOLID;
	}

	@Override
	public void interaction(Powder[][] grid) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
