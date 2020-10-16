package core;

import org.newdawn.slick.AppGameContainer;

import rendering.Board;

public class Main {

	public static void main(String[] args) {
		AppGameContainer gc;
		try {
			Board gameBoard = new Board("Game");
			gc = new AppGameContainer(gameBoard);
			gc.setAlwaysRender(true);
			gc.setDisplayMode(720, 720, false);
			gc.setFullscreen(false);
			gc.setTargetFrameRate(60);
			gc.start();

		} catch (Exception e) {
			
			
			e.printStackTrace();
		}
	}
}
