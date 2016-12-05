package thisisxanderh.turrets.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import thisisxanderh.turrets.actors.players.Hero;
import thisisxanderh.turrets.actors.players.Player;
import thisisxanderh.turrets.input.InputManager;

public class GameController {
	GameStage game;
	UIStage ui;
	public GameController() {
		game = new GameStage();
		
		TiledMap map = new TmxMapLoader().load("tiles/test.tmx");
		game.setMap(map);
		
		Player player = new Hero();
		game.addActor(player);
		
		ui = new UIStage(game, player);

        Gdx.input.setInputProcessor(ui);
        InputManager input = new InputManager(ui);
        player.setInput(input);		
		
		player.spawn();
	}
	
	public void tick(float delta) {
		game.act(delta);
		ui.act(delta);
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
}
