package thisisxanderh.turrets.entities.buildings.traps;

import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.entities.buildings.Building;
import thisisxanderh.turrets.graphics.SpriteList;

public abstract class Trap extends Building {
	protected TrapEffect effect;
	public Trap(SpriteList textureID) {
		super(textureID);
	}
	
	@Override
	protected boolean validPosition() {
		GameStage stage = (GameStage) this.getStage();
		return super.validPosition(stage.getBuildTrap());
	}
	
	public TrapEffect getEffect() {
		if (!built) {
			return new EmptyEffect();
		}
		return effect;
	}
}
