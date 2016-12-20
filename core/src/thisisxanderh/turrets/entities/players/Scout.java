package thisisxanderh.turrets.entities.players;

import thisisxanderh.turrets.entities.States;
import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class Scout extends Player {
	public Scout() {
		super(SpriteList.SCOUT_STANDING);
		this.addState(States.FLYING, SpriteCache.loadSprite(SpriteList.SCOUT_SHIP));
		
		speed = 15f;
		highDamage = 2f;
		lowDamage = 0.5f;
	}
}
