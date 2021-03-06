package com.williamhayward.turrets.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.williamhayward.turrets.entities.players.Player;

public class PauseUI extends UserInterface {
	public PauseUI(Player player, Skin skin) {
		super();
		Label label = new Label("Paused", skin);
		this.addActor(label);
	}
}
