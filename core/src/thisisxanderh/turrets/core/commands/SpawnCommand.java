package thisisxanderh.turrets.core.commands;

import java.util.List;

import thisisxanderh.turrets.entities.Modifiers;
import thisisxanderh.turrets.entities.enemies.Enemy;
import thisisxanderh.turrets.entities.enemies.EnemyList;
import thisisxanderh.turrets.entities.enemies.Fly;
import thisisxanderh.turrets.entities.enemies.Slime;
import thisisxanderh.turrets.entities.enemies.Snail;
import thisisxanderh.turrets.entities.enemies.Spawner;
import thisisxanderh.turrets.entities.enemies.Spider;

import java.util.ArrayList;


public class SpawnCommand extends Command {
	private List<Enemy> children = new ArrayList<Enemy>();
	private boolean paced = false;
	private float timer = 0;
	private float gap;
	public SpawnCommand(Spawner parent, String string) {
		super(parent);
		String[] parameters = string.split(" ");
		children = buildChildren(parameters[0]);
		if (parameters.length == 2) {
			paced = true;
			float duration = Float.parseFloat(parameters[1]);
			gap = duration / children.size();
		}
	}
	
	private List<Enemy> buildChildren(String string) {
		String[] childrenStrings = string.split(",");
		List<Enemy> children = new ArrayList<>();
		
		for (String childString: childrenStrings) {
			String[] childInfo = childString.trim().split("-");
			
			EnemyList childType = EnemyList.valueOf(childInfo[0]);
			Enemy child = null;
			switch (childType) {
				case SPIDER:
					child = new Spider(parent);
					break;
				case SLIME:
					child = new Slime(parent);
					break;
				case SNAIL:
					child = new Snail(parent);
					break;
				case FLY:
					child = new Fly(parent);
					break;
				default:
					break;
				
			}
			
			for (int i = 1; i < childInfo.length; i++) {
				Modifiers modification = Modifiers.valueOf(childInfo[i]);
				child.applyModifier(modification);
			}
			
			children.add(child);
		}
		return children;
	}

	@Override
	public void tick(float delta) {
		if (paced) {
			if (children.size() == 0) {
				finish();
			}
			timer += delta;
			int spawnCount = (int) Math.floor(timer / gap); //Find out how many should be spawned
			if (children.size() == 0) {
				return;
			}
			for (int i = 0; i < spawnCount; i++) {
				Enemy child = children.get(0);
				parent.spawn(child);
				children.remove(0);
				if (children.size() == 0) {
					finish();
				}
			}
			timer -= gap * spawnCount; // Leave remainder
		} else {
			for (Enemy child: children) {
				parent.spawn(child);
			}
			finish();
		}
	}

}
