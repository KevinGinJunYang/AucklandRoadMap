
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AucklandMain extends GUI{

	private Map<Integer, Node> nodePoints = new HashMap<>();
	private Map<Integer, Road> roadPoints = new HashMap<>();
	private Map<Integer, Segment> segmentPoints = new HashMap<>();
	private List<Segment> segementList = new ArrayList<Segment>();
	
	private double centerLat = -36.847622;
	private double centerLon = 174.763444;
	private double scale = 111.0;
	private Location origin = Location.newFromLatLon(centerLat,centerLon);



	public static void main(String[] args) {
		new AucklandMain();
	}



	@Override
	protected void redraw(Graphics g) { //FIXME NEED TO MOVE TO NODE AND ROAD CLASSES
		//draw nodes
		for(Node n : nodePoints.values()) {
			Point nodePoint = n.getLoc().asPoint(origin, scale);
			g.drawOval(nodePoint.x-2, nodePoint.y-2, 4, 4);
		}

		//draw edges
		for(Road r : roadPoints.values()) {
			for(Segment s: r.getSegments()){ 
				for(int i = 0; i < s.getCoords().size()-1; i++){
					Point startNode = s.getCoords().get(i).asPoint(origin, scale); 
					Point endNode = s.getCoords().get(i+1).asPoint(origin, scale); 

					g.drawLine(startNode.x, startNode.y, endNode.x, endNode.y); 
				}
			}
		}
	}



	@Override
	protected void onClick(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onSearch() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMove(Move m) {
		double zoom = 1.1;
		
		switch(m) {
		case NORTH: this.origin = new Location(origin.x, origin.y + 1);
		redraw();
		break;
		case EAST: this.origin = new Location(origin.x + 1, origin.y);
		redraw();
		break;
		case SOUTH: this.origin = new Location(origin.x, origin.y - 1);
		redraw();
		break;
		case WEST: this.origin = new Location(origin.x - 1, origin.y);
		redraw();		
		break;
		case ZOOM_IN: this.scale *= zoom;
		break;
		case ZOOM_OUT: this.scale /= zoom;
		break;


		}

	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		
		//--------------------------------LOADROADS----------------------------------------------

		try {
			File loadRoads = roads;
			BufferedReader roadReader = new BufferedReader(new FileReader(loadRoads));
			roadReader.readLine();
			String reader;

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
			}
			roadReader.close();

		}catch(Exception e) {
			e.printStackTrace();
			getTextOutputArea().setText("example doesn't load any files.");

		}


		//--------------------------------LOADNODES----------------------------------------------

		try {
			File loadNode = nodes;
			BufferedReader nodeReader = new BufferedReader(new FileReader(loadNode));
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
			getTextOutputArea().setText("example doesn't load any files.");
		}



		//------------------------------LOADSEGMENTS------------------------------------------------

		try{
			File segmentsFile = segments;
			BufferedReader segmentsFileReader = new BufferedReader(new FileReader(segmentsFile));
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
				Segment newSegment = new Segment(ID,segmentLength, node1, node2, loc);
				node1.addInSegments(newSegment);
				node2.addOutSegments(newSegment);
				road.addRoadSegment(newSegment);
				segmentPoints.put(ID,newSegment); 
				segementList.add(newSegment);
			}
			segmentsFileReader.close();
			
		}
		catch(IOException e){
			System.out.print("caught exception: " + e);
		}

	}


}