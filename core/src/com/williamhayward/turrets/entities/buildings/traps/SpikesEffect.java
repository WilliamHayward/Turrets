package com.williamhayward.turrets.entities.buildings.traps;

import com.williamhayward.turrets.entities.players.Player;

public class SpikesEffect extends TrapEffect {

	public SpikesEffect(Player owner) {
		super(owner);
		dps = 2.5f;
	}

}
