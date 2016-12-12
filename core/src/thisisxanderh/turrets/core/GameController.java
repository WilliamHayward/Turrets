package thisisxanderh.turrets.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import thisisxanderh.turrets.actors.players.Hero;
import thisisxanderh.turrets.actors.players.Player;
import thisisxanderh.turrets.input.InputManager;

public class GameController {
	private GameStage game;
	
	private boolean paused = false;
	private Player player;
	public GameController() {
		game = new GameStage();
		game.setController(this);
		
		TiledMap map = new TmxMapLoader().load("tiles/test.tmx");
		game.setMap(map);
		
		player = new Hero();
		game.addActor(player);
		
		
        InputManager input = new InputManager(player);
        player.setInput(input);		
		
		player.spawn();
	}
	
	public void tick(float delta) {
		if (paused) {
			player.act(delta);
			return;
		}
		game.act(delta);
	}
	
	public void draw() {
		game.draw();
	}
	
	public void resize (int width, int height) {
		
		game.getViewport().update(width, height, true);
	}
	
	public void dispose() {
		game.dispose();
	}

	public boolean isPaused() {
		return paused;
	}
	
	public void togglePause() {
		if (paused) {
			unPause();
		} else {
			pause();
		}
	}
	public void setPaused(boolean paused) {
		if (paused) {
			pause();
		} else {
			unPause();
		}
	}
	
	public void pause() {
		paused = true;
	}
	
	public void unPause() {
		paused = false;
	}
}
