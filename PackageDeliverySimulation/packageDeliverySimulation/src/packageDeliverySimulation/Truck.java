package packageDeliverySimulation;

public class Truck extends Event{
	
	private double totalTime = 0.0;
	private String event;
	private int truckNum;
	private double startTime;
	
	
	public Truck(int truckNum, double startTime) { //constructor
		this.truckNum = truckNum;
		this.startTime = startTime;
		event = "beginning";
		super.setTime(startTime);
	}
	
	//setters and getters
	public int getTruckNum() { 
		return truckNum;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getEvent() {
		return event;
	}

	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime - startTime;
	}
	
	public double getTotalTime() {
		return totalTime;
	}
	

}
