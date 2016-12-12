package thisisxanderh.turrets.actors.enemies;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;

import thisisxanderh.turrets.core.Coordinate;
import thisisxanderh.turrets.core.commands.Command;
import thisisxanderh.turrets.core.commands.Commander;
import thisisxanderh.turrets.core.commands.DisplayCommand;
import thisisxanderh.turrets.core.commands.EndCommand;
import thisisxanderh.turrets.core.commands.InvalidCommandException;
import thisisxanderh.turrets.core.commands.SpawnCommand;
import thisisxanderh.turrets.core.commands.WaitCommand;

public class Spawner extends Actor implements Commander {
	private List<Coordinate> positions;
	private List<Enemy> children = new ArrayList<>();

	List<Command> commands = new ArrayList<>();
	Command performing;
	
	
	public Spawner(String commandsLocation) throws InvalidCommandException {
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
		performing.tick(delta);
	}
	
	public void spawn(Enemy child) {
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

	private List<Command> buildCommands(String path) throws InvalidCommandException {
		List<Command> commands = new ArrayList<>();
		BufferedReader file = null;
		try {
			FileReader fileReader = new FileReader(path);
			file = new BufferedReader(fileReader);
			String line;
			String instruction;
			Command command = null;
			while ((line = file.readLine()) != null) {
				instruction = line.split(" ")[0];
				line = line.replaceFirst(instruction + " ", "");
				switch (instruction) {
					case "display":
						command = new DisplayCommand(this, line);
						break;
					case "wait":
						command = new WaitCommand(this, line);
						break;
					case "spawn":
						command = new SpawnCommand(this, line);
						break;
					default:
						throw new InvalidCommandException("Command " + instruction + " does not exist");
				}
				commands.add(command);
			}
			commands.add(new EndCommand(this));
			file.close();
			fileReader.close();
		} catch (FileNotFoundException e) {
			throw new InvalidCommandException("Command file not found");
		} catch (IOException e) {
			throw new InvalidCommandException("IO Error");
		}
		return commands;
	}
	
	public void deactivate() {
		this.remove();
	}
	
	public void finished(Command command) {
		nextCommand();
	}
	
	public void nextCommand() {
		Command command;
		command = commands.get(0);
		commands.remove(0);
		performing = command;
	}
	
}
