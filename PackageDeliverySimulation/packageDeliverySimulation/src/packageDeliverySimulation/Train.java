package packageDeliverySimulation;


/**
 * Class Train - holds information for each train,
 * @author OwenHill
 *
 */

public class Train extends Event {
	
	private double arriveTime;
	private double endTime;
	private Boolean isBlocking;
	
	public Train(double arriveTime, double endTime) {//default constructor
		this.arriveTime = arriveTime;
		this.endTime = endTime;
		super.setTime(arriveTime);
		isBlocking = true;
	}
	//setters and getters
	public double getArriveTime() {
		return arriveTime;
	}
	
	public double getEndTime() {
		return endTime;
	}
	
	
	public Boolean getIsBlocking() {
		return isBlocking;
	}
	
	
	public void setNotBlocking() {
		isBlocking = false;
		super.setTime(endTime);
	}


}
