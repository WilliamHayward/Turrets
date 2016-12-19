package thisisxanderh.turrets.input;

import com.badlogic.gdx.math.Vector2;

public interface InputManager {
	public void act(float delta);
	
	public boolean getSwitch();
	public boolean getJump();
	public boolean getPause();
	public boolean getBuild();
	public int getHotkey();
	public boolean getPrev();
	public boolean getNext();
	public boolean getExit();
	public boolean getPound();
	public boolean getConsole();
	public boolean getFacing(boolean prevFacing);
	public float getHorizontal();
	public float getVertical();
	
	public Vector2 getCursor();
	public Vector2 getCursorPosition();
	public Vector2 getCursorTile();
	
}
