package com.williamhayward.turrets.core.commands;

import com.williamhayward.turrets.entities.enemies.Spawner;

public class WaitCommand extends Command {
	private float timer;
	private boolean cleared = false;
	public WaitCommand(Spawner parent, String string) {
		super(parent);
		if ("cleared".equals(string)) {
			cleared = true;
		} else {
			timer = Float.parseFloat(string);
		}
	}

	@Override
	public void tick(float delta) {
		if (cleared) {
			if (parent.getChildren().isEmpty()) {
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
