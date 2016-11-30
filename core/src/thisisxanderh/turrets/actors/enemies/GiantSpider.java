package thisisxanderh.turrets.actors.enemies;

import thisisxanderh.turrets.graphics.SpriteList;

public class GiantSpider extends Enemy {

	public GiantSpider(Emitter parent) {
		super(SpriteList.SPIDER_GIANT, parent);
		maxHealth = 4;
		health = maxHealth;
	}

}
