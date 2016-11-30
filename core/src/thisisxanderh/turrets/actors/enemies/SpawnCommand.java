package thisisxanderh.turrets.actors.enemies;

import java.util.List;
import java.util.ArrayList;


public class SpawnCommand extends EmitterCommand {
	private List<Enemy> children = new ArrayList<Enemy>();
	private boolean paced = false;
	private float timer = 0;
	private float gap;
	public SpawnCommand(Emitter parent, List<Enemy> children) {
		super(parent);
		this.children = children;
	}
	
	public SpawnCommand(Emitter parent, List<Enemy> children, float duration) {
		this(parent, children);
		paced = true;
		gap = duration / children.size();
		System.out.println(gap);
	}

	@Override
	public void tick(float delta) {
		if (paced) {
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
