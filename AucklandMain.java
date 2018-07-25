
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
	protected void redraw(Graphics g) {
		//draw nodes
		for(Node n : nodePoints.values()) {
			n.draw(g, origin, scale);
		}

		//draw edges
		for(Road r : roadPoints.values()) {
			for(Segment s: r.getSegments()){
				s.draw(g, origin, scale);
				}
			}

	}



	@Override
	protected void onClick(MouseEvent e) {
		// TODO Auto-generated method stub
		for(Node n : nodePoints.values()) {
			if(e.getX() <= n.getLoc().asPoint(origin, scale).x && e.getY() <= n.getLoc().asPoint(origin, scale).y) {
				n.setHighlight(true);
			}
		}

	}

	@Override
	protected void onSearch() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMove(Move m) {
		//FIXME : Change dependent on scale of width and height
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

				//https://stackoverflow.com/questions/303913/java-reading-integers-from-a-file-into-an-array
				//Used to find how to parse to array
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
		catch(Exception e){
			e.printStackTrace();
			getTextOutputArea().setText("Error loading Segment File ");
		}

	}


}
