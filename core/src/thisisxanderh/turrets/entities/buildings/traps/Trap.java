package thisisxanderh.turrets.entities.buildings.traps;

import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.entities.buildings.Building;
import thisisxanderh.turrets.entities.players.Player;
import thisisxanderh.turrets.graphics.SpriteList;

public abstract class Trap extends Building {
	protected TrapEffect effect;
	public Trap(Player player, SpriteList textureID) {
		super(player, textureID);
	}
	
	@Override
	protected boolean canBuild() {
		GameStage stage = (GameStage) this.getStage();
		return super.canBuild(stage.getBuildTrap());
	}
	
	public TrapEffect getEffect() {
		if (!built) {
			return new TrapEffect();
		}
		return effect;
	}
}
