package thisisxanderh.turrets.entities.buildings.traps;

import thisisxanderh.turrets.entities.players.Player;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.terrain.Tile;

public class Spikes extends Trap {

	public Spikes(Player owner) {
		super(owner, SpriteList.SPIKES);
		this.setSize(Tile.SIZE, Tile.SIZE / 2);
		effect = new SpikesEffect(owner);
		name = "Spikes";
		
		this.cost = 10;
	}

}
