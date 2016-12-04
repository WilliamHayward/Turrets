package thisisxanderh.turrets.actors.players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;

import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class Engineer extends Player {
	public Engineer(OrthographicCamera camera) {
		super(camera, SpriteList.ENGINEER_STANDING);
		ship = SpriteCache.loadSprite(SpriteList.ENGINEER_SHIP);
		color = Color.SKY;
		

		speed = 8f;
		highDamage = 3f;
		lowDamage = 1f;
	}
}
