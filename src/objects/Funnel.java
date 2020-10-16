package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

public class Funnel implements Structure{

	private Vector2f pos;
	private Line l1, l2, l3, l4;
	
	
	public Funnel(Vector2f pos) {
		this.pos = pos;
		l1 = new Line(pos.getX() - 1, pos.getY(), pos.getX() - 25, pos.getY() - 25);
		l2 = new Line(pos.getX() - 2, pos.getY(), pos.getX() - 26, pos.getY() - 26);
		l3 = new Line(pos.getX() + 1, pos.getY(), pos.getX() + 25, pos.getY() - 25);
		l4 = new Line(pos.getX() + 2, pos.getY(), pos.getX() +26, pos.getY() - 26);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.draw(l1);
		g.draw(l2);
		g.draw(l3);
		g.draw(l4);
	}

	@Override
	public boolean intersects(Vector2f vec) {
		Line l = new Line(vec.getX(), vec.getY(), vec.getX(), vec.getY());
		if (l.intersects(l1) || l.intersects(l2) || l.intersects(l3) || l.intersects(l4)) {
			return true;
		}
		return false;
	}
	
}
