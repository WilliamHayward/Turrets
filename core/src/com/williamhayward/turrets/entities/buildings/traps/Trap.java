package com.williamhayward.turrets.entities.buildings.traps;

import com.williamhayward.turrets.core.GameStage;
import com.williamhayward.turrets.entities.buildings.Building;
import com.williamhayward.turrets.entities.players.Player;
import com.williamhayward.turrets.graphics.SpriteList;

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
