package com.williamhayward.turrets.core.commands;

import com.williamhayward.turrets.entities.enemies.Spawner;

public abstract class Command {
	protected Commander parent;
	
	public Command(Spawner parent) {
		this.parent = parent;
	}
	
	protected void finish() {
		parent.finished(this);
	}
	
	public abstract void tick(float delta);
}
