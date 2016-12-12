package thisisxanderh.turrets.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import thisisxanderh.turrets.actors.buildings.Building;
import thisisxanderh.turrets.actors.enemies.Enemy;
import thisisxanderh.turrets.actors.enemies.Spawner;
import thisisxanderh.turrets.actors.players.PlayerTypes;
import thisisxanderh.turrets.core.commands.InvalidCommandException;
import thisisxanderh.turrets.graphics.LayerList;
import thisisxanderh.turrets.terrain.Terrain;

public class GameStage extends Stage {
	public static final int SIZE = 50;
	
	private TiledMap map;
	private TiledMapRenderer mapRenderer;
	private Terrain terrain;
	private Map<PlayerTypes, List<Coordinate>> spawns = new HashMap<>();
	private List<GameActor> deadList = new ArrayList<>();
	
	private Map<LayerList, Group> layers = new HashMap<>();

	private GameController controller;
	
	public GameStage() {
		init();
	}
	
	private void init() {
		for (LayerList layer: LayerList.values()) {
			Group layerGroup = new Group();
			layers.put(layer, layerGroup);
			this.addLayer(layerGroup);
		}
		
		OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.zoom = 3f;
		Viewport viewport = new ScreenViewport(camera);
		setViewport(viewport);
		

	}

	
	public void setController(GameController controller) {
		this.controller = controller;
	}
	
	public GameController getController() {
		return controller;
	}
	public void setMap(TiledMap map) {
		this.map = map;
		terrain = new Terrain(map);
		mapRenderer = new OrthogonalTiledMapRenderer(map);

		Spawner spawn = null;
		try {
			spawn = new Spawner("tiles/spawn.txt");
		} catch (InvalidCommandException e) {
			e.printStackTrace();
			System.exit(1);
		}
        
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                if (cell == null) {
                	continue;
                }
                MapProperties properties = cell.getTile().getProperties();
                Object property = properties.get("spawn");
                if(property != null){
                	String type = (String) property;
                	addSpawn(x, y, PlayerTypes.valueOf(type));
                    cell.setTile(null);
                }
                property = properties.get("enemy_path");
                if (property != null) {
                	int pathPosition  = (int) property;
                	spawn.addPosition(pathPosition, x, y);
                    cell.setTile(null);
                }
            }
        }
        addActor(spawn);

	}
	
	public TiledMap getMap() {
		return map;
	}
	
	public void addSpawn(float x, float y, PlayerTypes type) {
		Coordinate spawn = new Coordinate(x, y);
		addSpawn(spawn, type);
	}
	
	public void addSpawn(Coordinate spawn, PlayerTypes type) {
		if (spawns.get(type) == null) {
			spawns.put(type, new ArrayList<>());
		}
		spawns.get(type).add(spawn);
	}
	
	public Coordinate getSpawn(PlayerTypes type) {
		List<Coordinate> list = spawns.get(type);
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

	public List<Building> getBuildings() {
		List<Building> buildings = new ArrayList<>();
		for (Actor actor: this.getActors()) {
			if (actor instanceof Building) {
				buildings.add((Building) actor);
			}
		}
		return buildings;
	}

	public List<Enemy> getEnemies() {
		List<Enemy> enemies = new ArrayList<>();
		for (Actor actor: this.getActors()) {
			if (actor instanceof Enemy) {
				enemies.add((Enemy) actor);
			}
		}
		return enemies;
	}
	
	@Override
	public void act(float delta) {
		
		super.act(delta);
		
		// Process collisions between actors
		/**
		 * This is overkill for what Turrets need, but if all GameActors need to
		 * be able to collide with all other GameActors, this will make that happen
		 */
		List<GameActor> actors = this.getGameActors();
		List<GameActor> others = this.getGameActors(); // Two lists to prevent concurrent modification
		for (GameActor actor: actors) {
			if (!actor.collides()) {
				continue;
			}
			Rectangle bounds = actor.getBounds();
			for (GameActor other: others) {
				if (!other.collides() || actor.equals(other)) {
					continue;
				}
				if (bounds.overlaps(other.getBounds())) {
					actor.collided(other);
					other.collided(actor);
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
