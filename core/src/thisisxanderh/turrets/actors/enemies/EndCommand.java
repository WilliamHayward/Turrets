package thisisxanderh.turrets.actors.enemies;

/**
 * Immediately kills parent emitter
 */
public class EndCommand extends EmitterCommand {

	public EndCommand(Emitter parent) {
		super(parent);
	}

	@Override
	public void tick(float delta) {
		parent.deactivate();
	}

}
