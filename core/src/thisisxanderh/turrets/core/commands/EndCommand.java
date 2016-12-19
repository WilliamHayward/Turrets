package thisisxanderh.turrets.core.commands;

import thisisxanderh.turrets.entities.enemies.Spawner;

/**
 * Immediately kills parent emitter
 */
public class EndCommand extends Command {

	public EndCommand(Spawner parent) {
		super(parent);
	}

	@Override
	public void tick(float delta) {
		parent.deactivate();
	}

}
