package thisisxanderh.turrets.actors.buildings.traps;

import thisisxanderh.turrets.actors.buildings.Building;
import thisisxanderh.turrets.core.GameStage;
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
