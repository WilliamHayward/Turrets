package thisisxanderh.turrets.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.graphics.LayerList;
import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.terrain.Tile;

public abstract class Entity extends Actor {
	protected Vector2 velocity = new Vector2(0, 0);
	protected Texture texture = SpriteCache.loadSprite(SpriteList.PLACEHOLDER);
	protected float health;
	protected float maxHealth;
	protected boolean onGround;
	
	protected boolean solid = false;
	
	protected LayerList layer = LayerList.DEFAULT;
	
	public static final float MAX_SPEED = Tile.SIZE;
	
	public Entity() {
		this.setWidth(Tile.SIZE);
		this.setHeight(Tile.SIZE);
	}
	
	public Entity(Texture texture) {
		this.texture = texture;
		this.setHeight(texture.getHeight());
		this.setWidth(texture.getWidth());
	}
	
	public Entity(SpriteList textureID) {
		this(SpriteCache.loadSprite(textureID));
	}
	
	public boolean collides() {
		return solid;
	}

	@Override
	public void draw(Batch batch, float alpha) {
		SpriteBatch spriteBatch = (SpriteBatch) batch;
		spriteBatch.draw(texture, getX(), getY(), 0, 0, getWidth(), getHeight(),
				1, 1, getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), false, false);
	}
	
	public LayerList getLayer() {
		return layer;
	}
	
	public Rectangle getBounds() {
		float x = this.getX();
		float y = this.getY();
		float width = this.getWidth();
		float height = this.getHeight();
		Rectangle rectangle = new Rectangle(x, y, width, height);
		return rectangle;
	}
	
	public void addVelocity(Vector2 other) {
		velocity.add(other);
	}
	
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public void addXVelocity(float xVelocity) {
		velocity.x += xVelocity;
	}
	
	public void setXVelocity(float xVelocity) {
		velocity.x = xVelocity;
	}

	public float getXVelocity() {
		return velocity.x;
	}
	
	public void addYVelocity(float yVelocity) {
		velocity.y += yVelocity;
	}
	
	public void setYVelocity(float yVelocity) {
		velocity.y = yVelocity;
	}
	
	public float getYVelocity() {
		return velocity.y;
	}
	
	@Override
	public void act(float delta) {
		float xVelocityStep = MathUtils.clamp(velocity.x * delta, -MAX_SPEED, MAX_SPEED * delta);
		float yVelocityStep = MathUtils.clamp(velocity.y * delta, -MAX_SPEED * delta, MAX_SPEED * delta);
		this.setPosition(getX() + xVelocityStep / delta, getY() + yVelocityStep / delta);
	}
	
	/**
	 * Move to touch the terrain as closely as possible without overlapping
	 */
	public void moveToContact() {
		GameStage stage = (GameStage) this.getStage();
		Rectangle bounds;
		Rectangle other;
		bounds = this.getBounds();
		
		// The x and y shrinkages are to ensure that only one axis is tested at a time
		// This is achieved by discarding irrelevant velocity
		float yShrinkage = this.getYVelocity();
		bounds.setHeight(bounds.getHeight() - Math.abs(yShrinkage));
		bounds.setY(bounds.getY() - yShrinkage);
		other = stage.getTerrain().getOverlap(bounds);

		if (other != null) {
			moveToContactHorizontal(bounds, other);
		}
		
		bounds = this.getBounds();
		float xShrinkage = this.getXVelocity();
		bounds.setWidth(bounds.getWidth() - Math.abs(xShrinkage));
		bounds.setX(bounds.getX() - xShrinkage);
		other = stage.getTerrain().getOverlap(bounds);

		if (other != null) {
			moveToContactVertical(bounds, other);
		}
	}

	private void moveToContactHorizontal(Rectangle bounds, Rectangle other) {
		if (this.getXVelocity() > 0) {
			bounds.setX(other.getX() - this.getWidth());
		} else {
			bounds.setX(other.getX() + other.getWidth());
		}
		this.setX(bounds.getX());
		this.setXVelocity(0);
	}
	
	private void moveToContactVertical(Rectangle bounds, Rectangle other) {
		if (this.getYVelocity() > 0) {
			bounds.setY(other.getY() - this.getHeight());
		} else {
			bounds.setY(other.getY() + other.getHeight());
			onGround = true;
		}
		this.setY(bounds.getY());
		this.setYVelocity(0);
	}
	
	public boolean inWorld() {
		return (this.getX() > 0 && this.getX() < GameStage.SIZE * Tile.SIZE &&
				this.getY() > 0 && this.getY() < GameStage.SIZE * Tile.SIZE);
	}
	
	public abstract void collided(Entity other);
	
	/**
	 * @return true if killed
	 */
	public boolean damage(float damage) {
		health -= damage;
		if (health <= 0) {
			die();
			return true;
		}
		return false;
	}

	/**
	 * @return true if killed
	 */
	public boolean damage(float damage, Entity cause) {
		health -= damage;
		if (health <= 0) {
			die(cause);
			return true;
		}
		return false;
	}
	
	public void die() {
		GameStage stage = (GameStage) this.getStage();
		stage.kill(this);
	}
	
	public void die(Entity cause) {
		die();
	}
}
