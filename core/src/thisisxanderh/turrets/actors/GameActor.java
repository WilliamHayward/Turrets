package thisisxanderh.turrets.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class GameActor extends Actor {
	protected float xVelocity = 0;
	protected float yVelocity = 0;
	protected Texture texture;
	
	public static final float MAX_SPEED = 50;
	
	public GameActor(Texture texture) {
		this.texture = texture;
		this.setHeight(texture.getHeight());
		this.setWidth(texture.getWidth());
	}
	
	public GameActor(SpriteList textureID) {
		texture = SpriteCache.loadSprite(textureID);
		this.setHeight(texture.getHeight());
		this.setWidth(texture.getWidth());
	}
	
	public Rectangle getBounds() {
		float x = this.getX();
		float y = this.getY();
		float width = this.getWidth();
		float height = this.getHeight();
		Rectangle rectangle = new Rectangle(x, y, width, height);
		return rectangle;
	}
	
	public void addXVelocity(float xVelocity) {
		this.xVelocity += xVelocity;
	}
	
	public void setXVelocity(float xVelocity) {
		this.xVelocity = xVelocity;
	}

	public float getXVelocity() {
		return xVelocity;
	}
	
	public void addYVelocity(float yVelocity) {
		this.yVelocity += yVelocity;
	}
	
	public void setYVelocity(float yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	public float getYVelocity() {
		return yVelocity;
	}
	
	@Override
	public void act(float delta) {
		xVelocity = MathUtils.clamp(xVelocity, -MAX_SPEED, MAX_SPEED);
		yVelocity = MathUtils.clamp(yVelocity, -MAX_SPEED, MAX_SPEED);
		this.setPosition(getX() + xVelocity, getY() + yVelocity);
	}
	
	public void moveToContact() {
		GameStage stage = (GameStage) this.getStage();
		Rectangle bounds;
		Rectangle other;
		bounds = this.getBounds();
		bounds.setHeight(bounds.getHeight() / 2);
		bounds.setY(bounds.getY() + bounds.getHeight() / 2);
		other = stage.getTerrain().getOverlap(bounds);

		if (other != null) {
			moveToContactHorizontal(bounds, other);
		}
		
		bounds = this.getBounds();
		bounds.setWidth(bounds.getWidth() / 2);
		bounds.setX(bounds.getX() + bounds.getWidth() / 2);
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
		}
		this.setY(bounds.getY());
		this.setYVelocity(0);
	}
}
