package thisisxanderh.turrets.actors.enemies;

public class WaitCommand extends EmitterCommand {
	private float timer;
	private boolean cleared = false;
	public WaitCommand(Emitter parent, float timer) {
		super(parent);
		this.timer = timer;
	}
	
	public WaitCommand(Emitter parent) {
		super(parent);
		cleared = true;
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
