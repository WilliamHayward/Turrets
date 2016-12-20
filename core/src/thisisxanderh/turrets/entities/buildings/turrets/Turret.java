package thisisxanderh.turrets.entities.buildings.turrets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.entities.Entity;
import thisisxanderh.turrets.entities.buildings.Building;
import thisisxanderh.turrets.entities.enemies.Enemy;
import thisisxanderh.turrets.graphics.Sprite;
import thisisxanderh.turrets.graphics.SpriteList;

public abstract class Turret extends Building {
	protected Sprite barrel;
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
		
		
		
		Color original = spriteBatch.getColor();
		
		if (!built) {
			if (this.validPosition()) {
				spriteBatch.setColor(Color.GREEN);
			} else {
				spriteBatch.setColor(Color.RED);
			}

		}
		
		TextureRegion barrel = this.barrel.getFrame();
		
		float width = barrel.getRegionWidth();
		float height = barrel.getRegionHeight();
		
		float y = getY() + getHeight() - height / 2f;
		float x = getX() + getWidth() / 2f - width / 2f;
		spriteBatch.draw(barrel, x, y, width / 2f, 0, 
				width, height, getScaleX(), getScaleY(), angle - 90);
		spriteBatch.setColor(original);
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
			Vector2 position = target.getCentre();
			position.add(target.getVelocity());
			float newAngle = getAngle(position.x, position.y); 
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
		for (Enemy enemy: stage.getActors(Enemy.class)) {
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
	
	private float getDistance(Entity other) {
		float distance = (float) Math.hypot(other.getX()-this.getX(), other.getY()-this.getY());
		return distance;
	}
	
	private float getAngle(float x, float y) {
		return getAngle(new Vector2(x, y));
	}
	
	private float getAngle(Vector2 target) {
		TextureRegion barrel = this.barrel.getFrame();
		float width = barrel.getRegionWidth();
		float height = barrel.getRegionHeight();
		
		float y = getY() + getHeight() - height / 2f;
		float x = getX() + getWidth() / 2f + width / 2f;
	    float angle = (float) Math.toDegrees(Math.atan2(target.y - y, target.x - x));

	    if(angle < 0){
	        angle += 360;
	    }

	    return angle;
	}
	
	
	
	@Override
	protected boolean validPosition() {
		GameStage stage = (GameStage) this.getStage();
		return super.validPosition(stage.getBuildTurret());
	}
}
