package thisisxanderh.turrets.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.entities.players.Player;

public class BuildUI extends UserInterface {
	Label timer;
	Player player;
	public BuildUI(Player player, Skin skin) {
		super();
		this.player = player;
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
            	stage.getController().startPlay();
            }
        });
        
        this.addActor(button);
        timer = new Label("", skin);
        timer.setSize(50, 50);
        timer.setPosition(Gdx.graphics.getWidth() - timer.getWidth() - 5,
        		Gdx.graphics.getHeight() - timer.getHeight() - 5);
        this.addActor(timer);
	}
	
	@Override
	public void act(float delta) {
        GameStage stage = (GameStage) player.getStage();
        int count = (int) Math.ceil(stage.getController().getBuildTimer());
		timer.setText(String.valueOf(count));
		
	}
}