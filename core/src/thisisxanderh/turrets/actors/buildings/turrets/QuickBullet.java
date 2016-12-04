package thisisxanderh.turrets.actors.buildings.turrets;

import thisisxanderh.turrets.core.GameActor;
import thisisxanderh.turrets.graphics.SpriteList;

public class QuickBullet extends Bullet {

	public QuickBullet() {
		super(SpriteList.PLACEHOLDER);
		speed = 25f;
		damage = 0.3f;
	}

}
