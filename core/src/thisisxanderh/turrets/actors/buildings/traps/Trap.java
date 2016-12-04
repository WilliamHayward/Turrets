package thisisxanderh.turrets.actors.buildings.traps;

import com.badlogic.gdx.math.Rectangle;

import thisisxanderh.turrets.actors.buildings.Building;
import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.terrain.Terrain;

public abstract class Trap extends Building {
	protected TrapEffect effect;
	public Trap(SpriteList textureID) {
		super(textureID);
	}
	
	@Override
	protected boolean validPosition() {
		return super.validPosition(-this.getBounds().getHeight());
	}
	
	public TrapEffect getEffect() {
		if (!built) {
			return new EmptyEffect();
		}
		return effect;
	}
}
