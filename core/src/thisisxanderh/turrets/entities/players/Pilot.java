package thisisxanderh.turrets.entities.players;

import thisisxanderh.turrets.entities.States;
import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class Pilot extends Player {
	public Pilot() {
		super(SpriteList.PILOT_STANDING);

		this.addState(States.FLYING, SpriteCache.loadSprite(SpriteList.PILOT_SHIP));
		

		speed = 8f;
		highDamage = 3f;
		lowDamage = 1f;
	}
}
