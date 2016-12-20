package thisisxanderh.turrets.entities.players;

import thisisxanderh.turrets.entities.States;
import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class Soldier extends Player {
	public Soldier() {
		super(SpriteList.SOLDIER_STANDING);
		this.addState(States.FLYING, SpriteCache.loadSprite(SpriteList.HERO_SHIP));

		speed = 7f;
		highDamage = 8f;
		lowDamage = 4f;
	}
}
