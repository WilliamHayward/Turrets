package thisisxanderh.turrets.input.schemes;

import com.badlogic.gdx.Input.Keys;

import thisisxanderh.turrets.input.DeviceList;

public abstract class KeyboardScheme extends ControlScheme {
	protected int jump;
	protected int left = Keys.A;
	protected int right = Keys.D;
	public KeyboardScheme() {
		device = DeviceList.KEYBOARD;
	}

	public int getJump() {
		return jump;
	}
	
	public int getLeft() {
		return left;
	}
	
	public int getRight() {
		return right;
	}
	
	public void setJump(int jump) {
		this.jump = jump;
	}
	
	public void setLeft(int left) {
		this.left = left;
	}
	
	public void setRight(int right) {
		this.right = right;
	}
}
