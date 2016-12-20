package thisisxanderh.turrets.entities.buildings.traps;

import thisisxanderh.turrets.entities.players.Player;

public class GlueEffect extends TrapEffect {

	public GlueEffect(Player owner) {
		super(owner);
		speedModifier = 0.5f;
	}

}
