package thisisxanderh.turrets.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import thisisxanderh.turrets.actors.GameActor;
import thisisxanderh.turrets.terrain.Terrain;

public class GameStage extends Stage {
	public static final int SIZE = 50;
	
	private TiledMap map;
	private TiledMapRenderer mapRenderer;
	private Terrain terrain;
	private List<Coordinate> spawns = new ArrayList<>();
	private List<GameActor> deadList = new ArrayList<>();
	
	public GameStage() {
		
	}

	public GameStage(Viewport viewport) {
		super(viewport);
	}

	public GameStage(Viewport viewport, Batch batch) {
		super(viewport, batch);
	}
	
	public void setMap(TiledMap map) {
		this.map = map;
		terrain = new Terrain(map);
		mapRenderer = new OrthogonalTiledMapRenderer(map);
	}
	
	public TiledMap getMap() {
		return map;
	}
	
	public void addSpawn(float x, float y) {
		Coordinate spawn = new Coordinate(x, y);
		addSpawn(spawn);
	}
	
	public void addSpawn(Coordinate spawn) {
		spawns.add(spawn);
	}
	
	public Coordinate getSpawn() {
		Random random = new Random();
		int index = random.nextInt(spawns.size());
		return spawns.get(index);
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	public List<GameActor> getGameActors() {
		List<GameActor> actors = new ArrayList<>();
		for (Actor actor: this.getActors()) {
			if (actor instanceof GameActor) {
				actors.add((GameActor) actor);
			}
		}
		return actors;
	}
	
	@Override
	public void act(float delta) {
		
		super.act(delta);
		
		// Process collisions between actors
		List<GameActor> actors = this.getGameActors();
		List<GameActor> others = this.getGameActors(); // Two lists to prevent concurrent modification
		for (GameActor actor: actors) {
			if (actor.collides()) {
				Rectangle bounds = actor.getBounds();
				for (GameActor other: others) {
					if (!other.collides()) {
						continue;
					}
					if (!actor.equals(other)) {
						if (bounds.overlaps(other.getBounds())) {
							actor.collided(other);
							other.collided(actor);
						}
					}
				}
			}
			others.remove(actor); // Collisions are already two way, don't need to be checked twice
		}

		for(GameActor actor: deadList) {
			actor.remove();
		}
		deadList.clear();
		
	}
	
	/**
	 * Delay actor death to prevent concurrent modification
	 */
	public void kill(GameActor actor) {
		deadList.add(actor);
	}
	
	
	@Override
	public void draw() {
		OrthographicCamera camera = (OrthographicCamera) this.getViewport().getCamera();
		if (camera != null) {
	        camera.update();
	        mapRenderer.setView(camera);
	        mapRenderer.render();
		}
		super.draw();
	}
}
