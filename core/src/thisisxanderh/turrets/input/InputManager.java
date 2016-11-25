package thisisxanderh.turrets.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;

public class InputManager {
	private DeviceList device = DeviceList.KEYBOARD;
	private Controller controller = null;
	
	private XboxListener controllerListener;
	
	public InputManager() {
		
	}
	
	public InputManager(DeviceList device) {
		this.device = device;
	}

	public DeviceList getDevice() {
		return device;
	}
	
	public void setController(Controller controller) {
		device = DeviceList.CONTROLLER;
		this.controller = controller;
		controllerListener = new XboxListener();
		controller.addListener(controllerListener);
	}
	
	public void setKeyboard() {
		device = DeviceList.KEYBOARD;
	}
	
	public boolean getJump() {
		switch (device) {
			case KEYBOARD:
				return Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
			case CONTROLLER:
				return controller.getButton(0);
		}
		return false;
	}
	
	public boolean getFacing(boolean prevFacing) {
		switch (device) {
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
		switch (device) {
			case KEYBOARD:
				return getHorizontalKeyboard();
			case CONTROLLER:
				return getHorizontalController();
			default:
		}
		return 0;
	}
	
	public float getHorizontalKeyboard() {
		float left = Gdx.input.isKeyPressed(Input.Keys.A) ? 1 : 0;
		float right = Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : 0;
		return right - left;
	}
	
	public float getHorizontalController() {
		float axis = controller.getAxis(0);
		return Math.abs(axis) < 0.2 ? 0 : axis;
	}
}
