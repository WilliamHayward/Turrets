package com.williamhayward.turrets.core.commands;

import com.williamhayward.turrets.entities.enemies.Spawner;

public class BuildCommand extends Command {

	public BuildCommand(Spawner parent) {
		super(parent);
	}

	@Override
	public void tick(float delta) {
		// Do nothing
	}

}
