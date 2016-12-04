package thisisxanderh.turrets.actors.players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;

import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class Soldier extends Player {
	public Soldier(OrthographicCamera camera) {
		super(camera, SpriteList.SOLDIER_STANDING);
		ship = SpriteCache.loadSprite(SpriteList.SOLDIER_SHIP);
		color = Color.SKY;
		

		speed = 7f;
		highDamage = 8f;
		lowDamage = 4f;
	}
}
