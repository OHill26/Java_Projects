package packageDeliverySimulation;

public class Event implements Comparable<Event> {
	private double time;
	private String event;
	
	public Event() {//Blank constructor
	}
	//getter
	public double getTime() {
		return time;
	}
	//setter
	public void setTime(double time) {
		this.time = time;
	}
	
	
	//sorts events based on time
	@Override
	public int compareTo(Event o) {
		if (this.time < o.getTime()) {
			return -1;
		}
		else if (this.time == o.getTime()) {
			return 0;
		}
		else {
			return 1;
		}
		
	}

	

}
