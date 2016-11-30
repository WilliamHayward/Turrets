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
		sprites.put(SpriteList.PLAYER_BLUE_STANDING, new Texture(Gdx.files.internal("sprites/player-blue-standing.png")));
		sprites.put(SpriteList.PLAYER_BLUE_WALKING, new Texture(Gdx.files.internal("sprites/player-blue-walking.png")));
		sprites.put(SpriteList.SPIDER, new Texture(Gdx.files.internal("sprites/spider_small.png")));
		sprites.put(SpriteList.SPIDER_GIANT, new Texture(Gdx.files.internal("sprites/spider_large.png")));
	}
	
	public static Texture loadSprite(SpriteList id) {
		return sprites.get(id);
	}

}
