package objects;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import rendering.Board;
import util.Physics;

public abstract class Powder {

	public static final int NORTH = 0;
	public static final int NORTH_EAST = 1;
	public static final int EAST = 2;
	public static final int SOUTH_EAST = 3;
	public static final int SOUTH = 4;
	public static final int SOUTH_WEST = 5;
	public static final int WEST = 6;
	public static final int NORTH_WEST = 7;
	public static final int STATE_SOLID = 0;
	public static final int STATE_LIQUID = 1;
	public static final int STATE_GAS = 2;

	protected Vector2f pos;
	protected Color color;

	protected Vector2f accl;
	protected Vector2f mmntm;
	protected Vector2f vel;

	protected float density;
	protected float mass;
	protected float friction;

	private float bottomFriction;

	protected int state;

	public Powder(Vector2f pos, Vector2f accl) {
		this.pos = pos;
		this.accl = accl;
		vel = new Vector2f(1, 1);
		bottomFriction = 0;
	}

	public abstract void init();

	public abstract void interaction(Powder[][] grid);

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

	public Powder[] getNeighbors(Powder[][] particles) {
		int width = particles.length;
		int height = particles[0].length;
		int x = (int) pos.getX();
		int y = (int) pos.getY();

		Powder north = null, northeast = null, east = null, southeast = null, south = null, southwest = null,
				west = null, northwest = null;
		if (y - 1 >= 0) {
			north = particles[x][y];
		}
		if (y - 1 >= 0 && x + 1 < width) {
			northeast = particles[x + 1][y - 1];
		}
		if (x + 1 < width) {
			east = particles[x + 1][y];
		}
		if (x + 1 < width && y + 1 < height) {
			southeast = particles[x + 1][y + 1];
		}
		if (y + 1 < height) {
			south = particles[x][y + 1];
		}
		if (y + 1 < height && x - 1 >= 0) {
			southwest = particles[x - 1][y + 1];
		}
		if (x - 1 >= 0) {
			west = particles[x - 1][y];
		}
		if (x - 1 >= 0 && y - 1 >= 0) {
			northwest = particles[x - 1][y - 1];
		}
		return new Powder[] { north, northeast, east, southeast, south, southwest, west, northwest };
	}

	public boolean canMoveNorth(Powder[][] particles) {
		return getNeighbors(particles)[NORTH] == null;
	}

	public boolean canMoveNorthEast(Powder[][] particles) {
		return getNeighbors(particles)[NORTH_EAST] == null;
	}

	public boolean canMoveEast(Powder[][] particles) {
		return getNeighbors(particles)[EAST] == null;
	}

	public boolean canMoveSouthEast(Powder[][] particles) {
		return getNeighbors(particles)[SOUTH_EAST] == null;
	}

	public boolean canMoveSouth(Powder[][] particles) {
		return getNeighbors(particles)[SOUTH] == null;
	}

	public boolean canMoveSouthWest(Powder[][] particles) {
		return getNeighbors(particles)[SOUTH_WEST] == null;
	}

	public boolean canMoveWest(Powder[][] particles) {
		return getNeighbors(particles)[WEST] == null;
	}

	public boolean canMoveNorthWest(Powder[][] particles) {
		return getNeighbors(particles)[NORTH_WEST] == null;
	}

	public void updateValues(Powder[][] particles, int tick, ArrayList<Structure> structs) {
		if (this instanceof Block || this instanceof Wind) {
			return;
		}

		Powder[] neighbors = getNeighbors(particles);
		bottomFriction = 0;

		boolean nullNeighbor = false;
		for (int i = 0; i < neighbors.length; i++) {
			if (neighbors[i] == null) {
				nullNeighbor = true;
			}
		}
		if (!nullNeighbor) {
			return;
		}

		mmntm = new Vector2f(accl.getX() + vel.getX() * mass, accl.getY() + vel.getY() * mass);
		int temp = 0;
		int xVal = (int) Math.abs(vel.getX());
		int yVal = (int) ((int) Math.abs(vel.getY()));
		boolean yBigger = yVal > xVal ? true : false;
		if (yBigger) {

		}
		while (xVal > 0 || yVal > 0) {
			if (yBigger) {
				while (yVal > 0) {
					moveVertically(particles, structs);
					collisionCheck(neighbors);
					if (xVal > 0 && yVal % xVal == 0) {
						moveHorizontally(particles, structs);
						collisionCheck(neighbors);
						xVal--;
					}
					yVal--;
				}
			} else {
				while (xVal > 0) {
					moveHorizontally(particles, structs);
					collisionCheck(neighbors);
					if (yVal > 0 && xVal % yVal == 0) {
						moveVertically(particles, structs);
						collisionCheck(neighbors);
						yVal--;
					}
					xVal--;
				}
			}
		}
		if (pos.getY() == Board.HEIGHT - 1) {
			bottomFriction = 0.001f;
		} else if (neighbors[SOUTH] != null) {
			bottomFriction = neighbors[SOUTH].getFriction();
		}
		if (bottomFriction != 0 && vel.getX() > 0) {
			vel = new Vector2f(vel.getX() - ((friction + bottomFriction)), vel.getY());
			if (vel.getX() < 0) {
				vel = new Vector2f(0, vel.getY());
			}
		} else if (bottomFriction != 0 && vel.getX() < 0) {
			vel = new Vector2f(vel.getX() + (friction + bottomFriction), vel.getY());
			if (vel.getX() > 0) {
				vel = new Vector2f(0, vel.getY());
			}
		}
		if (vel.getX() > Physics.MAX_VELOCITY_X) {
			vel = new Vector2f(50, vel.getY());
		}

		float totalMass = getMassAbove(particles);
		Random rand = new Random();
		int baseLine = rand.nextInt(1000);
		if (state == Powder.STATE_LIQUID) {
			baseLine = rand.nextInt(2);
		}
		int shift = -1;
		boolean moveRight=false, moveLeft=false;
		int actual = rand.nextInt((int) totalMass);
		if (actual > baseLine && totalMass > 10) {
			if (neighbors[2] == null || neighbors[3] == null) {
				shift = 0;
				moveLeft = true;
			}
			if (neighbors[6] == null || neighbors[7] == null) {
					shift = 1;
					moveRight = true;
			}
			if (moveRight && moveLeft) {
				shift = rand.nextInt(2);
			}
			if (shift == 0) {
				vel = new Vector2f(-1, vel.getY());
			} else {
				vel = new Vector2f(1, vel.getY());
			}
		}
		vel = new Vector2f(vel.getX(), vel.getY() + Physics.GRAVITY);
	}

	private void collisionCheck(Powder[] neighbors) {
		if (pos.getY() >= Board.WIDTH - 1) {
			if (vel.getY() >= 0) {
				vel = new Vector2f(vel.getX(), -(vel.getY() / (2 * density)));
			} else {
				vel = new Vector2f(vel.getX(), 0);
			}

			// System.out.println(vel);
		}
		if (pos.getX() <= 0) {
			vel = new Vector2f(-vel.getX() / (2 * density), vel.getY());
		}

		if (pos.getX() >= Board.WIDTH - 1) {
			vel = new Vector2f(-vel.getX() / (2 * density), vel.getY());
		}

		if (neighbors[2] != null) {
			if (vel.getX() >= 0) {
				vel.set(-vel.getX() - (neighbors[2].getVel().getX() * -0.75f) + (neighbors[2].getVel().getX() / 2),
						vel.getY());
				// System.out.println(vel);
				neighbors[2]
						.setVel(new Vector2f(-neighbors[2].getVel().getX() - (vel.getX() * -0.75f) + (vel.getX() / (2)),
								neighbors[2].getVel().getY()));
			} else {
				vel = new Vector2f(0, vel.getY());
			}
		}
		if (neighbors[4] != null) {
			if (vel.getY() >= 1) {
				vel.set(vel.getX(),
						(int) (-vel.getY() - (neighbors[4].getVel().getY()) + (neighbors[4].getVel().getY() / 2)));
				// System.out.println(vel);
			} else {
				vel = new Vector2f(vel.getX(), 0);
			}
		}
		// if (neighbors[6] != null) {
		// if (vel.getX() <= 0) {
		// vel.set(-vel.getX() + (neighbors[6].getVel().getX() / 2), vel.getY());
		// //System.out.println(vel);
		// neighbors[6]
		// .setVel(new Vector2f(-neighbors[6].getVel().getX() - (vel.getX() * -0.75f) +
		// (vel.getX() / (2)),
		// neighbors[6].getVel().getY()));
		// } else {
		// vel = new Vector2f(0, vel.getY());
		// }
		// }
		// if (ne)
		// int n = neighbors.length;
		// for (int i = 0; i < n; i++) {
		// Powder nbr = neighbors[i];
		// float velXThis = vel.getX();
		// float velYThis = vel.getY();
		// float velXNbr = 0;
		// float velYNbr = 0;
		// if (nbr != null) {
		// velXThis = Physics.calculateCollisionVelcoity1(mass, nbr.getMass(),
		// vel.getX());
		// velYThis = Physics.calculateCollisionVelcoity1(mass, nbr.getMass(),
		// vel.getY());
		// velXNbr = Physics.calculateCollisionVelcoity1(mass, nbr.getMass(),
		// vel.getX());
		// velYNbr = Physics.calculateCollisionVelcoity1(mass, nbr.getMass(),
		// vel.getY());
		// }
		// if (nbr != null && vel != null) {
		// if (i == 0 || i == 4) {
		// if (vel.getY() * nbr.getVel().getY() <= 0) {
		// //System.out.println("resch0");
		// vel = new Vector2f(vel.getX(), -velYThis);
		// nbr.setVel(new Vector2f(nbr.getPos().getX(), velYNbr));
		// if (i == 4) {
		// bottomFriction = nbr.getFriction();
		// }
		// }
		// }
		// if ((i == 1 || i == 3 || i == 5 || i == 7)) {
		// if (vel.getY() * nbr.getVel().getY() <= 0 && vel.getX() * nbr.getPos().getX()
		// < 0) {
		// vel = new Vector2f(velXThis, velYThis);
		// System.out.println("resch1");
		// nbr.setVel(new Vector2f(velXNbr, velYNbr));
		// if (i == 3 || i == 5) {
		// friction = nbr.getFriction();
		// }
		// }
		// }
		// if ((i == 6 || i == 2)) {
		// if (vel.getX() * nbr.getPos().getX() <= 0) {
		// vel = new Vector2f(velXThis, vel.getY());
		// nbr.setVel(new Vector2f(velXNbr, nbr.getPos().getY()));
		// }
		// }
		// }
		// }
	}

	private void moveVertically(Powder[][] particles, ArrayList<Structure> structs) {
		if ((int) vel.getY() != 0) {
			// xPos
			if (vel.getY() <= 0 && pos.getY() - 1 >= 0 && particles[(int) pos.getX()][(int) pos.getY() - 1] == null
					&& !intersectsAStructure(new Vector2f(pos.getX(), pos.getY() - 1), structs)) {
				pos = new Vector2f(pos.getX(), pos.getY() - 1);
				particles[(int) pos.getX()][(int) pos.getY() + 1] = null;
				particles[(int) pos.getX()][(int) pos.getY()] = this;
			}
			if (vel.getY() > 0 && pos.getY() + 1 < Board.WIDTH
					&& particles[(int) pos.getX()][(int) pos.getY() + 1] == null
					&& !intersectsAStructure(new Vector2f(pos.getX(), pos.getY() + 1), structs)) {
				pos = new Vector2f(pos.getX(), pos.getY() + 1);

				particles[(int) pos.getX()][(int) pos.getY() - 1] = null;
				particles[(int) pos.getX()][(int) pos.getY()] = this;
			}
		} else {
			// vel = new Vector2f(vel.getX(), 1);
		}
	}

	private void moveHorizontally(Powder[][] particles, ArrayList<Structure> structs) {
		if ((int) vel.getX() != 0) {
			// xPos
			if (vel.getX() < 0 && pos.getX() - 1 >= 0 && particles[(int) pos.getX() - 1][(int) pos.getY()] == null
					&& !intersectsAStructure(new Vector2f(pos.getX() - 1, pos.getY()), structs)) {
				pos = new Vector2f(pos.getX() - 1, pos.getY());
				particles[(int) pos.getX() + 1][(int) pos.getY()] = null;
				particles[(int) pos.getX()][(int) pos.getY()] = this;
			}
			if (vel.getX() > 0 && pos.getX() + 1 < Board.WIDTH
					&& particles[(int) pos.getX() + 1][(int) pos.getY()] == null
					&& !intersectsAStructure(new Vector2f(pos.getX() + 1, pos.getY()), structs)) {
				pos = new Vector2f(pos.getX() + 1, pos.getY());
				particles[(int) pos.getX() - 1][(int) pos.getY()] = null;
				particles[(int) pos.getX()][(int) pos.getY()] = this;
			} else {
				// vel = new Vector2f(0, vel.getY());
			}
		}
	}

	public float getMassAbove(Powder[][] particles) {
		float totalMass = this.mass;
		int level = 1;
		if (pos.getY() == 0) {
			return this.mass;
		}
		while (particles[(int) pos.getX()][(int) pos.getY() - level] != null) {
			totalMass += particles[(int) pos.getX()][(int) pos.getY() - level].getMass();
			level++;
			if (pos.getY() - level <= 0) {
				break;
			}
		}
		return totalMass;
	}

	public boolean intersectsAStructure(Vector2f vec, ArrayList<Structure> structs) {
		//

		return false;
	}

	public Vector2f getAccl() {
		return accl;
	}

	public void setAccl(Vector2f accl) {
		this.accl = accl;
	}

	public Vector2f getMmntm() {
		return mmntm;
	}

	public void setMmntm(Vector2f mmntm) {
		this.mmntm = mmntm;
	}

	public Vector2f getVel() {
		return vel;
	}

	public void setVel(Vector2f vel) {
		this.vel = vel;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

}
