package thisisxanderh.turrets.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.mappings.Xbox;

public class InputManager {
	private DeviceList device = DeviceList.KEYBOARD;
	private Controller controller = null;
	
	private float controllerLeftX = 0;
	
	public InputManager() {
		
	}
	
	public InputManager(DeviceList device) {
		this.device = device;
	}

	public void setController(Controller controller) {
		device = DeviceList.CONTROLLER;
		this.controller = controller;
	}
	
	public void setKeyboard() {
		device = DeviceList.KEYBOARD;
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
		return controller.getAxis(Xbox.L_STICK_HORIZONTAL_AXIS);
	}
}
