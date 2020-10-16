package rendering;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.Particle;

import characters.Player;
import io.KeyboardListener;
import io.MouseListener;
import objects.Block;
import objects.Funnel;
import objects.Lava;
import objects.Lead;
import objects.Powder;
import objects.Rock;
import objects.Sand;
import objects.Structure;
import objects.Water;
import objects.Wind;

public class Board extends BasicGame {

	public static final int WIDTH = 720;
	public static final int HEIGHT = 720;

	private int material;

	Funnel fun;

	private int tick = 0;

	private Powder[][] particles;

	private MouseListener ml;
	private KeyboardListener kl;

	private long endTime = 0;

	private ArrayList<Structure> structures;

	public Board(String title) {
		super(title);
		material = 1;
		kl = new KeyboardListener();
		structures = new ArrayList<Structure>();
		tick = 0;
		particles = new Powder[WIDTH][HEIGHT];
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				particles[i][j] = null;
			}
		}
		ml = new MouseListener();
		fun = new Funnel(new Vector2f(720 / 2, 720 / 2));
		structures.add(fun);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		ArrayList<Powder> rendered = new ArrayList<Powder>();
		int numParticles = 0;
		for (int i = WIDTH - 1; i > 0; i--) {
			for (int j = HEIGHT - 1; j >= 0; j--) {
				Powder p = particles[i][j];
				if (p != null && !rendered.contains(p)) {
					g.setColor(p.getColor());
					float x = p.getPos().getX();
					float y = p.getPos().getY();
					g.drawLine(x, y, x, y);
					p.updateValues(particles, tick, structures);
					rendered.add(p);
					numParticles++;
					p.interaction(particles);
				}
			}
		}
		String mat = "";
		if (material == 1) {
			mat = "Sand";
		} else if (material == 2) {
			mat = "Water";
		} else if (material == 3) {
			mat = "Lead";
		} else if (material == 4) {
			mat = "Rock";
		} else if (material == 5) {
			mat = "Lava";
		} else if (material == 6) {
			mat = "Block";
		} else if (material == 7) {
			mat = "Wind";
		}
		g.setColor(Color.white);
		g.drawString("# of particles = " + numParticles + " Material: " + mat, 100, 10);
		// fun.render(g);
		tick++;

	}

	@Override
	public void init(GameContainer gc) throws SlickException {

	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Vector2f mousePos = ml.getMousePos();
		if (mousePos.getX() > Board.WIDTH - 4) {
			mousePos = new Vector2f(Board.WIDTH - 4, mousePos.getY());
		}
		if (mousePos.getX() <= 4) {
			mousePos = new Vector2f(4, mousePos.getY());
		}
		if (mousePos.getY() >= Board.HEIGHT - 4) {
			mousePos = new Vector2f(mousePos.getX(), Board.HEIGHT-4);
		}
		if (mousePos.getX() <= 4) {
			mousePos = new Vector2f(mousePos.getX(), 4);
		}
		if (ml.leftMouseDown() && particles[(int) mousePos.getX()][(int) mousePos.getY()] == null) {
			mousePos = new Vector2f(mousePos.getX(), mousePos.getY());

			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (material == 1) {
						particles[(int) mousePos.getX() + i][(int) mousePos.getY() + j] = new Sand(
								new Vector2f(ml.getMousePos().getX() + i, ml.getMousePos().getY() + j),
								new Vector2f(0, 0));
					} else if (material == 2) {
						particles[(int) mousePos.getX() + i][(int) mousePos.getY() + j] = new Water(
								new Vector2f(ml.getMousePos().getX() + i, ml.getMousePos().getY() + j),
								new Vector2f(0, 0));
					} else if (material == 3) {
						particles[(int) mousePos.getX() + i][(int) mousePos.getY() + j] = new Lead(
								new Vector2f(ml.getMousePos().getX() + i, ml.getMousePos().getY() + j),
								new Vector2f(0, 0));
					} else if (material == 4) {
						particles[(int) mousePos.getX() + i][(int) mousePos.getY() + j] = new Rock(
								new Vector2f(ml.getMousePos().getX() + i, ml.getMousePos().getY() + j),
								new Vector2f(0, 0));
					} else if (material == 5) {
						particles[(int) mousePos.getX() + i][(int) mousePos.getY() + j] = new Lava(
								new Vector2f(ml.getMousePos().getX() + i, ml.getMousePos().getY() + j),
								new Vector2f(0, 0));
					} else if (material == 6) {
						particles[(int) mousePos.getX() + i][(int) mousePos.getY() + j] = new Block(
								new Vector2f(ml.getMousePos().getX() + i, ml.getMousePos().getY() + j),
								new Vector2f(0, 0));
					}
				}

			}
			if (material == 7) {
				particles[(int) mousePos.getX()][(int) mousePos.getY()] = new Wind(
						new Vector2f(ml.getMousePos().getX(), ml.getMousePos().getY()), new Vector2f(0, 0));
			}
			// particles[(int) mousePos.getX()+10][(int) mousePos.getY()] = new Water(new
			// Vector2f(mousePos.getX()+10, mousePos.getY()), new Vector2f(0, 0));

		} else {

		}
		if (kl.isKeyDown(Input.KEY_1)) {
			material = 1;
		}
		if (kl.isKeyDown(Input.KEY_2)) {
			material = 2;
		}
		if (kl.isKeyDown(Input.KEY_3)) {
			material = 3;
		}
		if (kl.isKeyDown(Input.KEY_4)) {
			material = 4;
		}
		if (kl.isKeyDown(Input.KEY_5)) {
			material = 5;
		}
		if (kl.isKeyDown(Input.KEY_6)) {
			material = 6;
		}
		if (kl.isKeyDown(Input.KEY_7)) {
			material = 7;
		}
	}

}
