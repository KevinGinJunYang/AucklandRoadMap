import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Node {

	private int nodeID;
	private double lat;
	private double lon;
	private Location loc;
	private boolean highlight;

	private ArrayList<Segment> inSegments;
	private ArrayList<Segment> outSegments;
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
		this.inSegments = new ArrayList<Segment>();
		this.outSegments = new ArrayList<Segment>();
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
	 * @return the inSegments
	 */
	public ArrayList<Segment> getInSegments() {
		return inSegments;
	}
	/**
	 * @param inSegments the inSegments to set
	 */
	public void setInSegments(ArrayList<Segment> inSegments) {
		this.inSegments = inSegments;
	}
	/**
	 * @return the outSegments
	 */
	public ArrayList<Segment> getOutSegments() {
		return outSegments;
	}
	/**
	 * @param outSegments the outSegments to set
	 */
	public void setOutSegments(ArrayList<Segment> outSegments) {
		this.outSegments = outSegments;
	}


	public void addInSegments(Segment s) {
		this.inSegments.add(s);
	}

	public void addOutSegments(Segment s) {
		this.outSegments.add(s);
	}

	/**
	 * @return the highlight
	 */
	public boolean isHighlight() {
		return highlight;
	}
	/**
	 * @param highlight the highlight to set
	 */
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}

	public void draw(Graphics g, Location origin, double scale) {
		Point nodePoint = loc.asPoint(origin, scale);

		if(highlight == true) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.BLACK);

		}
		g.fillOval(nodePoint.x-2, nodePoint.y-2, 4, 4);
	}
}
