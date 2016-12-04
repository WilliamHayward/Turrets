package thisisxanderh.turrets.actors.buildings.turrets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import thisisxanderh.turrets.actors.buildings.Building;
import thisisxanderh.turrets.actors.enemies.Enemy;
import thisisxanderh.turrets.core.Coordinate;
import thisisxanderh.turrets.core.GameActor;
import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.graphics.SpriteList;

public abstract class Turret extends Building {
	protected Texture barrel;
	private float angle = -90;
	
	protected float cooldown;
	private float timer = 0f;
	
	protected Bullet bullet;
	
	public Turret(SpriteList textureID) {
		super(textureID);
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		SpriteBatch spriteBatch = (SpriteBatch) batch;
		
		float width = barrel.getWidth();
		float height = barrel.getHeight();
		
		float y = getY() + getHeight() - barrel.getHeight() / 5;
		spriteBatch.draw(barrel, getX() + getWidth() / 2f - width / 2f, y,
				width / 2f, 0, width, height, 1, 1, angle - 90, 0, 0, 
				(int) width, (int) height, false, false);
				
		super.draw(batch, alpha);
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if (!built) {
			return;
		}
		Enemy target = getNearest();
		if (target != null) {
			float newAngle = getAngle(target.getX(), target.getY()); 
			if (newAngle - 180 > 45 && newAngle - 180 < 135) {
				angle = newAngle;
			} else {
				target = null;
			}
		}
		
		timer -= delta;
		if (timer <= 0 && target != null) {
			timer = cooldown;
			shoot(target);
		}
	}
	
	private void shoot(Enemy target) {
		reload();
		bullet.setPosition(getX() + getWidth() / 2f, getY() + getHeight() / 2f);
		bullet.fire(target);
		this.getStage().addActor(bullet);
	}
	
	protected abstract void reload();
	
	private Enemy getNearest() {
		//TODO: This and some related functions are calibrated for the bottom left of the barrel/enemy. Fix that
		float range = 1000;
		GameStage stage = (GameStage) this.getStage();
		float shortest = Float.MAX_VALUE;
		Enemy closest = null;
		for (Enemy enemy: stage.getEnemies()) {
			float distance = getDistance(enemy);
			if (distance < shortest) {
				shortest = distance;
				closest = enemy;
			}
		}
		if (shortest > range) {
			closest = null;
		}
		return closest;
	}
	
	private float getDistance(GameActor other) {
		float distance = (float) Math.hypot(other.getX()-this.getX(), other.getY()-this.getY());
		return distance;
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
	protected boolean validPosition() {
		return super.validPosition(this.getBounds().getHeight());
	}
}
