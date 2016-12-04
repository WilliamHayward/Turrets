package thisisxanderh.turrets.actors.buildings.traps;

public abstract class TrapEffect {
	protected float speedModifier = 1f; // Multiply speed by this
	protected float dps = 0f;
	public TrapEffect() {
		
	}
	
	public void combine(TrapEffect other) {
		speedModifier *= other.getSpeedModifier();
		dps += other.getSpeedModifier();
	}

	public float getDPS() {
		return dps;
	}
	
	public float getSpeedModifier() {
		return speedModifier;
	}
}
