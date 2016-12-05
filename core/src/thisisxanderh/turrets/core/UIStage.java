package thisisxanderh.turrets.core;

import javax.swing.GroupLayout.Alignment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import thisisxanderh.turrets.actors.players.Player;

public class UIStage extends Stage {
	private GameStage gameStage;
	private Skin skin;
	
	private PlayUI play;
	private BuildUI build;
	private PauseUI pause;
	
	private UserInterface active;
	
	private GameController controller;
	
	public UIStage(GameStage gameStage, Player player) {
		super();
        this.gameStage = gameStage;
        gameStage.setUiStage(this);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        build = new BuildUI(player, skin);
        play = new PlayUI(player, skin);
        
        pause = new PauseUI(player, skin);
        active = play;
        this.addActor(active);
    }
	
	public void setController(GameController controller) {
		this.controller = controller;
	}
	
	public GameController getController() {
		return controller;
	}
	
	public Coordinate getOrigin() {
		Camera camera = gameStage.getCamera();
		float x = camera.position.x - camera.viewportWidth;
		float y = camera.position.y + camera.viewportHeight;
		
		return new Coordinate(x, y);
	}
	
	public GameStage getGameStage() {
		return gameStage;
	}
	
	public float getZoom() {
		OrthographicCamera camera = (OrthographicCamera) gameStage.getCamera();
		return camera.zoom;
	}
	
	public void pause() {
		active.remove();
		this.addActor(pause);
	}
	
	public void unPause() {
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
		}
	}
	
	private class PlayUI extends UserInterface {
		private PlayUI(Player player, Skin skin) {
			super();
			
		}
		
	}
	
	private class PauseUI extends UserInterface {
		Player player;
		private PauseUI(Player player, Skin skin) {
			super();
			this.player = player;
			Label label = new Label("Paused", skin);
			this.addActor(label);
		}
		
		@Override
		public void act(float delta) {
			player.act(delta);
		}
	}
	
}
