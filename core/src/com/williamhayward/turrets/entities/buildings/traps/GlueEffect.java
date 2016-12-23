package com.williamhayward.turrets.entities.buildings.traps;

import com.williamhayward.turrets.entities.players.Player;

public class GlueEffect extends TrapEffect {

	public GlueEffect(Player owner) {
		super(owner);
		speedModifier = 0.5f;
	}

}
