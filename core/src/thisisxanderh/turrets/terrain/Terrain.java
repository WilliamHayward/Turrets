package thisisxanderh.turrets.terrain;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class Terrain implements Shape2D {
	private List<Tile> tiles;
	
	public Terrain(MapLayer map, String property, String value) {
		tiles = new ArrayList<>();
		TiledMapTileLayer layer = (TiledMapTileLayer) map;
		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                if (cell == null) {
                	continue;
                }
                TiledMapTile tile = cell.getTile();
                if (tile == null) {
                	continue;
                }
                MapProperties props = tile.getProperties();
                String item = (String) props.get(property);
				
				if (item.equals(value)) {
                    addTile(x, y);
                    cell.setTile(null);
                }
			}
		}
		// TODO: Optimize rectangles
	}
	
	public void addTile(int x, int y) {
		addTile(new Tile(x, y));
	}
	
	public void addTile(Tile tile) {
		tiles.add(tile);
	}

	@Override
	public boolean contains(Vector2 point) {
		for (Tile tile: tiles) {
			if (tile.contains(point)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean contains(float x, float y) {
		for (Tile tile: tiles) {
			if (tile.contains(x, y)) {
				return true;
			}
		}
		return false;
	}

	public boolean overlaps(Rectangle other) {
		for (Tile tile: tiles) {
			if (tile.overlaps(other)) {
				return true;
			}
		}
		return false;
	}
	
	public Tile getOverlap(Rectangle other) {
		for (Tile tile: tiles) {
			if (tile.overlaps(other)) {
				return tile;
			}
		}
		return null;
	}
	
	public List<Tile> getTiles() {
		return tiles;
	}
}
