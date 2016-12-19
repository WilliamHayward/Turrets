package thisisxanderh.turrets.entities.players;

import com.badlogic.gdx.graphics.Color;
import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class Scout extends Player {
	public Scout() {
		super(SpriteList.SCOUT_STANDING);
		ship = SpriteCache.loadSprite(SpriteList.SCOUT_SHIP);
		color = Color.SKY;

		speed = 15f;
		highDamage = 2f;
		lowDamage = 0.5f;
	}
}
