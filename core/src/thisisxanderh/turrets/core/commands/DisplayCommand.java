package thisisxanderh.turrets.core.commands;

import thisisxanderh.turrets.actors.enemies.Spawner;

public class DisplayCommand extends Command {
	private String message;
	public DisplayCommand(Spawner parent, String string) {
		super(parent);
		this.message = string;
	}

	@Override
	public void tick(float delta) {
		System.out.println(message); //TODO: Obviously have this display an actual message
		finish();
	}

}
