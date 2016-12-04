package thisisxanderh.turrets.actors.buildings.traps;

import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.terrain.Tile;

public class Glue extends Trap {

	public Glue() {
		super(SpriteList.GLUE);
		this.setSize(Tile.SIZE * 2f, Tile.SIZE / 3f);
		effect = new GlueEffect();
		name = "Glue";
	}

}
