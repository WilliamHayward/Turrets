package thisisxanderh.turrets.terrain;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Rectangle;

public class Terrain extends Actor implements Shape2D {
	private List<Tile> tiles;
	
	public Terrain(MapLayer map, String property, String value) {
		//tiles = new ArrayList<>();
		TiledMapTileLayer layer = (TiledMapTileLayer) map;
		TileMap tiles = new TileMap(layer.getWidth(), layer.getHeight());
		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = 0; y < layer.getHeight(); y++) {
				tiles.set(x, y, false);
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
                    //addTile(x, y);
					tiles.set(x, y, true);
                    cell.setTile(null);
                }
			}
		}
		this.tiles = optimiseTiles(tiles);
	}
	
	private List<Tile> optimiseTiles(TileMap map) {
		List<Tile> tiles = new ArrayList<>();
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				boolean cell = map.get(x, y);
				if (!cell) {
					continue;
				}
				Tile tile = makeTile(map, x, y);
				tiles.add(tile);
			}
		}
		return tiles;
	}
	
	private Tile makeTile(TileMap map, int startingX, int startingY) {
		boolean right;
		boolean down;
		int x = startingX;
		int y = startingY;
		Tile tile = new Tile(x, y);
		do {
			if (!map.get(x, y)) {
				break;
			}
			map.set(x, y, false);
			right = false;
			if (x < map.getWidth()) {
				right = map.get(x + 1, y);
			}
			
			down = false;
			if (y < map.getHeight()) {
				down = map.get(x, y + 1);
			}
			
			if (right && down) {
				if (!map.get(x + 1, y + 1)) {
					down = false;
				}
			}
			
			if (right) {
				tile.growRight();
			}
			
			if (down) {
				tile.growDown();
			}
			
			if (!right && down) {
				x = startingX;
				y++;
			} else if (right){
				x++;
			}
		} while (right || down);
		return tile;
	}
	
	public void addTile(int x, int y) {
		addTile(new Tile(x, y));
	}
	
	public void addTile(Tile tile) {
		tiles.add(tile);
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		for (Tile tile: tiles) {
			tile.draw(batch, alpha);
		}
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
