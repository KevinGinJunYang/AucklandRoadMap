import java.util.ArrayList;
import java.util.List;

public class Segment {
	
	private int road;
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
	public Segment(int road, double length, Node nodeID1, Node nodeID2, ArrayList<Location> coords) {
		this.road = road;
		this.length = length;
		this.nodeID1 = nodeID1;
		this.nodeID2 = nodeID2;
		this.coords = coords;
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

}
