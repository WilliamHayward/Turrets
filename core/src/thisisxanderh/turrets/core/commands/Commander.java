package thisisxanderh.turrets.core.commands;

import java.util.List;

import thisisxanderh.turrets.actors.enemies.Enemy;
import thisisxanderh.turrets.core.Coordinate;

public interface Commander {

	
	void finished(Command command);

	void deactivate();

	List<Coordinate> getPath();

	void spawn(Enemy child);

	void childDeath(Enemy enemy);

	List<Enemy> getChildren();
	
}
