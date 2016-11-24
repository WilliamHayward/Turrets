package thisisxanderh.turrets.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

import thisisxanderh.turrets.core.GameStage;

public class Player extends GameActor {
	Texture texture;
	private float speed = 10.5f;
	private static final float GRAVITY = -9.8f;
	public Player() {
		texture = new Texture(Gdx.files.internal("sprites/player-blue-standing.png"));//TODO: Make this flyweight
		this.setHeight(texture.getHeight());
		this.setWidth(texture.getWidth());
		
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(texture, this.getX(), this.getY());
	}
	
	@Override
	public void act(float delta) {
		handleInput();
		super.act(delta);
		this.addYVelocity(GRAVITY * delta);
		GameStage stage = (GameStage) this.getStage();
		Rectangle bounds = this.getBounds();
        if (stage.getTerrain().overlaps(this.getBounds())) {
        	this.moveToContact();
        } else {
        }
		
		stage.getViewport().getCamera().position.x = this.getX();
		stage.getViewport().getCamera().position.y = this.getY();
	}
	
	private void handleInput() {
		float moveLeft = Gdx.input.isKeyPressed(Input.Keys.A) ? 1 : 0;
		float moveRight = Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : 0;
		float horizontalSpeed = moveRight - moveLeft;
		this.setXVelocity(horizontalSpeed * speed);
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			this.setYVelocity(5);
		}
	}
}
