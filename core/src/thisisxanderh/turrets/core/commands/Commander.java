package thisisxanderh.turrets.core.commands;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import thisisxanderh.turrets.actors.enemies.Enemy;

public interface Commander {

	
	void finished(Command command);

	void deactivate();

	List<Vector2> getPath();

	void spawn(Enemy child);

	void childDeath(Enemy enemy);

	List<Enemy> getChildren();
	
}
