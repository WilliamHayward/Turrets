package thisisxanderh.turrets.actors.buildings.turrets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import thisisxanderh.turrets.actors.buildings.Building;
import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.graphics.SpriteList;

public abstract class Turret extends Building {
	protected Texture barrel;
	public Turret(SpriteList textureID) {
		super(textureID);
	}
	
	@Override
	protected boolean validPosition() {
		return super.validPosition(this.getBounds().getHeight());
	}

}
