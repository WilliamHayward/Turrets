package thisisxanderh.turrets.input;

import thisisxanderh.turrets.core.Coordinate;

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
	public boolean getFacing(boolean prevFacing);
	public float getHorizontal();
	public float getVertical();
	
	public Coordinate getCursor();
}
