package com.williamhayward.turrets.core.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.williamhayward.turrets.core.GameController;
import com.williamhayward.turrets.core.GameStage;
import com.williamhayward.turrets.entities.enemies.Spawner;

public class DisplayCommand extends Command {
	private String message;
	public DisplayCommand(Spawner parent, String string) {
		super(parent);
		this.message = string;
	}

	@Override
	public void tick(float delta) {
		Actor spawner = (Actor) parent; 
		// BUG: Spawner, despite extending Actor, apparently doesn't have a getStage() method
		// TODO: Investigate "why the heck?"
		GameStage stage = (GameStage) spawner.getStage();
		GameController controller = stage.getController();
		controller.toast(message, 3);
		Gdx.app.debug("Display", message);
		finish();
	}

}
