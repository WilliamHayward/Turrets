package thisisxanderh.turrets.entities.buildings;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.entities.Entity;
import thisisxanderh.turrets.entities.players.Player;
import thisisxanderh.turrets.graphics.LayerList;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.terrain.Terrain;
import thisisxanderh.turrets.terrain.Tile;

public abstract class Building extends Entity {
	protected boolean built = false;
	protected boolean canBuild = false;
	protected String name;
	private Vector2 prevPosition = new Vector2(0,0);
	
	protected int cost;
	
	private Player owner;
	
	public Building(Player owner, SpriteList texture) {
		super(texture);
		
		this.owner = owner;
	}
	
	public int getCost() {
		return cost;
	}
	
	public boolean build() {
		if (owner.getMoney() < getCost() || !canBuild) {
			return false;
		}
		owner.takeMoney(getCost());
		solid = true;
		built = true;
		layer = LayerList.BUILDING;
		return true;
	}

	@Override
	public void draw(Batch batch, float alpha) {
		Color color = batch.getColor();
		if (!built) {
			if (canBuild) {
				color = Color.GREEN;
			} else {
				color = Color.RED;
			}
		}
		
		draw(batch, alpha, color);
		
	}
	
	public void draw(Batch batch, float alpha, Color tint) {
		Color original = batch.getColor();
		batch.setColor(tint);
		super.draw(batch, alpha);
		batch.setColor(original);
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if (!built) {
			// Only check if valid position when conditions change (Collision checks are a bit costly)
			if (prevPosition.x != getX() || prevPosition.y != getY()) {
				canBuild = canBuild();
			}
		} else {
			
		}
		prevPosition.set(getX(), getY());
	}
	
	public Player getOwner() {
		return owner;
	}
	
	protected abstract boolean canBuild();
	
	protected boolean canBuild(Terrain terrain) {	
		if (owner.getMoney() < getCost()) {
			return false;
		}
		
		GameStage stage = (GameStage) this.getStage();
		
		// Don't place it in the ground
		Rectangle bounds = this.getBounds();
		bounds.width -= 2;
		bounds.height -= 2;
		bounds.x += 1;
		bounds.y += 1;
		
		if (!terrain.overlaps(bounds)) {
			return false;
		}
		
		int tileCount = (int) Math.floor(getWidth() / Tile.SIZE);
		bounds.width = Tile.SIZE - 2;
		for (int i = 0; i < tileCount; i++) {
			// Ensure no overhang for longer buildings
			if (!terrain.overlaps(bounds)) {
				return false;
			}
			bounds.x += Tile.SIZE;
		}
		
		// Two buildings can't occupy the same position
		bounds = this.getBounds();
		for (Building building: stage.getActors(Building.class)) {
			if (building.equals(this)) {
				// It'll always be colliding with itself, ya dingus
				continue;
			}
			if (building.getBounds().overlaps(bounds)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void collided(Entity other) {
		// Do nothing
	}
	
	public String getName() {
		return name;
	}
}
