
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Node {

	private int nodeID;
	private double lat;
	private double lon;
	private Location loc;

	private ArrayList<Segment> segments;
	private ArrayList<Road> roads;
	/**
	 * @param nodeID
	 * @param lat
	 * @param lon
	 * @param loc
	 * @param inSegments
	 * @param outSegments
	 */
	public Node(int nodeID, double lat, double lon, Location loc) {
		super();
		this.nodeID = nodeID;
		this.lat = lat;
		this.lon = lon;
		this.loc = Location.newFromLatLon(lat, lon);
		this.setSegments(new ArrayList<Segment>());
		this.setRoads(new ArrayList<>());
	}
	/**
	 * @return the nodeID
	 */
	public int getNodeID() {
		return nodeID;
	}
	/**
	 * @param nodeID the nodeID to set
	 */
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}
	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}
	/**
	 * @return the lon
	 */
	public double getLon() {
		return lon;
	}
	/**
	 * @param lon the lon to set
	 */
	public void setLon(double lon) {
		this.lon = lon;
	}
	/**
	 * @return the loc
	 */
	public Location getLoc() {
		return loc;
	}
	/**
	 * @param loc the loc to set
	 */
	public void setLoc(Location loc) {
		this.loc = loc;
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
		Point nodePoint = loc.asPoint(origin, scale);

		g.fillOval(nodePoint.x-2, nodePoint.y-2, 4, 4);
	}
	/**
	 * @param segment the segment to get
	 */
	public ArrayList<Segment> getSegments() {
		return segments;
	}
	/**
	 * @param segment the segment to set
	 */
	public void setSegments(ArrayList<Segment> segments) {
		this.segments = segments;
	}

	public void addSegment(Segment s) {
		this.segments.add(s);
	}
	/**
	 * @return the roads
	 */
	public ArrayList<Road> getRoads() {
		return roads;
	}
	/**
	 * @param roads the roads to set
	 */
	public void setRoads(ArrayList<Road> roads) {
		this.roads = roads;
	}
	
	/**
	 * @param add road to roads
	 */
	public void addRoads(Road r) {
		this.roads.add(r);
	}

}
