package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class Wind extends Powder {

	Vector2f permPos;

	public Wind(Vector2f pos, Vector2f accl) {
		super(pos, accl);
		this.pos = pos;
		permPos = pos;
		this.accl = new Vector2f(10, -40);
		init();
	}

	@Override
	public void init() {
		density = 1;
		mass = density;
		friction = 0.05f;
		mmntm = new Vector2f(mass * vel.getX(), mass * vel.getY());
		color = Color.cyan;
		state = Powder.STATE_SOLID;
	}

	@Override
	public void interaction(Powder[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] != null) {
					float distance = grid[i][j].getPos().distance(pos);
					Vector2f sum = grid[i][j].getVel()
							.add(new Vector2f((accl.getX() * 1 / (distance)), (accl.getY() / distance)));
					if (i != pos.getX() && j != pos.getY() && sum.getX() != 0
							&& sum.getY() != 0) {
						grid[i][j].setVel(sum);
					}
				}
			}
		}
	}

}
