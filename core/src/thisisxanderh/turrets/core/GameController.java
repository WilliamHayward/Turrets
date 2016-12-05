package thisisxanderh.turrets.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import thisisxanderh.turrets.actors.players.Hero;
import thisisxanderh.turrets.actors.players.Player;
import thisisxanderh.turrets.input.InputManager;

public class GameController {
	private GameStage game;
	private UIStage ui;
	
	private boolean paused = false;
	public GameController() {
		game = new GameStage();
		game.setController(this);
		
		TiledMap map = new TmxMapLoader().load("tiles/test.tmx");
		game.setMap(map);
		
		Player player = new Hero();
		game.addActor(player);
		
		ui = new UIStage(game, player);
		ui.setController(this);
		
        Gdx.input.setInputProcessor(ui);
        InputManager input = new InputManager(ui);
        player.setInput(input);		
		
		player.spawn();
	}
	
	public void tick(float delta) {
		ui.act(delta);
		if (paused) {
			return;
		}
		game.act(delta);
	}
	
	public void draw() {
		game.draw();
		ui.draw();
	}
	
	public void resize (int width, int height) {
		
		game.getViewport().update(width, height, true);
		ui.getViewport().update(width, height);
	}
	
	public void dispose() {
		game.dispose();
		ui.dispose();
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
		ui.pause();
	}
	
	public void unPause() {
		paused = false;
		ui.unPause();
	}
}
