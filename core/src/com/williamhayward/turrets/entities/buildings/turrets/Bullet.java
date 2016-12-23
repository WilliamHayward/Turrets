package com.williamhayward.turrets.entities.buildings.turrets;

import com.williamhayward.turrets.core.GameStage;
import com.williamhayward.turrets.entities.Entity;
import com.williamhayward.turrets.entities.enemies.Enemy;
import com.williamhayward.turrets.graphics.LayerList;
import com.williamhayward.turrets.graphics.SpriteList;

public abstract class Bullet extends Entity {
	protected float speed;
	protected float damage;
	
	private Turret parent;
	
	public Bullet(Turret parent, SpriteList textureID) {
		super(textureID);
		this.parent = parent;
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
			if (other.dead()) {
				parent.addMoney(((Enemy) other).getBounty());
			}
			this.remove();
		}
	}
	
	protected void hitGround() {
		this.remove();
	}

}
