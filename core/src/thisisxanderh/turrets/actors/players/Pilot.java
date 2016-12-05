package thisisxanderh.turrets.actors.players;

import com.badlogic.gdx.graphics.Color;
import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class Pilot extends Player {
	public Pilot() {
		super(SpriteList.PILOT_STANDING);
		ship = SpriteCache.loadSprite(SpriteList.PILOT_SHIP);
		color = Color.SKY;
		

		speed = 8f;
		highDamage = 3f;
		lowDamage = 1f;
	}
}
