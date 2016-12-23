package com.williamhayward.turrets.entities.buildings.traps;

import com.williamhayward.turrets.entities.players.Player;
import com.williamhayward.turrets.graphics.SpriteList;
import com.williamhayward.turrets.terrain.Tile;

public class Spikes extends Trap {

	public Spikes(Player owner) {
		super(owner, SpriteList.SPIKES);
		this.setSize(Tile.SIZE, Tile.SIZE / 2);
		effect = new SpikesEffect(owner);
		name = "Spikes";
		
		this.cost = 10;
	}

}
