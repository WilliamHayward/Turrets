package thisisxanderh.turrets.actors.buildings.turrets;

import thisisxanderh.turrets.actors.enemies.Enemy;
import thisisxanderh.turrets.core.GameActor;
import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.graphics.LayerList;
import thisisxanderh.turrets.graphics.SpriteList;

public abstract class Bullet extends GameActor {
	protected float speed;
	protected float damage;
	public Bullet(SpriteList textureID) {
		super(textureID);
		layer = LayerList.BULLET;
		solid = true;
	}
	
	public void fire(GameActor target) {
		float destinationX = target.getX();
		float destinationY = target.getY();
		float xDiff = destinationX - this.getX();
		float yDiff = destinationY - this.getY();
		
		float xVel = (speed * xDiff);
		float yVel = (speed * yDiff);

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
	public void collided(GameActor other) {
		if (other instanceof Enemy) {
			other.damage(damage);
			this.remove();
		}
	}
	
	protected void hitGround() {
		this.remove();
	}

}