package thisisxanderh.turrets.actors.enemies;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;

import thisisxanderh.turrets.core.Coordinate;

public class Emitter extends Actor {
	private List<Coordinate> positions;
	private List<Enemy> children = new ArrayList<>();
	private float delay = 3f;
	public Emitter() {
		positions = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			positions.add(null);
		}
		
	}
	
	public void addPosition(int position, float x, float y) {
		Coordinate coordinate = new Coordinate(x, y);
		positions.set(position, coordinate);
	}
	
	public List<Coordinate> getPath() {
		return positions;
	}
	
	@Override
	public void act(float delta) {
		delay -= delta;
		if (delay <= 0 && children.size() < 5) {
			delay = 3f;
			spawn();
		}
	}
	
	public void spawn() {
		Enemy creep = new Creep(this);
		this.getStage().addActor(creep);
		children.add(creep);
	}
	
	public void childDeath(Enemy child) {
		children.remove(child);
	}
}
