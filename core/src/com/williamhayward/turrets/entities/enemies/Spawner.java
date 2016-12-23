package com.williamhayward.turrets.entities.enemies;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.williamhayward.turrets.core.commands.BuildCommand;
import com.williamhayward.turrets.core.commands.Command;
import com.williamhayward.turrets.core.commands.Commander;
import com.williamhayward.turrets.core.commands.DisplayCommand;
import com.williamhayward.turrets.core.commands.EndCommand;
import com.williamhayward.turrets.core.commands.InvalidCommandException;
import com.williamhayward.turrets.core.commands.SpawnCommand;
import com.williamhayward.turrets.core.commands.WaitCommand;

public class Spawner extends Actor implements Commander {
	private List<Vector2> positions;
	private List<Enemy> children = new ArrayList<>();

	List<Command> commands = new ArrayList<>();
	Command performing = null;
	
	
	public Spawner(String commandsLocation) throws InvalidCommandException {
		positions = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			positions.add(null);
		}
		commands = buildCommands(commandsLocation);
	}
	
	
	
	public void addPosition(int position, float x, float y) {
		Vector2 Vector2 = new Vector2(x, y);
		positions.set(position, Vector2);
	}
	
	public List<Vector2> getPath() {
		return positions;
	}
	
	public boolean isBuild() {
		return (performing instanceof BuildCommand);
	}
	
	@Override
	public void act(float delta) {
		if (performing == null) {
			return;
		}
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
					case "build":
						command = new BuildCommand(this);
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
