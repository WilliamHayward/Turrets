package thisisxanderh.turrets.actors.enemies;

public class DisplayCommand extends EmitterCommand {
	private String message;
	public DisplayCommand(Emitter parent, String message) {
		super(parent);
		this.message = message;
	}

	@Override
	public void tick(float delta) {
		System.out.println(message); //TODO: Obviously have this display an actual message
		finish();
	}

}
