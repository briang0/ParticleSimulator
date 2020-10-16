package io;

import org.newdawn.slick.Input;

public class KeyboardListener {
	
	private Input i;
	
	public KeyboardListener() {
		i = new Input(720);
	}
	
	public boolean isKeyDown(int key) {
		return i.isKeyDown(key);
	}
	
}
