package thisisxanderh.turrets.actors.players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;

import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class Scout extends Player {
	public Scout(OrthographicCamera camera) {
		super(camera, SpriteList.SCOUT_STANDING);
		ship = SpriteCache.loadSprite(SpriteList.SCOUT_SHIP);
		color = Color.SKY;
		

		speed = 15f;
		highDamage = 2f;
		lowDamage = 0.5f;
	}
}
