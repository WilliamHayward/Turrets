package thisisxanderh.turrets.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

import thisisxanderh.turrets.core.Coordinate;
import thisisxanderh.turrets.core.UIStage;
import thisisxanderh.turrets.input.schemes.ControlScheme;
import thisisxanderh.turrets.input.schemes.DefaultKeyboard;
import thisisxanderh.turrets.input.schemes.KeyboardScheme;
import thisisxanderh.turrets.terrain.Tile;

public class InputManager {
	private ControlScheme scheme;
	private Controller controller = null;
	private UIStage stage;
	private float cursorX;
	private float cursorY;
	public InputManager(UIStage stage) {
		this.stage = stage;
		scheme = new DefaultKeyboard();
	}

	public DeviceList getDevice() {
		return scheme.getDevice();
	}
	
	public void update() {
		switch (getDevice()) {
			case KEYBOARD:
				cursorX = Gdx.input.getX();
				cursorY = Gdx.input.getY();
				break;
			case CONTROLLER:
				float xDiff = controller.getAxis(3);
				float yDiff = controller.getAxis(4);
				if (Math.abs(xDiff) < 0.2f) {
					xDiff = 0;
				}
				if (Math.abs(yDiff) < 0.2f) {
					yDiff = 0;
				}
				cursorX += xDiff * 5;
				cursorY += yDiff * 5;
				cursorX = MathUtils.clamp(cursorX, 0, stage.getWidth());
				cursorY = MathUtils.clamp(cursorY, 0, stage.getHeight());
			default:
				return;
		}
	}
	
	public void setController(Controller controller) {
		//device = DeviceList.CONTROLLER;
		this.controller = controller;
	}
	
	public void setKeyboard() {
		scheme = new DefaultKeyboard();
	}
	
	/**
	 * Get cursor position on screen
	 */
	public Coordinate getCursor() {
		return new Coordinate(cursorX, cursorY);
	}
	
	/**
	 * Get cursor position in world
	 */
	public Coordinate getCursorPosition() {
		OrthographicCamera camera = (OrthographicCamera) stage.getGameStage().getCamera();
		Vector3 position = new Vector3(cursorX, cursorY, 0);
		camera.unproject(position);
		return new Coordinate(position.x, position.y);
	}
	
	/**
	 * Get cursor tile position in world
	 */
	public Coordinate getCursorTile() {
		Coordinate position = getCursorPosition();
		float x = (float) Math.floor(position.getX() / Tile.SIZE) * Tile.SIZE;
		float y = (float) Math.floor(position.getY() / Tile.SIZE) * Tile.SIZE;
		return new Coordinate(x, y);
	}
	
	public boolean getSwitch() {
		switch (getDevice()) {
			case KEYBOARD:
				return Gdx.input.isKeyJustPressed(Keys.P);
			case CONTROLLER:
			default:
				return false;
		}
	}
	
	public boolean getJump() {
		switch (getDevice()) {
			case KEYBOARD:
				return Gdx.input.isKeyJustPressed(
						((KeyboardScheme) scheme).getJump());
			case CONTROLLER:
				return controller.getButton(0);
			default:
				return false;
		}
	}
	
	public boolean getPause() {
		return Gdx.input.isKeyJustPressed(Keys.ESCAPE);
	}
	
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

	public boolean getPrev() {
		return Gdx.input.isKeyJustPressed(Keys.Q);
	}
	
	public boolean getNext() {
		return Gdx.input.isKeyJustPressed(Keys.E);
	}
	
	public boolean getExit() {
		return Gdx.input.isKeyJustPressed(Keys.ESCAPE);
	}
	
	public boolean getPound() {
		switch (getDevice()) {
			case KEYBOARD:
				return Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT);
			case CONTROLLER:
			default:
				return false;
		}
	}
	
	public boolean getFacing(boolean prevFacing) {
		switch (getDevice()) {
			case KEYBOARD:
				return Gdx.input.getX() < (Gdx.graphics.getWidth() / 2f);
			case CONTROLLER:
				float position = controller.getAxis(3);
				if (Math.abs(position) < 0.2f) {
					float movement = getHorizontalController();
					if (Math.abs(movement) > 0.2f) {
						return movement < 0;
					}
				} else {
					return position < 0;
				}
				break;
			default:
				return false;
		}
		return prevFacing;
	}
	
	public float getHorizontal() {
		switch (getDevice()) {
			case KEYBOARD:
				return getHorizontalKeyboard();
			case CONTROLLER:
				return getHorizontalController();
			default:
				return 0;
		}
	}
	
	public float getHorizontalKeyboard() {
		KeyboardScheme keyboard = (KeyboardScheme) scheme;
		float left = Gdx.input.isKeyPressed(keyboard.getLeft()) ? 1 : 0;
		float right = Gdx.input.isKeyPressed(keyboard.getRight()) ? 1 : 0;
		return right - left;
	}
	
	public float getHorizontalController() {
		float axis = controller.getAxis(0);
		return Math.abs(axis) < 0.2 ? 0 : axis;
	}
	

	
	public float getVertical() {
		switch (getDevice()) {
			case KEYBOARD:
				return getVerticalKeyboard();
			case CONTROLLER:
				return getVerticalController();
			default:
				return 0;
		}
	}
	
	public float getVerticalKeyboard() {
		KeyboardScheme keyboard = (KeyboardScheme) scheme;
		float up = Gdx.input.isKeyPressed(keyboard.getUp()) ? 1 : 0;
		float down = Gdx.input.isKeyPressed(keyboard.getDown()) ? 1 : 0;
		return up - down;
	}
	
	public float getVerticalController() {
		float axis = controller.getAxis(1);
		return Math.abs(axis) < 0.2 ? 0 : axis;
	}
}
