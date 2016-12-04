package thisisxanderh.turrets.graphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SpriteCache {
	private static Map<SpriteList, Texture> sprites = new HashMap<>();
	private SpriteCache() {
	}
	
	public static void loadAllSprites() {
		sprites.put(SpriteList.PLACEHOLDER, new Texture(Gdx.files.internal("PLACEHOLDER.png")));
		
		sprites.put(SpriteList.HERO_STANDING, new Texture(Gdx.files.internal("sprites/hero_standing.png")));
		sprites.put(SpriteList.HERO_SHIP, new Texture(Gdx.files.internal("sprites/hero_ship.png")));
		
		sprites.put(SpriteList.PILOT_STANDING, new Texture(Gdx.files.internal("sprites/pilot_standing.png")));
		sprites.put(SpriteList.PILOT_SHIP, new Texture(Gdx.files.internal("sprites/pilot_ship.png")));
		
		sprites.put(SpriteList.ENGINEER_STANDING, new Texture(Gdx.files.internal("sprites/engineer_standing.png")));
		sprites.put(SpriteList.ENGINEER_SHIP, new Texture(Gdx.files.internal("sprites/engineer_ship.png")));
		
		sprites.put(SpriteList.SOLDIER_STANDING, new Texture(Gdx.files.internal("sprites/soldier_standing.png")));
		sprites.put(SpriteList.SOLDIER_SHIP, new Texture(Gdx.files.internal("sprites/soldier_ship.png")));
		
		sprites.put(SpriteList.SCOUT_STANDING, new Texture(Gdx.files.internal("sprites/scout_standing.png")));
		sprites.put(SpriteList.SCOUT_SHIP, new Texture(Gdx.files.internal("sprites/scout_ship.png")));
		
		sprites.put(SpriteList.SPIDER, new Texture(Gdx.files.internal("sprites/spider_small.png")));
		sprites.put(SpriteList.SPIDER_GIANT, new Texture(Gdx.files.internal("sprites/spider_large.png")));

		sprites.put(SpriteList.BASE_MACHINE_GUN, new Texture(Gdx.files.internal("sprites/base_machine_gun.png")));
		sprites.put(SpriteList.BASE_CANNON, new Texture(Gdx.files.internal("sprites/base_cannon.png")));

		sprites.put(SpriteList.GLUE, new Texture(Gdx.files.internal("sprites/glue.png")));
		sprites.put(SpriteList.SPIKES, new Texture(Gdx.files.internal("sprites/spikes.png")));
	}
	
	public static Texture loadSprite(SpriteList id) {
		return sprites.get(id);
	}

}
