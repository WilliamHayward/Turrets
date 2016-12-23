package com.williamhayward.turrets.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.williamhayward.turrets.core.GameStage;
import com.williamhayward.turrets.entities.buildings.traps.Glue;
import com.williamhayward.turrets.entities.buildings.traps.Spikes;
import com.williamhayward.turrets.entities.buildings.turrets.Cannon;
import com.williamhayward.turrets.entities.buildings.turrets.MachineGun;
import com.williamhayward.turrets.entities.players.Player;

public class BuildUI extends UserInterface {
	Label timer;
	Player player;
	private static final float BUTTON_SIZE = 75f;
	private static final float PADDING = 10f;
	public BuildUI(Player player, Skin skin) {
		super();
		this.player = player;
		Group buttons = new Group();
		TextButton button = new TextButton("Machine Gun", skin, "default");
        button.setWidth(BUTTON_SIZE);
        button.setHeight(BUTTON_SIZE);
        
        button.setText(button.getText() + "\n" + new MachineGun(null).getCost());
        
        float xPosition = 0;
        
        button.setPosition(xPosition, 0);
        
        button.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y) {
            	player.selectBuilding(0);
            }
        });
        
        buttons.addActor(button);
        
        button = new TextButton("Cannon", skin, "default");
        button.setWidth(BUTTON_SIZE);
        button.setHeight(BUTTON_SIZE);
        
        button.setText(button.getText() + "\n" + new Cannon(null).getCost());
        
        xPosition += BUTTON_SIZE + PADDING;
        
        button.setPosition(xPosition, 0);

        button.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y) {
            	player.selectBuilding(1);
            }
        });
        
        buttons.addActor(button);
        
        button = new TextButton("Glue", skin, "default");
        button.setWidth(BUTTON_SIZE);
        button.setHeight(BUTTON_SIZE);
        
        button.setText(button.getText() + "\n" + new Glue(null).getCost());
        
        xPosition += BUTTON_SIZE + PADDING;
        
        button.setPosition(xPosition, 0);

        button.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y) {
            	player.selectBuilding(2);
            }
        });
        
        buttons.addActor(button);
        
        button = new TextButton("Spikes", skin, "default");
        button.setWidth(BUTTON_SIZE);
        button.setHeight(BUTTON_SIZE);

        button.setText(button.getText() + "\n" + new Spikes(null).getCost());
        
        xPosition += BUTTON_SIZE + PADDING;
        
        button.setPosition(xPosition, 0);

        button.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y) {
            	player.selectBuilding(3);
            }
        });
        
        buttons.setWidth(xPosition + BUTTON_SIZE);
        
        buttons.addActor(button);
        
        buttons.setPosition(Gdx.graphics.getWidth() / 2f - buttons.getWidth() / 2f, 5f);
        this.addActor(buttons);
        
        button = new TextButton("Play", skin, "default");
        
        button.setWidth(100f);
        button.setHeight(20f);
        button.setPosition(5, Gdx.graphics.getHeight() - button.getHeight() - 5);

        button.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y) {
            	GameStage stage = (GameStage) player.getStage();
            	stage.getController().startSiege();
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
        int seconds = (int) Math.ceil(stage.getController().getBuildTimer());
        int minutes = (int) Math.floor(seconds / 60);
        seconds -= minutes * 60;
        String time = String.format("%d:%02d", minutes, seconds);
		timer.setText(time);
		
	}
}