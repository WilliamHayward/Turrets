package com.williamhayward.turrets.entities.buildings.traps;

import com.williamhayward.turrets.entities.players.Player;
import com.williamhayward.turrets.graphics.SpriteList;
import com.williamhayward.turrets.terrain.Tile;

public class Glue extends Trap {

	public Glue(Player owner) {
		super(owner, SpriteList.GLUE);
		this.setSize(Tile.SIZE * 2f, Tile.SIZE / 3f);
		effect = new GlueEffect(owner);
		name = "Glue";
		
		this.cost = 5;
	}

}
