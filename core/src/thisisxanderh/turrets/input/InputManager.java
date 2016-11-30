package thisisxanderh.turrets.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;

import thisisxanderh.turrets.input.schemes.ControlScheme;
import thisisxanderh.turrets.input.schemes.DefaultKeyboard;
import thisisxanderh.turrets.input.schemes.KeyboardScheme;

public class InputManager {
	private ControlScheme scheme;
	private Controller controller = null;
	
	public InputManager() {
		scheme = new DefaultKeyboard();
	}

	public DeviceList getDevice() {
		return scheme.getDevice();
	}
	
	public void setController(Controller controller) {
		//device = DeviceList.CONTROLLER;
		this.controller = controller;
	}
	
	public void setKeyboard() {
		scheme = new DefaultKeyboard();
	}
	
	public boolean getJump() {
		switch (getDevice()) {
			case KEYBOARD:
				return Gdx.input.isKeyJustPressed(
						((KeyboardScheme) scheme).getJump());
			case CONTROLLER:
				return controller.getButton(0);
		}
		return false;
	}
	
	public boolean getFacing(boolean prevFacing) {
		switch (getDevice()) {
			case KEYBOARD:
				return Gdx.input.getX() < (Gdx.graphics.getWidth() / 2f);
			case CONTROLLER:
				float position = controller.getAxis(3);
				if (Math.abs(position) < 0.2f) {
					float movement = getHorizontalController();
					System.out.println(movement);
					if (Math.abs(movement) > 0.2f) {
						return movement < 0;
					}
				} else {
					return position < 0;
				}
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
		}
		return 0;
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
}
