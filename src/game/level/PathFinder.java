package game.level;

import java.util.ArrayList;

import game.math.Point2D;

public class PathFinder {
	
	private ArrayList<WalkingPath> paths = new ArrayList<WalkingPath>();
	private int nextPath = 0;
	
	/**
	 * recursivly find paths throught a map starting at x0, y0
	 */
	public PathFinder(int x0, int y0, Map map) {
		// check if there is tile to the left of x0
		if (x0 > 1) {
			char leftTile = map.getTile(x0 - 1, y0);
			// check if moving onto tile is considered a valid move
			if (leftTile == Map.HORIZONTALPATH || leftTile == map.CORNER2 || leftTile == map.CORNER4) {
				//create new path runner
				paths.add(new WalkingPath());
				move(x0, y0, -1, 0, paths.size() - 1, map);
			}
		}
		// check if there is tile to the right of x0
		if (x0 < map.getWidth() - 2) {
			char rightTile = map.getTile(x0 + 1, y0);
			// check if moving onto tile is considered a valid move
			if (rightTile == Map.HORIZONTALPATH || rightTile == map.CORNER1 || rightTile == map.CORNER3) {
				//create new path runner
				paths.add(new WalkingPath());
				move(x0, y0, 1, 0, paths.size() - 1, map);
			}
		}
		// check if there is tile to above of y0
		if (y0 > 1) {
			char upTile = map.getTile(x0, y0 - 1);
			// check if moving onto tile is considered a valid move
			if (upTile == Map.VERTICALPATH || upTile == map.CORNER1 || upTile == map.CORNER2) {
				//create new path runner
				paths.add(new WalkingPath());
				move(x0, y0, 0, -1, paths.size() - 1, map);
			}
		}
		// check if there is tile below y0
		if (y0 < map.getHeight() -2) {
			char downTile = map.getTile(x0, y0 + 1);
			// check if moving onto tile is considered a valid move
			if (downTile == Map.VERTICALPATH || downTile == map.CORNER3 || downTile == map.CORNER4) {
				//create new path runner
				paths.add(new WalkingPath());
				move(x0, y0, 0, 1, paths.size() - 1, map);
			}
		}
	}
	
	/**
	 * @return next path for entities to traverse
	 */
	public WalkingPath nextPath() {
		WalkingPath path = paths.get(nextPath++);
		if (nextPath >= paths.size()) {
			nextPath = 0;
		}
		return path;
	}
	
	/**
	 * Movement consist of five stages
	 * <ol>
	 * 	<li>Move point and get current tile</li>
	 * 	<li>Check collision with straight paths / junctions (go straight)</li>
	 * 	<li>Check collision with corners (turn)</li>
	 * 	<li>Check collision with splitters (create one new path)</li>
	 * 	<li>End path (stop further movement)</li>
	 * </ol>
	 */
	private void move(int x, int y, int vx, int vy, int id, Map map) {
		// TODO add exceptions for bad map design
		addPathPoint(id, x, y);
		int nx = x + vx;
		int ny = y + vy;
		char tile = map.getTile(nx, ny);
		// moving vertical
		if (vx == 0) {
			collideVertical(tile, nx, ny, vx, vy, id, map);
			return;
		}else {
			collideHorizontal(tile, nx, ny, vx, vy, id, map);
			return;
		}
	}
	
	private void collideHorizontal(char tile, int x, int y, int vx, int vy, int id, Map map) {
		if (tile == Map.HORIZONTALPATH || tile == Map.JUNCTION) {
			// continue in the same direction
			move(x, y, vx, vy, id, map);
			return;
		}
		collideCorner(tile, x, y, vx, vy, id, map);
	}

	private void collideVertical(char tile, int x, int y, int vx, int vy, int id, Map map) {
		if (tile == Map.VERTICALPATH || tile == Map.JUNCTION) {
			// continue in the same direction
			move(x, y, vx, vy, id, map);
			return;
		}
		collideCorner(tile, x, y, vx, vy, id, map);
	}
	
	// this class assume that the map is made correctly meaning that no path goes into a corner the wrong way
	private void collideCorner(char tile, int x, int y, int vx, int vy, int id, Map map) {
		if (tile == Map.CORNER1) {
			/*input
			 * 1. - right
			 * vx = 1
			 * vy = 0
			 * 
			 * 2. - up
			 * vx = 0
			 * vy = -1
			 */
			move(x, y, vy, vx, id, map);
			/*output
			 * 1. - down
			 * vx = 0
			 * vy = 1
			 * 
			 * 2. - left
			 * vx = -1
			 * vy = 0
			 */
			return;
		}
		if (tile == Map.CORNER2) {
			/*input
			 * 1. - left
			 * vx = -1
			 * vy = 0
			 * 
			 * 2. - up
			 * vx = 0
			 * vy = -1
			 */
			move(x, y, -vy, -vx, id, map);
			/*output
			 * 1. - down
			 * vx = 0
			 * vy = 1
			 * 
			 * 2. - right
			 * vx = 1
			 * vy = 0
			 */
			return;
		}
		if (tile == Map.CORNER3) {
			/*input
			 * 1. - right
			 * vx = 1
			 * vy = 0
			 * 
			 * 2. - down
			 * vx = 0
			 * vy = 1
			 */
			move(x, y, -vy, -vx, id, map);
			/*output
			 * 1. - up
			 * vx = 0
			 * vy = -1
			 * 
			 * 2. - left
			 * vx = -1
			 * vy = 0
			 */
			return;
		}
		if (tile == Map.CORNER4) {
			/*input
			 * 1. - left
			 * vx = -1
			 * vy = 0
			 * 
			 * 2. - down
			 * vx = 0
			 * vy = 1
			 */
			move(x, y, vy, vx, id, map);
			/*output
			 * 1. - up
			 * vx = 0
			 * vy = -1
			 * 
			 * 2. - right
			 * vx = 1
			 * vy = 0
			 */
			return;
		}
		collideSplitter(tile, x, y, vx, vy, id, map);
	}

	private void collideSplitter(char tile, int x, int y, int vx, int vy, int id, Map map) {
		if (tile == Map.SPLITTER) {
			
			paths.add(paths.get(id).clone());
			/*
			 * 1. - up
			 * vx = 0
			 * vy = -1
			 * 
			 * 2. - right
			 * vx = 1
			 * vy = 0
			 * 
			 * 1. - left
			 * vx = -1
			 * vy = 0
			 * 
			 * 2. - down
			 * vx = 0
			 * vy = 1
			 */
			move(x, y, -vy, -vx, paths.size() - 1, map);
			/*
			 * 1. - right
			 * vx = 1
			 * vy = 0
			 * 
			 * 2. - up
			 * vx = 0
			 * vy = -1
			 * 
			 * 1. - down
			 * vx = 0
			 * vy = 1
			 * 
			 * 2. - left
			 * vx = -1
			 * vy = 0
			 */
			move(x, y, vy, vx, id, map);
			/*
			 * 1. - left
			 * vx = -1
			 * vy = 0
			 * 
			 * 2. - down
			 * vx = 0
			 * vy = 1
			 * 
			 * 1. - up
			 * vx = 0
			 * vy = -1
			 * 
			 * 2. - right
			 * vx = 1
			 * vy = 0
			 */
			return;
		}
		
		// end of path
		addPathPoint(id, x, y);
	}

	private void addPathPoint(int id, int x, int y) {
		Point2D p = new Point2D(x, y);
		paths.get(id).addPoint(p);
	}
}
