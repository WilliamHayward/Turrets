package com.williamhayward.turrets.core.commands;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.williamhayward.turrets.entities.enemies.Enemy;

public interface Commander {

	
	void finished(Command command);

	void deactivate();

	List<Vector2> getPath();

	void spawn(Enemy child);

	void childDeath(Enemy enemy);

	List<Enemy> getChildren();
	
}
