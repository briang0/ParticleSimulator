package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class Lead extends Powder implements Metal{

	public Lead(Vector2f pos, Vector2f accl) {
		super(pos, accl);
		init();
	}

	@Override
	public void init() {
		density = 5;
		mass = density;
		friction = 0.15f;
		mmntm = new Vector2f(mass*vel.getX(), mass*vel.getY());
		color = Color.lightGray;
		state = Powder.STATE_SOLID;
	}

	@Override
	public void interaction(Powder[][] grid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void corrode(Powder[][] arr) {
		arr[(int) pos.getX()][(int) pos.getY()] = new Sand(pos, accl);
	}

}
