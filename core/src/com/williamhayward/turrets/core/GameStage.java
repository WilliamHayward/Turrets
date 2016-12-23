package com.williamhayward.turrets.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.williamhayward.turrets.core.commands.InvalidCommandException;
import com.williamhayward.turrets.entities.Entity;
import com.williamhayward.turrets.entities.enemies.Spawner;
import com.williamhayward.turrets.entities.players.Player;
import com.williamhayward.turrets.entities.players.PlayerTypes;
import com.williamhayward.turrets.graphics.LayerList;
import com.williamhayward.turrets.terrain.Terrain;

public class GameStage extends Stage {
	public static final int SIZE = 50;
	
	private TiledMap map;
	private TiledMapRenderer mapRenderer;
	private Terrain terrain;
	private Terrain buildTurret;
	private Terrain buildTrap;
	private Map<PlayerTypes, List<Vector2>> spawns = new HashMap<>();
	private List<Entity> deadList = new ArrayList<>();
	
	private boolean debug = false;
	
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
		terrain = new Terrain(map.getLayers().get("Terrain"), "solid", "true");
		if (debug) {
			this.addActor(terrain);
		}
		
		MapLayer buildLayer = map.getLayers().get("Build");
		buildTurret = new Terrain(buildLayer, "build", "turret");
		
		buildTrap = new Terrain(buildLayer, "build", "trap");
		
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		
		for (MapLayer mapLayer: map.getLayers()) {
			TiledMapTileLayer layer = (TiledMapTileLayer) mapLayer;
			if (layer.getProperties().get("spawner") != null) {
				addEnemySpawner(layer);
			}
		}
		
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Spawns");
        addPlayerSpawns(layer);
	}
	
	private void addPlayerSpawns(TiledMapTileLayer layer) {
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
            }
        }
	}
	
	private void addEnemySpawner(TiledMapTileLayer layer) {
		Spawner spawn = null;
		String path = (String) layer.getProperties().get("spawner");
		
		try {
			spawn = new Spawner(path);
		} catch (InvalidCommandException e) {
			e.printStackTrace();
		}
		
		if (spawn == null) {
			return;
		}
		
		for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                if (cell == null) {
                	continue;
                }
                MapProperties properties = cell.getTile().getProperties();
                Object property = properties.get("enemy_path");
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
		Vector2 spawn = new Vector2(x, y);
		addSpawn(spawn, type);
	}
	
	public void addSpawn(Vector2 spawn, PlayerTypes type) {
		if (spawns.get(type) == null) {
			spawns.put(type, new ArrayList<>());
		}
		spawns.get(type).add(spawn);
	}
	
	public Vector2 getSpawn(PlayerTypes type) {
		List<Vector2> list = spawns.get(type);
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
	
	public Terrain getBuildTurret() {
		return buildTurret;
	}
	
	public Terrain getBuildTrap() {
		return buildTrap;
	}
	
	/**
	 * This filters down a list of all actors to just those
	 * which are a given instance.
	 */
	public <T> List<T> getActors(Class<T> filter) {
		List<T> actors = new ArrayList<>();
		for (Actor actor: this.getActors()) {
			if (filter.isInstance(actor)) { // Check that it is correct class
				actors.add(filter.cast(actor)); // Cast to that class and add to list
			}
		}
		return actors;
	}
	
	@SuppressWarnings("unchecked")
	public List<Entity> getEntities() {
		return (List<Entity>)(Object) getActors(Entity.class);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (!controller.isBuildMode() && controller.siegeEnded()) {
			controller.startBuild();
		}
		
		/**
		 * Check if each actor is colliding with each other actor.
		 */
		List<Entity> entities = this.getEntities();
		List<Entity> others = new ArrayList<>(entities); // Two lists to prevent concurrent modification
		
		for (Entity entity: entities) {
			if (!entity.collides()) {
				continue;
			}
			Rectangle bounds = entity.getBounds();
			for (Entity other: others) {
				if (!other.collides() || entity.equals(other)) {
					continue;
				}
				if (bounds.overlaps(other.getBounds())) {
					entity.collided(other);
					other.collided(entity);
				}
			}
			others.remove(entity); // Collisions are already two way, don't need to be checked twice
		}
	
		for(Entity entity: deadList) {
			entity.remove();
		}
		
		deadList.clear();
		
	}
	
	/**
	 * Delay actor death to prevent concurrent modification
	 */
	public void kill(Entity entity) {
		deadList.add(entity);
	}
	
	
	@Override
	public void draw() {
		this.setViewport(getActors(Player.class).get(0).getViewport());
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
		if (actor instanceof Entity) {
			layer = ((Entity) actor).getLayer();
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
