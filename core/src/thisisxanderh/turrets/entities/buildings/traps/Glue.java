package thisisxanderh.turrets.entities.buildings.traps;

import thisisxanderh.turrets.entities.players.Player;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.terrain.Tile;

public class Glue extends Trap {

	public Glue(Player owner) {
		super(owner, SpriteList.GLUE);
		this.setSize(Tile.SIZE * 2f, Tile.SIZE / 3f);
		effect = new GlueEffect(owner);
		name = "Glue";
		
		this.cost = 5;
	}

}
