package io;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

public class MouseListener {

	private Input input;
	private boolean leftMouseMuted;
	private boolean rightMouseMuted;
	
	
	public MouseListener() {
		input = new Input(720);
	}
	
	public boolean leftMouseDown() {
		return input.isMouseButtonDown(0) && !leftMouseMuted;
	}
	
	public void muteLeftMouse() {
		leftMouseMuted = true;
	}
	
	public void unmuteLeftMouse() {
		leftMouseMuted = false;
	}
	
	public boolean rightMouseDown() {
		return input.isMouseButtonDown(1) && !rightMouseMuted;
	}
	
	public void muteRightMouse() {
		rightMouseMuted = true;
	}
	
	public void unmuteRightMouse() {
		rightMouseMuted = false;
	}
	
	public Vector2f getMousePos() {
		return new Vector2f(input.getMouseX(), input.getMouseY());
	}
	
	
}
