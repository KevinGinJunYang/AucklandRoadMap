
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Segment {

	private int road;
	private Road roads;
	private double length;
	private Node nodeID1;
	private Node nodeID2;
	private ArrayList<Location> coords;
	/**
	 * @param road
	 * @param length
	 * @param nodeID1
	 * @param nodeID2
	 * @param coords
	 */
	public Segment(int road, double length, Node nodeID1, Node nodeID2, ArrayList<Location> coords, Road r) {
		this.road = road;
		this.length = length;
		this.nodeID1 = nodeID1;
		this.nodeID2 = nodeID2;
		this.coords = coords;
		this.roads = r;
	}
	/**
	 * @return the road
	 */
	public int getRoad() {
		return road;
	}
	/**
	 * @param road the road to set
	 */
	public void setRoad(int road) {
		this.road = road;
	}
	/**
	 * @return the length
	 */
	public double getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(double length) {
		this.length = length;
	}
	/**
	 * @return the nodeID1
	 */
	public Node getNodeID1() {
		return nodeID1;
	}
	/**
	 * @param nodeID1 the nodeID1 to set
	 */
	public void setNodeID1(Node nodeID1) {
		this.nodeID1 = nodeID1;
	}
	/**
	 * @return the nodeID2
	 */
	public Node getNodeID2() {
		return nodeID2;
	}
	/**
	 * @param nodeID2 the nodeID2 to set
	 */
	public void setNodeID2(Node nodeID2) {
		this.nodeID2 = nodeID2;
	}
	/**
	 * @return the coords
	 */
	public List<Location> getCoords() {
		return coords;
	}
	/**
	 * @param coords the coords to set
	 */
	public void setCoords(ArrayList<Location> coords) {
		this.coords = coords;
	}

	/**
	 * draw function that takes location and double
	 * 
	 * @param g drawing function 
	 * @param origin Location 
	 * @param scale 
	 * 
	 */
	public void draw(Graphics g, Location origin, double scale) {

		for(int i = 0; i < getCoords().size()-1; i++){
			Point startNode = getCoords().get(i).asPoint(origin, scale);
			Point endNode = getCoords().get(i+1).asPoint(origin, scale);

			g.drawLine(startNode.x, startNode.y, endNode.x, endNode.y);

		}
	}
	
	/**
	 * @return roads
	 */
	public Road getRoads() {
		return roads;
	}
	
	/**
	 * @param roads to set roads
	 */
	public void setRoads(Road roads) {
		this.roads = roads;
	}
	
	/**
	 * @return road label
	 */
	public String getRoadName() {
		return roads.getLabel();
	}

}
