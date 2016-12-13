package thisisxanderh.turrets.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;


public class Toast extends Actor {
	public static final float DURATION = 5f;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 100;
	public static final int PADDING = 20;
	
	private NinePatch patch;
	private Label label;
	
	private float timer;
	public Toast(String title, Skin skin) {
		patch = skin.getPatch("default-round");
		label = new Label(title, skin);
		label.setWrap(true);
		timer = DURATION;
	}
	
	public Toast(String title, Skin skin, float timer) {
		this(title, skin);
		this.timer = timer;
	}
	
	@Override
	public void act(float delta) {
		timer -= delta;
		if (timer <= 0) {
			this.remove();
		}
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		Stage stage = this.getStage();
		label.setAlignment(Align.center);
		
		if (label.getWidth() > WIDTH) {
			label.setWidth(WIDTH);
			// You have to pack and then set the width again, otherwise label.getHeight() isn't correct.
			// Source: http://stackoverflow.com/questions/24276180/libgdx-label-multiline-text-height
			label.pack();
			label.setWidth(WIDTH);
		}

		float x = stage.getWidth() / 2 - label.getWidth() / 2;
		float y = PADDING;
		label.setPosition(x, y);
		patch.draw(batch, x - PADDING / 2, y, label.getWidth() + PADDING, label.getHeight());
		label.draw(batch, alpha);
	}
}
