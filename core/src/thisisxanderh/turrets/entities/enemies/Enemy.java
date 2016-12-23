package thisisxanderh.turrets.entities.enemies;


import java.util.List;

import com.badlogic.gdx.math.Vector2;

import thisisxanderh.turrets.core.commands.Commander;
import thisisxanderh.turrets.entities.Entity;
import thisisxanderh.turrets.entities.buildings.traps.Trap;
import thisisxanderh.turrets.entities.buildings.traps.TrapEffect;
import thisisxanderh.turrets.entities.players.Player;
import thisisxanderh.turrets.graphics.LayerList;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.terrain.Tile;

public abstract class Enemy extends Entity {
	protected List<Vector2> path;
	protected int destination = 0;
	protected int direction = 1; // 1 for moving forward, -1 for moving back
	protected float speed;
	protected Commander parent;
	protected TrapEffect effects = new TrapEffect();
	
	protected int bounty = 0;
	
	public Enemy(SpriteList textureID, Commander parent) {
		super(textureID);
		this.setX(0);
		this.setY(0);
		this.parent = parent;
		this.path = parent.getPath();
		layer = LayerList.ENEMY;
		solid = true;
	}
	
	protected void spawn() {
		this.setX(path.get(0).x * Tile.SIZE);
		this.setY(path.get(0).y * Tile.SIZE);
	}
	
	@Override
	public void act(float delta) {
		float destinationX = path.get(destination).x * Tile.SIZE;
		float destinationY = path.get(destination).y * Tile.SIZE;
		float xDiff = destinationX - this.getX();
		float yDiff = destinationY - this.getY();
		
		float xVel = 0;
		float yVel = 0;
		
		float speed = this.speed * effects.getSpeedModifier();
		
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
		
		if (Math.abs(xVel) < speed && Math.abs(yVel) < speed) {
			destination += direction;
			if (destination < 0 || path.get(destination) == null) {
				direction *= -1;
				this.setScaleX(-getScaleX());
				destination += direction;
			}
		}
		this.setXVelocity(xVel);
		this.setYVelocity(yVel);
		
		
		super.act(delta);
		this.damage(effects.getDPS() * delta);
		if (dead()) {
			for (Player player: effects.getOwners()) {
				player.addMoney(getBounty());
			}
		}
		effects = new TrapEffect();
	}
	
	@Override
	public void die() {
		super.die();
		parent.childDeath(this);
	}
	
	@Override
	public void collided(Entity other) {
		if (other instanceof Trap) {
			Trap trap = (Trap) other;
			effects.combine(trap.getEffect());
		}
	}
	
	@Override
	public void bop(float damage) {
		this.damage(damage);
	}

	public int getBounty() {
		return bounty;
	}
}
