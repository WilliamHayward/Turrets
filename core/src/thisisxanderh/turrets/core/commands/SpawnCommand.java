package thisisxanderh.turrets.core.commands;

import java.util.List;

import thisisxanderh.turrets.entities.enemies.Enemy;
import thisisxanderh.turrets.entities.enemies.EnemyList;
import thisisxanderh.turrets.entities.enemies.GiantSpider;
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
		// TODO: This is a bit messy. Set up a register of enemies
		for (String childString: childrenStrings) {
			EnemyList child = EnemyList.valueOf(childString);
			switch (child) {
			case GIANT_SPIDER:
				children.add(new GiantSpider(parent));
				break;
			case SPIDER:
				children.add(new Spider(parent));
				break;
			default:
				break;
				
			}
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
