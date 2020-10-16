package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import rendering.Board;

public class Lava extends Powder {

	public Lava(Vector2f pos, Vector2f accl) {
		super(pos, accl);
		init();
	}

	@Override
	public void init() {
		density = 20;
		mass = density;
		friction = 0.05f;
		mmntm = new Vector2f(mass * vel.getX(), mass * vel.getY());
		color = Color.orange;
		state = Powder.STATE_LIQUID;
	}

	@Override
	public void interaction(Powder[][] arr) {
		Powder[] nbrs = getNeighbors(arr);
		for (int i = 0; i < nbrs.length; i++) {
			if (nbrs[i] != null && nbrs[i] instanceof Rock || (nbrs[i] != null && nbrs[i] instanceof Water)) {
				arr[(int) nbrs[i].getPos().getX()][(int) nbrs[i].getPos().getY()] = null;
				nbrs[i] = new Lava(nbrs[i].getPos(), nbrs[i].getAccl());
				arr[(int) nbrs[i].getPos().getX()][(int) nbrs[i].getPos().getY()] = new Lava(pos, accl);
			}
			/*if (nbrs[i] != null && nbrs[i] instanceof Water) {
				arr[(int) nbrs[i].getPos().getX()][(int) nbrs[i].getPos().getY()] = null;
				nbrs[i] = new Rock(nbrs[i].getPos(), nbrs[i].getAccl());
				arr[(int) nbrs[i].getPos().getX()][(int) nbrs[i].getPos().getY()] = new Rock(pos, accl);
			}*/
		}

	}

}
