package thisisxanderh.turrets.actors.buildings.traps;

import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.terrain.Tile;

public class Spikes extends Trap {

	public Spikes() {
		super(SpriteList.SPIKES);
		this.setSize(Tile.SIZE, Tile.SIZE / 2);
		effect = new SpikesEffect();
	}

}
