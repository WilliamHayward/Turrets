package thisisxanderh.turrets.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import thisisxanderh.turrets.terrain.Terrain;

public class GameStage extends Stage {
	
	private TiledMap map;
	private TiledMapRenderer mapRenderer;
	private Terrain terrain;
	private List<Coordinate> spawns = new ArrayList<>();
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
