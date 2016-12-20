package thisisxanderh.turrets.entities.players;

import thisisxanderh.turrets.entities.States;
import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class Engineer extends Player {
	public Engineer() {
		super(SpriteList.ENGINEER_STANDING);
		this.addState(States.FLYING, SpriteCache.loadSprite(SpriteList.ENGINEER_SHIP));
		
		speed = 8f;
		highDamage = 3f;
		lowDamage = 1f;
	}
}
