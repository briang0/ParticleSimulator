package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import rendering.Board;

public class Water extends Powder{

	public Water(Vector2f pos, Vector2f accl) {
		super(pos, accl);
		init();
	}

	@Override
	public void init() {
		density = 20;
		mass = density;
		friction = 0.05f;
		mmntm = new Vector2f(mass*vel.getX(), mass*vel.getY());
		color = Color.blue;
		state = Powder.STATE_LIQUID;
	}

	@Override
	public void interaction(Powder[][] grid) {
		if (pos.getY()+1 < Board.HEIGHT && grid[(int) pos.getX()][(int) pos.getY()+1] instanceof Metal) {
			Metal m = (Metal) grid[(int)pos.getX()][(int) pos.getY()+1];
			m.corrode(grid);
		}
	}

	
	
}
