package thisisxanderh.turrets.core.commands;

import thisisxanderh.turrets.actors.enemies.Spawner;

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
