
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;




public class AucklandMain extends GUI{

	private Map<Integer, Node> nodePoints = new HashMap<>();
	private Map<Integer, Road> roadPoints = new HashMap<>();
	private Map<Integer, Segment> segmentPoints = new HashMap<>();
	private Set<String> roadNames = new HashSet<>();
	private Set<String> roadclickNames = new HashSet<>();
	private List<Road> trieRoads = new ArrayList<>();
	private List<Node> selectedNode = new ArrayList<>();
	private List<Segment> selectedSegment = new ArrayList<>();
	private TrieNode trieNode = new TrieNode();

	private double centerLat = -36.847622;
	private double centerLon = 174.763444;
	private double scale = 111.0;
	private Location origin = Location.newFromLatLon(centerLat,centerLon);
	private int pointX;
	private int pointY;
	private int scroll;



	public static void main(String[] args) {
		new AucklandMain();
	}



	@Override
	protected void redraw(Graphics g) {
		//draw nodes
		g.setColor(Color.BLACK);
		for(Node n : nodePoints.values()) {
			n.draw(g, origin, scale);
		}

		//draw edges
		for(Road r : roadPoints.values()) {
			r.draw(g, origin, scale);

		}
		
		g.setColor(Color.BLUE);
		for(Road r: trieRoads) {
			r.draw(g, origin, scale);
		}
		
	
		g.setColor(Color.RED);
		for(Node n : selectedNode) {
			n.draw(g, origin, scale);
		}

		
		g.setColor(Color.BLUE);
		for(Segment s : selectedSegment) {
			s.draw(g, origin, scale);
		}
	}



	@Override
	protected void onClick(MouseEvent e) {
		
		//clears all the previous clicked values 
		roadclickNames.clear();
		selectedNode.clear();
		selectedSegment.clear();
		trieRoads.clear();
		roadNames.clear();

		
		//gets closest node from point 
		Node selected = findNeighborNode(e.getPoint(), origin, scale);

		if(selectedNode.contains(selected)) {
			selectedNode.clear();
		}
		//add closest node to list 
		selectedNode.add(selected);

		if(selected!=null) {
			for(Segment seg : selected.getSegments()) {
				// stores the selctedSegments and the roadNames
				selectedSegment.add(seg);
				roadclickNames.add(seg.getRoadName());

				int nodeID = selected.getNodeID();
				double lat = selected.getLat();
				double lon = selected.getLon();
				String roadName = roadclickNames.toString().replace("[","").replace("]","");
				// prints ID, Latitude, Longitude and roadSegments 
				getTextOutputArea().setText("Node ID : " + nodeID + "\nLatitude : " + lat + "   Longitude : " + lon + "\nRoads connected to intersection : \n" + roadName);


			}
		}

	}

	/**
	 * Finds the closest node from point of click on the screen 
	 * 
	 * @param p
	 * @param origin
	 * @param scale
	 * @return
	 */
	private Node findNeighborNode(Point p, Location origin, double scale) {
		//https://stackoverflow.com/questions/35877681/scalafx-javafx-8-get-nearest-nodes
		Location selected = Location.newFromPoint(p, origin, scale); // gets location of click
		Node endingNode = null;
		double distance = Double.POSITIVE_INFINITY; // sets so that  distance is huge and no node is found 
		for(Node n : nodePoints.values()) {
			double nodeDistance = selected.distance(n.getLoc()); // gets distance between click and node 
			if(nodeDistance < distance) { // keeps replacing distance to the distance of closest node to click 
				distance  = nodeDistance;
				endingNode = n;
			}
		}
		return endingNode; 
	}



	@Override
	protected void onSearch() {
		// clears the previous search values 
		trieRoads.clear();
		roadNames.clear();
		selectedNode.clear();
		selectedSegment.clear();
		
		//gets the input of user
		String input = getSearchBox().getText();
		if(input == null || input == "") {
			trieRoads.clear();
		}

		TrieNode node = trieNode.get(input);
		// checks if location is valid 
		if(node == null) {
			getTextOutputArea().setText("No location found with \" " + input + " \" ");
		}else {
			//gets all the children in the prefix
			trieRoads = node.getAll();
			for (Road road : trieRoads) {
				//stores all the roads 
				if (!roadNames.contains(road.getLabel())) {
					roadNames.add(road.getLabel());
				}
			}
			getTextOutputArea().setText(roadNames.toString().replace("[","").replace("]",""));
		}

		// if exactly same then print details 
		for(Road r : roadPoints.values()) {
			for(Segment s : r.getSegments()) {
				if(input.equalsIgnoreCase(s.getRoadName())) {
					getTextOutputArea().setText("Road ID : " + r.getRoadID() + "\nLatitude : " + s.getNodeID1().getLat()
							+ "   Longitude : " + s.getNodeID2().getLon() + "\nLocation : \n" + s.getRoadName());
				}
			}
		}


	}


	@Override
	protected void onMove(Move m) {
		//FIXME : Change dependent on scale of width and height
		double zoom = 1.1;

		switch(m) {
		case NORTH: this.origin = new Location(origin.x, origin.y + 1);
		break;
		case EAST: this.origin = new Location(origin.x + 1, origin.y);
		break;
		case SOUTH: this.origin = new Location(origin.x, origin.y - 1);		
		break;
		case WEST: this.origin = new Location(origin.x - 1, origin.y);
		break;
		case ZOOM_IN: this.scale *= zoom;
		break;
		case ZOOM_OUT: this.scale /= zoom;
		break;


		}

	}
	
	@Override
	protected void onPress(MouseEvent e){
		pointX = e.getX();
		pointY = e.getY();
	}
	
	@Override
	protected void onDrag(MouseEvent e){	
		double moveX = (pointX- e.getX())/scale;
		double moveY = (e.getY() - pointY)/scale;
		origin = origin.moveBy(moveX,moveY);
		//reset x and y 
		pointX = e.getX();
		pointY = e.getY();
		
	}
	
	@Override
	protected void onScroll(MouseWheelEvent e){
		scroll = e.getWheelRotation();
		if (scroll < 0){
			onMove(Move.ZOOM_IN);
		}else { 
			onMove(Move.ZOOM_OUT);
		}

	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {

		//--------------------------------LOADROADS----------------------------------------------

		try {
			BufferedReader roadReader = new BufferedReader(new FileReader(roads));
			roadReader.readLine();
			String reader;
			// https://stackoverflow.com/questions/19575308/read-a-file-separated-by-tab-and-put-the-words-in-an-arraylist
			// used to find how to parse a tab file
			while ((reader = roadReader.readLine()) != null) {
				String[] dataRoad = reader.split("\t");
				int roadID = Integer.parseInt(dataRoad[0]);
				int type = Integer.parseInt(dataRoad[1]);
				String label = dataRoad[2];
				String city = dataRoad[3];
				boolean oneWay = Boolean.parseBoolean(dataRoad[4]);
				int speed = Integer.parseInt(dataRoad[5]);
				int roadClass = Integer.parseInt(dataRoad[6]);
				boolean notForCar = Boolean.parseBoolean(dataRoad[7]);
				boolean notForPede = Boolean.parseBoolean(dataRoad[8]);
				boolean notForBicy = Boolean.parseBoolean(dataRoad[9]);


				Road road = new Road(roadID, type, label, city, oneWay, speed, roadClass, notForCar, notForPede, notForBicy);
				roadPoints.put(roadID, road);
				trieNode.add(road);

			}
			roadReader.close();

		}catch(Exception e) {
			e.printStackTrace();
			getTextOutputArea().setText("Error Loading Roads File");

		}


		//--------------------------------LOADNODES----------------------------------------------

		try {
			BufferedReader nodeReader = new BufferedReader(new FileReader(nodes));
			String reader;

			while ((reader = nodeReader.readLine()) != null) {
				String[] dataNode = reader.split("\t");
				int nodeID = Integer.parseInt(dataNode[0]);
				double lat = Double.parseDouble(dataNode[1]);
				double lon = Double.parseDouble(dataNode[2]);
				Location locat = Location.newFromLatLon(lat, lon);

				Node node = new Node(nodeID, lat, lon, locat);
				nodePoints.put(nodeID, node);
			}
			nodeReader.close();

		}catch(Exception e) {
			e.printStackTrace();
			getTextOutputArea().setText("Error Loading Nodes File");
		}



		//------------------------------LOADSEGMENTS------------------------------------------------

		try{

			BufferedReader segmentsFileReader = new BufferedReader(new FileReader(segments));
			segmentsFileReader.readLine();
			for(String line = segmentsFileReader.readLine(); line != null; line = segmentsFileReader.readLine()){
				String [] dataSeg = line.split("\t");
				int ID = Integer.parseInt(dataSeg[0]);
				Road road = roadPoints.get(ID);
				double segmentLength = Double.parseDouble(dataSeg[1]);
				int nodeID1 = Integer.parseInt(dataSeg[2]);
				int nodeID2 = Integer.parseInt(dataSeg[3]);
				Node node1 = nodePoints.get(nodeID1);
				Node node2 = nodePoints.get(nodeID2);

				ArrayList<Location> loc = new ArrayList<Location>();
				for (int i = 4; i < dataSeg.length; i = i+2) {
					double lat = Double.parseDouble(dataSeg[i]);
					double lon = Double.parseDouble(dataSeg[i+1]);
					Location location = Location.newFromLatLon(lat, lon);
					loc.add(location);
				}
				Segment newSegment = new Segment(ID,segmentLength, node1, node2, loc, road);
				node1.addSegment(newSegment);
				node2.addSegment(newSegment);
				road.addRoadSegment(newSegment);
				segmentPoints.put(ID,newSegment);

			}
			segmentsFileReader.close();

		}
		catch(Exception e){
			e.printStackTrace();
			getTextOutputArea().setText("Error loading Segment File ");
		}

	}


}
