package thisisxanderh.turrets.actors.buildings.traps;

import thisisxanderh.turrets.actors.buildings.Building;
import thisisxanderh.turrets.graphics.SpriteList;

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
