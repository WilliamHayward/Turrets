package thisisxanderh.turrets.actors.players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;

import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class Hero extends Player {

	public Hero(OrthographicCamera camera) {
		super(camera, SpriteList.HERO_STANDING);
		ship = SpriteCache.loadSprite(SpriteList.HERO_SHIP);
		color = Color.SKY;
		
		speed = 10f;
		highDamage = 5f;
		lowDamage = 2f;
	}

}
