package com.williamhayward.turrets.core.commands;

import com.williamhayward.turrets.entities.enemies.Spawner;

public class WaitCommand extends Command {
	private float timer;
	private boolean cleared = false;
	public WaitCommand(Spawner parent, String string) {
		super(parent);
		if (string.equals("cleared")) {
			cleared = true;
		} else {
			timer = Float.parseFloat(string);
		}
	}

	@Override
	public void tick(float delta) {
		if (cleared) {
			if (parent.getChildren().size() == 0) {
				finish();
			}
		} else {
			timer -= delta;
			if (timer <= 0) {
				finish();
			}
		}
	}

}
