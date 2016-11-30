package thisisxanderh.turrets.actors.enemies;

public abstract class EmitterCommand {
	protected boolean blocking = true;
	protected Emitter parent;
	public EmitterCommand(Emitter parent) {
		this.parent = parent;
	}
	
	public boolean blocking() {
		return blocking;
	}
	
	protected void finish() {
		parent.finished(this);
	}
	
	public abstract void tick(float delta);
}
