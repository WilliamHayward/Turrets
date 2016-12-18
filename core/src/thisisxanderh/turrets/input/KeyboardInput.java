package thisisxanderh.turrets.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class KeyboardInput implements InputManager {
	private Stage stage;
	
	private float cursorX;
	private float cursorY;

	@Override
	public void act(float delta) {
		cursorX = Gdx.input.getX();
		cursorY = Gdx.input.getY();
	}
	
	public KeyboardInput(Stage stage) {
		this.stage = stage;
	}

	@Override
	public boolean getSwitch() {
		return Gdx.input.isKeyJustPressed(Keys.P);
	}

	@Override
	public boolean getJump() {
		return Gdx.input.isKeyJustPressed(Keys.SPACE);
	}

	@Override
	public boolean getPause() {
		return Gdx.input.isKeyJustPressed(Keys.ESCAPE);
	}

	@Override
	public boolean getBuild() {
		if (Gdx.input.justTouched()) {
			Vector2 position = stage.screenToStageCoordinates(new Vector2(cursorX, cursorY));
			Actor hit = stage.hit(position.x, position.y, false);
			if (hit == null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getHotkey() {
		if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
			return 0;
		}
		if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
			return 1;
		}
		if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
			return 2;
		}
		if (Gdx.input.isKeyJustPressed(Keys.NUM_4)) {
			return 3;
		}
		return -1;
	}

	@Override
	public boolean getPrev() {
		return Gdx.input.isKeyJustPressed(Keys.Q);
	}

	@Override
	public boolean getNext() {
		return Gdx.input.isKeyJustPressed(Keys.E);
	}

	@Override
	public boolean getExit() {
		return Gdx.input.isKeyJustPressed(Keys.ESCAPE);
	}

	@Override
	public boolean getPound() {
		return Gdx.input.isKeyJustPressed(Keys.SHIFT_LEFT);
	}

	@Override
	public boolean getFacing(boolean prevFacing) {
		return Gdx.input.getX() < (Gdx.graphics.getWidth() / 2f);
	}

	@Override
	public float getHorizontal() {
		float left = Gdx.input.isKeyPressed(Keys.A) ? 1 : 0;
		float right = Gdx.input.isKeyPressed(Keys.D) ? 1 : 0;
		return right - left;
	}

	@Override
	public float getVertical() {
		float up = Gdx.input.isKeyPressed(Keys.W) ? 1 : 0;
		float down = Gdx.input.isKeyPressed(Keys.S) ? 1 : 0;
		return up - down;
	}

	@Override
	public Vector2 getCursor() {
		return new Vector2(Gdx.input.getX(), Gdx.input.getY());
	}

	@Override
	public boolean getConsole() {
		return Gdx.input.isKeyJustPressed(Keys.GRAVE);
	}

}
