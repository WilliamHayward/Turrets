package thisisxanderh.turrets.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import thisisxanderh.turrets.actors.players.Player;
import thisisxanderh.turrets.core.Coordinate;
import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.terrain.Tile;

public class Input extends Stage {
	InputManager manager;
	private Player player;
	private Skin skin;
	private BuildUI build;
	private PlayUI play;
	private PauseUI pause;
	private UserInterface active;
	private boolean paused = false;
	public Input(Player player) {
		super();
		manager = new KeyboardInput(this);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        build = new BuildUI(player, skin);
        play = new PlayUI(player, skin);
        
        pause = new PauseUI(player, skin);
        active = build;
        this.addActor(active);
		this.player = player;
		
        Gdx.input.setInputProcessor(this);
	}
	
	public InputManager getManager() {
		return manager;
	}
	
	public void togglePause() {
		if (paused) {
			unPause();
		} else {
			pause();
		}
	}
	
	public void pause() {
		paused = true;
		active.remove();
		this.addActor(pause);
	}
	
	public void unPause() {
		paused = false;
		pause.remove();
		this.addActor(active);
	}
	
	private void setMode(UserInterface mode) {
		active.remove();
		active = mode;
		this.addActor(active);
	}
	
	public void toggleMode() {
		if (active instanceof PlayUI) {
			setMode(build);
		} else {
			setMode(play);
		}
	}
	
	@Override
	public void act(float delta) {

		active.act(delta);
		manager.act(delta);
	}
	
	/**
	 * Get cursor position in world
	 */
	public Coordinate getCursorPosition() {
		GameStage stage = (GameStage) player.getStage();
		OrthographicCamera camera = (OrthographicCamera) stage.getCamera();
		Coordinate cursor = manager.getCursor();
		Vector3 position = new Vector3(cursor.getX(), cursor.getY(), 0);
		camera.unproject(position);
		return new Coordinate(position.x, position.y);
	}
	
	/**
	 * Get cursor tile position in world
	 */
	public Coordinate getCursorTile() {
		Coordinate position = getCursorPosition();
		float x = (float) Math.floor(position.getX() / Tile.SIZE) * Tile.SIZE;
		float y = (float) Math.floor(position.getY() / Tile.SIZE) * Tile.SIZE;
		return new Coordinate(x, y);
	}
	
	private abstract class UserInterface extends Group {
		
	}
	
	private class BuildUI extends UserInterface {
		private BuildUI(Player player, Skin skin) {
			super();
			TextButton button = new TextButton("Machine Gun", skin, "default");
	        button.setWidth(100f);
	        button.setHeight(20f);
	        button.setPosition(5, 5);

	        button.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y) {
	            	player.selectBuilding(0);
	            }
	        });
	        
	        this.addActor(button);
	        
	        button = new TextButton("Cannon", skin, "default");
	        button.setWidth(100f);
	        button.setHeight(20f);
	        button.setPosition(110f, 5);

	        button.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y) {
	            	player.selectBuilding(1);
	            }
	        });
	        
	        this.addActor(button);
	        
	        button = new TextButton("Glue", skin, "default");
	        button.setWidth(100f);
	        button.setHeight(20f);
	        button.setPosition(215f, 5);

	        button.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y) {
	            	player.selectBuilding(2);
	            }
	        });
	        
	        this.addActor(button);
	        
	        button = new TextButton("Spikes", skin, "default");
	        button.setWidth(100f);
	        button.setHeight(20f);
	        button.setPosition(320f, 5);

	        button.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y) {
	            	player.selectBuilding(3);
	            }
	        });
	        
	        this.addActor(button);
	        
	        button = new TextButton("Play", skin, "default");
	        button.setWidth(100f);
	        button.setHeight(20f);
	        button.setPosition(5, Gdx.graphics.getHeight() - button.getHeight() - 5);

	        button.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y) {
	            	GameStage stage = (GameStage) player.getStage();
	            	stage.getController().startPlay();;
	            }
	        });
	        
	        this.addActor(button);
		}
	}
	
	private class PlayUI extends UserInterface {
		private PlayUI(Player player, Skin skin) {
			super();
			
		}
		
	}
	
	private class PauseUI extends UserInterface {
		private PauseUI(Player player, Skin skin) {
			super();
			Label label = new Label("Paused", skin);
			this.addActor(label);
		}
	}
}
