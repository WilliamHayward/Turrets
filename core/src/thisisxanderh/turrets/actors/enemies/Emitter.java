package thisisxanderh.turrets.actors.enemies;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;

import thisisxanderh.turrets.core.Coordinate;

public class Emitter extends Actor {
	private List<Coordinate> positions;
	private List<Enemy> children = new ArrayList<>();
	
	private List<EmitterCommand> commands = new ArrayList<>();
	private List<EmitterCommand> performing = new ArrayList<>();
	
	public Emitter(String commandsLocation) {
		positions = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			positions.add(null);
		}
		commands = buildCommands(commandsLocation);
		nextCommand();
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
		for (EmitterCommand command: performing) {
			command.tick(delta);
		}
	}
	
	protected void spawn(Enemy child) {
		this.getStage().addActor(child);
		children.add(child);
		child.spawn();
	}
	
	public List<Enemy> getChildren() {
		return children;
	}
	
	public void childDeath(Enemy child) {
		children.remove(child);
	}

	private List<EmitterCommand> buildCommands(String path) {
		List<EmitterCommand> commands = new ArrayList<>();
		List<Enemy> spawnList = new ArrayList<>();
		commands.add(new WaitCommand(this, 1f));
		spawnList.add(new GiantSpider(this));
		spawnList.add(new Spider(this));
		spawnList.add(new GiantSpider(this));
		commands.add(new SpawnCommand(this, spawnList, 3));
		commands.add(new EndCommand(this));
		return commands;
	}
	
	protected void finished(EmitterCommand command) {
		performing.remove(command);
		nextCommand();
	}
	
	protected void deactivate() {
		this.remove();
	}
	
	private void nextCommand() {
		EmitterCommand command;
		do {
			command = commands.get(0);
			commands.remove(0);
			performing.add(command);
		} while(!command.blocking());
		
	}
	
}
