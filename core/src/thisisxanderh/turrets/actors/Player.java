package thisisxanderh.turrets.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

import thisisxanderh.turrets.actors.enemies.Enemy;
import thisisxanderh.turrets.core.Coordinate;
import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.graphics.LayerList;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.input.DeviceList;
import thisisxanderh.turrets.input.InputManager;
import thisisxanderh.turrets.terrain.Tile;

public class Player extends GameActor {
	private float speed = 10.5f;
	private static final float GRAVITY = -25f;
	private InputManager input;
	private boolean facingLeft = false;
	private boolean doubleJumpAvailable = true;
	private boolean groundPound = false;
	private float stunnedTimer = 0f;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	
	public Player(OrthographicCamera camera) {
		super(SpriteList.PLAYER_BLUE_STANDING);
		this.camera = camera;
		input = new InputManager(camera);
		layer = LayerList.PLAYER;
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
		
		
		spriteBatch.end();
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		shapeRenderer.begin(ShapeType.Filled);
		Coordinate cursor = input.getCursorPosition();
		shapeRenderer.circle(cursor.getX(), cursor.getY(), 30);
		shapeRenderer.end();
		spriteBatch.begin();
	}
	@Override
	public void act(float delta) {
		if (!this.inWorld()) {
			spawn();
		}
		
		stunnedTimer -= delta;
		if (stunnedTimer < 0 && !groundPound) {
			handleInput();
		}
		
		super.act(delta);
		GameStage stage = (GameStage) this.getStage();
		Rectangle bounds = this.getBounds();
    	onGround = false;
        if (stage.getTerrain().overlaps(bounds)) {
        	this.moveToContact();
        } else {
        }
        
        if (onGround) {
        	if (groundPound) {
        		stunnedTimer = 0.5f;
        		setYVelocity(4f);
        	}
        	doubleJumpAvailable = true;
        	groundPound = false;
        }

		for (GameActor other: stage.getGameActors()) {
			if (!other.collides()) {
				continue;
			}
			if (!this.equals(other)) {
				if (bounds.overlaps(other.getBounds())) {
					this.collided(other);
					other.collided(this);
				}
			}
		}
        
		this.addYVelocity(GRAVITY * delta);
		
		
		
		stage.getViewport().getCamera().position.x = this.getX();
		stage.getViewport().getCamera().position.y = this.getY();
	}
	
	private void handleInput() {
		input.update();
		float horizontalSpeed = input.getHorizontal();
		this.setXVelocity(horizontalSpeed * speed);
		
		if (onGround) {
			if (input.getJump()) {
				this.setYVelocity(13);
			}
		} else {
			if (doubleJumpAvailable) {
				if (input.getJump()) {
					this.setYVelocity(13);
					doubleJumpAvailable = false;
				}
			}
			if (input.getPound()) {
				this.setYVelocity(-20);
				this.setXVelocity(0);
				doubleJumpAvailable = false;
				groundPound = true;
			}
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
	
	@Override
	public void collided(GameActor other) {
		Rectangle bounds = getBounds();
		if (other instanceof Enemy) {
			if (this.getYVelocity() < 0) {
				if (bounds.getY() - this.getYVelocity() > other.getY() + other.getHeight()) {
					float damage = groundPound ? 5f : 2f;
					other.damage(damage, this);
					this.setYVelocity(13);
					groundPound = false;
				}
			}
		}
	}
	
}
