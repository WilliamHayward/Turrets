package thisisxanderh.turrets.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import thisisxanderh.turrets.core.Coordinate;
import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.input.DeviceList;
import thisisxanderh.turrets.input.InputManager;
import thisisxanderh.turrets.terrain.Tile;

public class Player extends GameActor {
	private float speed = 10.5f;
	private static final float GRAVITY = -9.8f;
	private InputManager input;
	private boolean facingLeft = false;
	
	public Player() {
		super(SpriteList.PLAYER_BLUE_STANDING);
		input = new InputManager();
	}
	
	public void spawn() {
		GameStage stage = (GameStage) this.getStage();
		Coordinate spawn = stage.getSpawn();
		this.setX(spawn.getX() * Tile.SIZE);
		this.setY(spawn.getY() * Tile.SIZE);
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		SpriteBatch spriteBatch = (SpriteBatch) batch;
		spriteBatch.draw(texture, getX(), getY(), 0, 0, getWidth(), getHeight(),
				1, 1, getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), facingLeft, false);
	}
	@Override
	public void act(float delta) {
		handleInput();
		super.act(delta);
		this.addYVelocity(GRAVITY * delta);
		GameStage stage = (GameStage) this.getStage();
		Rectangle bounds = this.getBounds();
        if (stage.getTerrain().overlaps(bounds)) {
        	this.moveToContact();
        } else {
        }
		
		stage.getViewport().getCamera().position.x = this.getX();
		stage.getViewport().getCamera().position.y = this.getY();
	}
	
	private void handleInput() {
		float horizontalSpeed = input.getHorizontal();
		this.setXVelocity(horizontalSpeed * speed);
		
		if (input.getJump()) {
			this.setYVelocity(10);
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
			if (input.getDevice() == DeviceList.KEYBOARD) {
				input.setController(Controllers.getControllers().first());
			} else {
				input.setKeyboard();
			}
		}
		facingLeft = input.getFacing(facingLeft);
	}
}
