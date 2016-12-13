package thisisxanderh.turrets.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import thisisxanderh.turrets.actors.enemies.Enemy;
import thisisxanderh.turrets.actors.enemies.Spawner;
import thisisxanderh.turrets.actors.players.Hero;
import thisisxanderh.turrets.actors.players.Player;
import thisisxanderh.turrets.graphics.Toast;
import thisisxanderh.turrets.input.Input;

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
		game.addActor(player);
		
		
        Input input = new Input(player);
        player.setInput(input);		
		
		player.spawn();
		startBuild();
	}
	
	public void notify(String message) {
		notify(message, Toast.DURATION);
	}
	
	public void notify(String message, float duration) {
		for (Player player: game.getActors(Player.class)) {
			
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
				this.startPlay();
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
	
	public void startPlay() {
		buildMode = false;
		for (Spawner spawner: game.getActors(Spawner.class)) {
			spawner.nextCommand();
		}
		player.getInput().setPlay();
	}
	
	public void startBuild() {
		buildMode = true;
		buildTimer = buildTime;
		player.getInput().setBuild();
	}
	
	public boolean endPlay() {
		for (Spawner spawner: game.getActors(Spawner.class)) {
			if (!spawner.isBuild()) {
				return false;
			}
		}
		if (game.getActors(Enemy.class).size() > 0) {
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
