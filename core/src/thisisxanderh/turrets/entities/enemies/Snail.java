package thisisxanderh.turrets.entities.enemies;

import thisisxanderh.turrets.core.commands.Commander;
import thisisxanderh.turrets.entities.Modifiers;
import thisisxanderh.turrets.entities.States;
import thisisxanderh.turrets.graphics.SpriteList;

public class Snail extends Enemy {
	public float hideTimer = 0;
	public Snail(Commander parent) {
		super(SpriteList.SNAIL, parent);
		this.applyModifier(Modifiers.GIANT);
		maxHealth = 1;
		health = maxHealth;
		speed = 2f;
		bounty = 10;
		this.addState(States.INVINCIBLE, SpriteList.SNAIL_HIDING);
	}
	
	@Override
	public void act(float delta) {
		hideTimer -= delta;
		if (hideTimer < 0) {
			if(this.isState(States.INVINCIBLE)) {
				this.setState(States.DEFAULT);
			}
			super.act(delta);
		}
	}
	
	@Override
	public void bop(float damage) {
		this.setState(States.INVINCIBLE);
		hideTimer = 1f;
		resize();
	}

}
