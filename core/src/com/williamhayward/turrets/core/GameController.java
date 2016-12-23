package com.williamhayward.turrets.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.williamhayward.turrets.entities.enemies.Enemy;
import com.williamhayward.turrets.entities.enemies.Spawner;
import com.williamhayward.turrets.entities.players.Hero;
import com.williamhayward.turrets.entities.players.Player;
import com.williamhayward.turrets.graphics.Toast;
import com.williamhayward.turrets.input.InputManager;
import com.williamhayward.turrets.input.KeyboardInput;
import com.williamhayward.turrets.ui.UIManager;

public class GameController {
	private GameStage game;
	
	private boolean paused = false;
	private boolean buildMode = true;
	
	private float buildTime = 30f;
	private float buildTimer;
	
	private Player player;
	public GameController() {
		game = new GameStage();
		game.setController(this);
		
		TiledMap map = new TmxMapLoader().load("tiles/test.tmx");
		game.setMap(map);
		
		player = new Hero();
		player.addMoney(50);
		
		game.addActor(player);
		
		
        UIManager ui = new UIManager(player);
        player.setUI(ui);		

		InputManager input = new KeyboardInput(game);
		player.setInput(input);
		
		player.spawn();
		startBuild();
	}
	
	public void toast(String message) {
		toast(message, Toast.DURATION);
	}
	
	public void toast(String message, float duration) {
		for (Player player: game.getActors(Player.class)) {
			Toast bread = new Toast(message, player.getUI().getSkin(), duration);
			player.getUI().toast(bread);
		}
	}
	
	public void tick(float delta) {
		if (paused) {
			player.act(delta);
			return;
		}
		if (buildMode) {
			buildTimer -= delta;
			if (buildTimer <= 0) {
				this.startSiege();
			}
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
	
	public void startSiege() {
		buildMode = false;
		for (Spawner spawner: game.getActors(Spawner.class)) {
			spawner.nextCommand();
		}
		player.getUI().setSiege();
	}
	
	public void startBuild() {
		buildMode = true;
		buildTimer = buildTime;
		player.getUI().setBuild();
	}
	
	public boolean siegeEnded() {
		for (Spawner spawner: game.getActors(Spawner.class)) {
			if (!spawner.isBuild()) {
				return false;
			}
		}
		if (!game.getActors(Enemy.class).isEmpty()) {
			return false;
		}
		return true;
	}
	
	public boolean isBuildMode() {
		return buildMode;
	}

	public void setBuildMode(boolean buildMode) {
		this.buildMode = buildMode;
	}
	
	public float getBuildTimer() {
		return buildTimer;
	}
}
