package com.williamhayward.turrets.graphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class SpriteCache {
	private static Map<SpriteList, Sprite> sprites = new HashMap<>();
	private SpriteCache() {
	}
	
	public static void loadAllSprites() {
		TextureAtlas atlas;
		atlas = new TextureAtlas(Gdx.files.internal("packed/sprites.atlas"));
		
		for (SpriteList sprite: SpriteList.values()) {
			String name = sprite.toString().toLowerCase();
			Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions(name);
			if (frames.size == 0) {
				Gdx.app.error("URGENT", name + " has no frames");
			}
			sprites.put(sprite, new Sprite(0.5f, frames));
		}
		
		// Any permanent custom tinkerings can go here easily enough. Frame durations and such.
	}
	
	public static Sprite loadSprite(SpriteList id) {
		return new Sprite(sprites.get(id));
	}

}
