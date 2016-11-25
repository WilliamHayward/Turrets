package thisisxanderh.turrets.graphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SpriteCache {
	private static Map<SpriteList, Texture> sprites = new HashMap<>();
	private SpriteCache() {
		// TODO Auto-generated constructor stub
	}
	
	public static void loadAllSprites() {
		sprites.put(SpriteList.PLAYER_BLUE_STANDING, new Texture(Gdx.files.internal("sprites/player-blue-standing.png")));
	}
	
	public static Texture loadSprite(SpriteList id) {
		return sprites.get(id);
	}

}
