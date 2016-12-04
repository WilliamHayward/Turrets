package thisisxanderh.turrets.actors.players;

import com.badlogic.gdx.graphics.OrthographicCamera;

import javafx.scene.paint.Color;

import thisisxanderh.turrets.graphics.SpriteList;

public class BluePlayer extends Player {

	public BluePlayer(OrthographicCamera camera) {
		super(camera, SpriteList.PLAYER_BLUE_STANDING);
		color = Color.BLUE;
	}

}
