package thisisxanderh.turrets.core;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;

import thisisxanderh.turrets.terrain.Terrain;
import thisisxanderh.turrets.terrain.Tile;

public class Line {
	private Coordinate start;
	private Coordinate end;
	
	public Line(Coordinate start, Coordinate end) {
		if (start.getX() < end.getX()) {
			this.start = start;
			this.end = end;
		} else {
			this.start = end;
			this.end = start;
		}
	}
	
	public Line(float x1, float y1, float x2, float y2) {
		if (x1 < x2) {
			start = new Coordinate(x1, y1);
			end = new Coordinate(x2, y2);
		} else {
			start = new Coordinate(x2, y2);
			end = new Coordinate(x1, y1);
		}
	}
	
	public Coordinate getStart() {
		return start;
	}
	
	public Coordinate getEnd() {
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
		
		boolean leftValid = start.getX() < left && end.getX() < left;
		boolean rightValid = start.getX() > right && end.getX() > right;
		boolean topValid = start.getY() > top && end.getY() > top;
		boolean bottomValid = start.getY() < bottom && end.getY() < bottom;
		
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
	public Coordinate intersectsAt(Line line) {
		float Ax1 = start.getX();
		float Ax2 = end.getX();
		float Ay1 = start.getY();
		float Ay2 = end.getY();
		float Bx1 = line.getStart().getX();
		float Bx2 = line.getEnd().getX();
		float By1 = line.getStart().getY();
		float By2 = line.getEnd().getY();

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
	    	return new Coordinate(xInt, yInt);
	        
	    }

		return null;
	}
	
	public Coordinate intersectsAt(Rectangle rect) {
		if (this.intersects(rect)) {
			for (Line edge: getLines(rect)) {
				return intersectsAt(edge);
			}
		}
		return null;
	}
	
	public Coordinate intersectsAt(Terrain terrain) {
		for (Tile tile: terrain.getTiles()) {
			if (this.intersects(tile)) {
				return this.intersectsAt(tile);
			}
		}
		return null;
	}
}
