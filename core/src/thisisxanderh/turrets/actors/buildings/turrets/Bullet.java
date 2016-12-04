package thisisxanderh.turrets.actors.buildings.turrets;

import thisisxanderh.turrets.actors.enemies.Enemy;
import thisisxanderh.turrets.core.Coordinate;
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
		float angle = getAngle(destinationX, destinationY) - 180 - 45;
		angle /= 90f;
		float xDiff = destinationX - this.getX();
		float yDiff = destinationY - this.getY();
		
		float xVel = (speed * xDiff);//) * angle;
		float yVel = (speed * yDiff);

		this.setXVelocity(xVel);
		this.setYVelocity(yVel);
	}

	private float getAngle(float x, float y) {
		return getAngle(new Coordinate(x, y));
	}
	
	private float getAngle(Coordinate target) {
	    float angle = (float) Math.toDegrees(Math.atan2(target.getY() - getY(), target.getX() - getX()));

	    if(angle < 0){
	        angle += 360;
	    }

	    return angle;
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
