package thisisxanderh.turrets.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import thisisxanderh.turrets.actors.players.Player;

public class UIStage extends Stage {
	private GameStage gameStage;
	private Skin skin;
	public UIStage(GameStage gameStage, Player player) {
		super();
        this.gameStage = gameStage;
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

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
	
	
}
