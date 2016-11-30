package thisisxanderh.turrets.actors.enemies;


import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import thisisxanderh.turrets.actors.GameActor;
import thisisxanderh.turrets.core.Coordinate;
import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.terrain.Tile;

public abstract class Enemy extends GameActor {
	protected List<Coordinate> path;
	protected int destination = 0;
	protected int direction = 1; // 1 for moving forward, -1 for moving back
	protected float speed = 10f;
	protected Emitter parent;
	public Enemy(SpriteList textureID, Emitter parent) {
		super(textureID);
		this.setX(0);
		this.setY(0);
		this.parent = parent;
		this.path = parent.getPath();
		spawn();
	}
	
	private void spawn() {
		this.setX(path.get(0).getX() * Tile.SIZE);
		this.setY(path.get(0).getY() * Tile.SIZE);
	}
	
	@Override
	public void act(float delta) {
		float destinationX = path.get(destination).getX() * Tile.SIZE;
		float destinationY = path.get(destination).getY() * Tile.SIZE;
		float xDiff = destinationX - this.getX();
		float yDiff = destinationY - this.getY();
		
		float xVel = 0;
		float yVel = 0;
		
		if (Math.abs(xDiff) <= speed * 2) {
			this.setX(destinationX);
		} else {
			xVel = (speed * Math.signum(xDiff));
		}
		
		if (Math.abs(yDiff) <= speed * 2) {
			this.setY(destinationY);
		} else {
			yVel = (speed * Math.signum(yDiff));
		}
		
		if (xVel == 0 && yVel == 0) {
			destination += direction;
			if (destination < 0 || path.get(destination) == null) {
				direction *= -1;
				destination += direction;
			}
		}
		this.setXVelocity(xVel);
		this.setYVelocity(yVel);
		
		
		super.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		SpriteBatch spriteBatch = (SpriteBatch) batch;
		spriteBatch.draw(texture, getX(), getY(), 0, 0, getWidth(), getHeight(),
				1, 1, getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), direction == -1, false);
	}
	
	@Override
	public void die() {
		super.die();
		parent.childDeath(this);
	}
	
}
