package com.williamhayward.turrets.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.williamhayward.turrets.ui.ToastUI;

public class Toast extends Actor {
	public static final float DURATION = 5f;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 100;
	public static final int PADDING = 20;
	
	private NinePatch patch;
	private Label label;
	
	private ToastUI toaster;
	
	private float timer;
	private float speed;
	
	public Toast(String title, Skin skin) {
		patch = skin.getPatch("default-round");
		label = new Label(title, skin);
		label.setWrap(true);
		label.setAlignment(Align.center);
		if (label.getWidth() > WIDTH) {
			label.setWidth(WIDTH);
			// You have to pack and then set the width again, otherwise label.getHeight() isn't correct.
			// Source: http://stackoverflow.com/questions/24276180/libgdx-label-multiline-text-height
			label.pack();
			label.setWidth(WIDTH);
		}
		timer = DURATION;
	}
	
	public Toast(String title, Skin skin, float timer) {
		this(title, skin);
		this.timer = timer;
	}
	
	@Override
	public void act(float delta) {
		timer -= delta;
		float speed = 0;
		if (timer <= 0) {
			toaster.toastNext();
			this.remove();
		} else if (timer < 0.5f) { // Last second, move down
			speed = -this.speed;
		} else {
			float distance = label.getY() - PADDING;
			if (Math.abs(distance) <= Math.abs(this.speed)) {
				label.setY(PADDING);
			} else {
				speed = this.speed;
			}
		}
		label.setY(label.getY() + speed);
	}
	
	public void rush() {
		timer = 1;
	}
	
	public void setToaster(ToastUI toaster) {
		this.toaster = toaster;
		float x = toaster.getStage().getWidth() / 2 - label.getWidth() / 2;
		float y = -label.getHeight();
		label.setPosition(x, y);
		float distance = label.getY() - PADDING;
		speed = Math.abs(distance / 30f);
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		patch.draw(batch, label.getX() - PADDING / 2, label.getY(), label.getWidth() + PADDING, label.getHeight());
		label.draw(batch, alpha);
	}
}
