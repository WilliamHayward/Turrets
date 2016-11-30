package thisisxanderh.turrets.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import thisisxanderh.turrets.actors.enemies.Enemy;
import thisisxanderh.turrets.core.Coordinate;
import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.core.Line;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.input.DeviceList;
import thisisxanderh.turrets.input.InputManager;
import thisisxanderh.turrets.terrain.Tile;

public class Player extends GameActor {
	private float speed = 10.5f;
	private static final float GRAVITY = -25f;
	private InputManager input;
	private boolean facingLeft = false;

	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	
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
		
		
		spriteBatch.end();
		shapeRenderer.setProjectionMatrix(this.getStage().getCamera().combined);
		Camera camera = this.getStage().getCamera();
		Vector3 unprojected = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		float x = unprojected.x;
		float y = unprojected.y;
		Line line = new Line(x, y, getX() + getWidth() / 2, getY() + getHeight() / 2);
		GameStage stage = (GameStage) this.getStage();
		
		shapeRenderer.begin(ShapeType.Line);
		if (line.intersects(stage.getTerrain())) {
			shapeRenderer.setColor(1, 0, 0, 1);
		} else {
			shapeRenderer.setColor(0, 1, 0, 1);
		}
		shapeRenderer.line(getX() + getWidth() / 2, getY() + getHeight() / 2, x, y);
		shapeRenderer.end();
		spriteBatch.begin();
	}
	@Override
	public void act(float delta) {
		if (!this.inWorld()) {
			spawn();
		}
		handleInput();
		super.act(delta);
		GameStage stage = (GameStage) this.getStage();
		Rectangle bounds = this.getBounds();
        if (stage.getTerrain().overlaps(bounds)) {
        	this.moveToContact();
        } else {
        }
        
		this.addYVelocity(GRAVITY * delta);
		
		stage.getViewport().getCamera().position.x = this.getX();
		stage.getViewport().getCamera().position.y = this.getY();
	}
	
	private void handleInput() {
		float horizontalSpeed = input.getHorizontal();
		this.setXVelocity(horizontalSpeed * speed);
		
		if (input.getJump()) {
			this.setYVelocity(13);
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
					other.die(this);
					this.setYVelocity(13);
				}
			}
		}
	}
	
}
