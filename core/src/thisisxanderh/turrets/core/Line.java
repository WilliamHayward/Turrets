package thisisxanderh.turrets.core;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import thisisxanderh.turrets.terrain.Terrain;
import thisisxanderh.turrets.terrain.Tile;

public class Line {
	private Vector2 start;
	private Vector2 end;
	
	public Line(Vector2 start, Vector2 end) {
		if (start.x < end.x) {
			this.start = start;
			this.end = end;
		} else {
			this.start = end;
			this.end = start;
		}
	}
	
	public Line(float x1, float y1, float x2, float y2) {
		if (x1 < x2) {
			start = new Vector2(x1, y1);
			end = new Vector2(x2, y2);
		} else {
			start = new Vector2(x2, y2);
			end = new Vector2(x1, y1);
		}
	}
	
	public Vector2 getStart() {
		return start;
	}
	
	public Vector2 getEnd() {
		return end;
	}
	
	private List<Line> getLines(Rectangle rect) {
		List<Line> lines = new ArrayList<>();
		float left = rect.getX();
		float right = rect.getX() + rect.getWidth();
		float top = rect.getY() + rect.getHeight();
		float bottom = rect.getY();
		Line line;
		
		// Top
		line = new Line(left, top, right, top);
		lines.add(line);
		// Bottom
		line = new Line(left, bottom, right, bottom);
		lines.add(line);
		// Left
		line = new Line(left, top, left, bottom);
		lines.add(line);
		// Right
		line = new Line(right, top, right, bottom);
		lines.add(line);
		
		return lines;
	}
	
	public boolean intersects(Line line) {
		return intersectsAt(line) != null;
	}
	
	public boolean intersects(Rectangle rect) {
		float left = rect.getX();
		float right = rect.getX() + rect.getWidth();
		float top = rect.getY() + rect.getHeight();
		float bottom = rect.getY();
		
		boolean leftValid = start.x < left && end.x < left;
		boolean rightValid = start.x > right && end.x > right;
		boolean topValid = start.y > top && end.y > top;
		boolean bottomValid = start.y < bottom && end.y < bottom;
		
		boolean sidesValid = leftValid || rightValid;
		if (sidesValid || topValid || bottomValid) {
			for (Line edge: getLines(rect)) {
				if (intersects(edge)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean intersects(Terrain terrain) {
		for (Tile tile: terrain.getTiles()) {
			if (this.intersects(tile)) {
				return true;
			}
		}
		return false;
	}
	
	// Taken from http://stackoverflow.com/a/1968345
	public Vector2 intersectsAt(Line line) {
		float Ax1 = start.x;
		float Ax2 = end.x;
		float Ay1 = start.y;
		float Ay2 = end.y;
		float Bx1 = line.getStart().x;
		float Bx2 = line.getEnd().x;
		float By1 = line.getStart().y;
		float By2 = line.getEnd().y;

		float s1_x, s1_y, s2_x, s2_y;
	    s1_x = Ax2 - Ax1;     
	    s1_y = Ay2 - Ay1;
	    s2_x = Bx2 - Bx1;
	    s2_y = By2 - By1;

	    float s, t;
	    s = (-s1_y * (Ax1 - Bx1) + s1_x * (Ay1 - By1)) / (-s2_x * s1_y + s1_x * s2_y);
	    t = ( s2_x * (Ay1 - By1) - s2_y * (Ax1 - Bx1)) / (-s2_x * s1_y + s1_x * s2_y);

	    if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
	    	float xInt = Ax1 + (t * s1_x);
	    	float yInt = Ay1 + (t * s1_y);
	    	return new Vector2(xInt, yInt);
	        
	    }

		return null;
	}
	
	public Vector2 intersectsAt(Rectangle rect) {
		if (this.intersects(rect)) {
			for (Line edge: getLines(rect)) {
				return intersectsAt(edge);
			}
		}
		return null;
	}
	
	public Vector2 intersectsAt(Terrain terrain) {
		for (Tile tile: terrain.getTiles()) {
			if (this.intersects(tile)) {
				return this.intersectsAt(tile);
			}
		}
		return null;
	}
}
