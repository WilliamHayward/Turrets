package thisisxanderh.turrets.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import javafx.scene.paint.Color;
import thisisxanderh.turrets.graphics.LayerList;
import thisisxanderh.turrets.terrain.Terrain;

public class GameStage extends Stage {
	public static final int SIZE = 50;
	
	private TiledMap map;
	private TiledMapRenderer mapRenderer;
	private Terrain terrain;
	private Map<Color, List<Coordinate>> spawns = new HashMap<>();
	private List<GameActor> deadList = new ArrayList<>();
	
	private Map<LayerList, Group> layers = new HashMap<>();
	
	public GameStage() {
		init();
	}

	public GameStage(Viewport viewport) {
		super(viewport);
		init();
	}

	public GameStage(Viewport viewport, Batch batch) {
		super(viewport, batch);
		init();
	}
	
	private void init() {
		for (LayerList layer: LayerList.values()) {
			Group layerGroup = new Group();
			layers.put(layer, layerGroup);
			this.addLayer(layerGroup);
		}
	}
	
	public void setMap(TiledMap map) {
		this.map = map;
		terrain = new Terrain(map);
		mapRenderer = new OrthogonalTiledMapRenderer(map);
	}
	
	public TiledMap getMap() {
		return map;
	}
	
	public void addSpawn(float x, float y, Color color) {
		Coordinate spawn = new Coordinate(x, y);
		addSpawn(spawn, color);
	}
	
	public void addSpawn(Coordinate spawn, Color color) {
		if (spawns.get(color) == null) {
			spawns.put(color, new ArrayList<>());
		}
		spawns.get(color).add(spawn);
	}
	
	public Coordinate getSpawn(Color color) {
		List<Coordinate> list = spawns.get(color);
		if (list == null) {
			return null;
		}
		Random random = new Random();
		int index = random.nextInt(list.size());
		return list.get(index);
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
		/**
		 * This is overkill for what Turrets need, but if all GameActors need to
		 * be able to collide with all other GameActors, this will make that happen
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
		}*/
	
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
	
	private void addLayer(Group group) {
		super.addActor(group);
	}
	
	@Override
	public void addActor(Actor actor) {
		LayerList layer = LayerList.DEFAULT;
		if (actor instanceof GameActor) {
			layer = ((GameActor) actor).getLayer();
		}
		layers.get(layer).addActor(actor);
	}
	
	@Override
	public Array<Actor> getActors () {
		Array<Actor> actors = new Array<>();
		for (Group layer: layers.values()) {
			for (Actor actor: layer.getChildren()) {
				actors.add(actor);
			}
		}
		return actors;
	}
}
