import java.util.ArrayList;

public class Road {

	private int roadID;
	private int type;
	private String label;
	private String city;
	private Boolean oneWay;
	private int speed;
	private int roadClass;
	private Boolean notForCar;
	private Boolean notForPede;
	private Boolean notForBicy;
	private ArrayList<Segment> segments;
	/**
	 * @param roadID
	 * @param type
	 * @param label
	 * @param city
	 * @param oneWay
	 * @param speed
	 * @param roadClass
	 * @param notForCar
	 * @param notForPede
	 * @param notForBicy
	 * @param segments
	 */
	public Road(int roadID, int type, String label, String city, Boolean oneWay, int speed, int roadClass,
			Boolean notForCar, Boolean notForPede, Boolean notForBicy) {
		super();
		this.roadID = roadID;
		this.type = type;
		this.label = label;
		this.city = city;
		this.oneWay = oneWay;
		this.speed = speed;
		this.roadClass = roadClass;
		this.notForCar = notForCar;
		this.notForPede = notForPede;
		this.notForBicy = notForBicy;
		this.segments = new ArrayList<Segment>();
	}
	/**
	 * @return the roadID
	 */
	public int getRoadID() {
		return roadID;
	}
	/**
	 * @param roadID the roadID to set
	 */
	public void setRoadID(int roadID) {
		this.roadID = roadID;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the oneWay
	 */
	public Boolean getOneWay() {
		return oneWay;
	}
	/**
	 * @param oneWay the oneWay to set
	 */
	public void setOneWay(Boolean oneWay) {
		this.oneWay = oneWay;
	}
	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	/**
	 * @return the roadClass
	 */
	public int getRoadClass() {
		return roadClass;
	}
	/**
	 * @param roadClass the roadClass to set
	 */
	public void setRoadClass(int roadClass) {
		this.roadClass = roadClass;
	}
	/**
	 * @return the notForCar
	 */
	public Boolean getNotForCar() {
		return notForCar;
	}
	/**
	 * @param notForCar the notForCar to set
	 */
	public void setNotForCar(Boolean notForCar) {
		this.notForCar = notForCar;
	}
	/**
	 * @return the notForPede
	 */
	public Boolean getNotForPede() {
		return notForPede;
	}
	/**
	 * @param notForPede the notForPede to set
	 */
	public void setNotForPede(Boolean notForPede) {
		this.notForPede = notForPede;
	}
	/**
	 * @return the notForBicy
	 */
	public Boolean getNotForBicy() {
		return notForBicy;
	}
	/**
	 * @param notForBicy the notForBicy to set
	 */
	public void setNotForBicy(Boolean notForBicy) {
		this.notForBicy = notForBicy;
	}
	/**
	 * @return the segments
	 */
	public ArrayList<Segment> getSegments() {
		return segments;
	}
	/**
	 * @param segments the segments to set
	 */
	public void setSegments(ArrayList<Segment> segments) {
		this.segments = segments;
	}
	
	public void addRoadSegment(Segment s) {
		this.segments.add(s);
	}
}
