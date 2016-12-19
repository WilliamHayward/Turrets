package thisisxanderh.turrets.entities.players;

import com.badlogic.gdx.graphics.Color;
import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class Soldier extends Player {
	public Soldier() {
		super(SpriteList.SOLDIER_STANDING);
		ship = SpriteCache.loadSprite(SpriteList.SOLDIER_SHIP);
		color = Color.SKY;
		

		speed = 7f;
		highDamage = 8f;
		lowDamage = 4f;
	}
}
