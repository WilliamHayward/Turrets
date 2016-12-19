package thisisxanderh.turrets.entities.buildings.turrets;

import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.entities.Entity;
import thisisxanderh.turrets.entities.enemies.Enemy;
import thisisxanderh.turrets.graphics.LayerList;
import thisisxanderh.turrets.graphics.SpriteList;

public abstract class Bullet extends Entity {
	protected float speed;
	protected float damage;
	public Bullet(SpriteList textureID) {
		super(textureID);
		layer = LayerList.BULLET;
		solid = true;
	}
	
	public void fire(Entity target) {
		float destinationX = target.getX();
		float destinationY = target.getY();
		float xDiff = destinationX - this.getX();
		float yDiff = destinationY - this.getY();
		
		float xVel = speed * xDiff;
		float yVel = speed * yDiff;

		this.setXVelocity(xVel);
		this.setYVelocity(yVel);
	}	
	
	@Override
	public void act(float delta) {
		GameStage stage = (GameStage) this.getStage();
		if (stage.getTerrain().overlaps(this.getBounds())) {
			hitGround();
			
		}
		super.act(delta);
	}
	
	@Override
	public void collided(Entity other) {
		if (other instanceof Enemy) {
			other.damage(damage);
			this.remove();
		}
	}
	
	protected void hitGround() {
		this.remove();
	}

}
