package thisisxanderh.turrets.entities.enemies;

import thisisxanderh.turrets.core.commands.Commander;
import thisisxanderh.turrets.graphics.SpriteList;

public class GiantSpider extends Enemy {

	public GiantSpider(Commander parent) {
		super(SpriteList.SPIDER, parent);
		this.setScale(2f);
		maxHealth = 4;
		health = maxHealth;
		speed = 10f;
		bounty = 10;
	}

}
