package thisisxanderh.turrets.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class GameActor extends Actor {
	protected float xVelocity = 0;
	protected float yVelocity = 0;
	protected Texture texture;
	
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
		this.setPosition(this.getX() + this.getXVelocity(),
				this.getY() + this.getYVelocity());
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
		/*
		Rectangle newPosition;
		float posHeight = this.getHeight();
		float posWidth = this.getWidth();
		float posX = this.getX();
		float posY = this.getY();
		float newX = posX;
		float newY = posY;
		float newXVelocity = this.getXVelocity();
		float newYVelocity = this.getYVelocity();
		newPosition = new Rectangle(posX, posY, posWidth, posHeight);
		if (this.getXVelocity() > 0) {
			newX = other.getX() - this.getWidth();
		} else if (this.getXVelocity() < 0) {
			newX = other.getX() + other.getWidth();
		}
		
		newPosition.setX(newX);
		if (newPosition.overlaps(other)) {
			newX = posX;
		} else {
			newXVelocity = 0;
		}
		this.setPosition(newX, newY);
		this.setXVelocity(newXVelocity);
		this.setYVelocity(newYVelocity);
		for (int i = 0; i < 2; i++) {
			return;
		}
		
		newPosition = new Rectangle(posX, posY, posWidth, posHeight);
		if (this.getYVelocity() > 0) {
			newY = other.getY() - other.getHeight();
			
		} else if (this.getYVelocity() < 0){
			newY = other.getY() + other.getHeight();
		}
		newPosition.setY(newY);
		if (newPosition.overlaps(other)) {
			newY = posY;
		} else {
			newYVelocity = 0;
		}
		this.setPosition(newX, newY);
		this.setXVelocity(newXVelocity);
		this.setYVelocity(newYVelocity);*/
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
