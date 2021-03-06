package com.williamhayward.turrets;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.williamhayward.turrets.core.GameController;
import com.williamhayward.turrets.graphics.SpriteCache;

public class Turrets extends ApplicationAdapter {
	
	private GameController controller;
	
	@Override
	public void create () {
		SpriteCache.loadAllSprites();
		controller = new GameController();
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float delta = Gdx.graphics.getDeltaTime();
		controller.tick(delta);
		
		controller.draw();
	}
	
	@Override
	public void dispose () {
		controller.dispose();
	}

	@Override
	public void resize (int width, int height) {
		controller.resize(width, height);
	}
}
