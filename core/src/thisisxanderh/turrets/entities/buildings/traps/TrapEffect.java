package thisisxanderh.turrets.entities.buildings.traps;

import java.util.ArrayList;
import java.util.List;

import thisisxanderh.turrets.entities.players.Player;

public class TrapEffect {
	protected float speedModifier = 1f; // Multiply speed by this
	protected float dps = 0f;
	private List<Player> owners = new ArrayList<>();
	
	public TrapEffect() {
		
	}
	
	public TrapEffect(Player owner) {
		owners.add(owner);
	}
	
	public void combine(TrapEffect other) {
		speedModifier *= other.getSpeedModifier();
		dps += other.getDPS();
		owners.addAll(other.getOwners());
	}

	public float getDPS() {
		return dps;
	}
	
	public float getSpeedModifier() {
		return speedModifier;
	}
	
	public List<Player> getOwners() {
		return owners;
	}
	
	public void addOwner(Player player) {
		owners.add(player);
	}
}
