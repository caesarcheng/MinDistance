package cst.zju.com.util;

public class NetworkFeathurIndex {
	private double averagePathLength;
	private double averageClusteringCoefficient;
	private int networkCapacity;
	
	public NetworkFeathurIndex() {
		super();
		this.averageClusteringCoefficient=0;
		this.averagePathLength=0;
		this.networkCapacity=0;
	}
	public double getAveragePathLength() {
		return averagePathLength;
	}
	public void setAveragePathLength(double averagePathLength) {
		this.averagePathLength = averagePathLength;
	}
	public double getAverageClusteringCoefficient() {
		return averageClusteringCoefficient;
	}
	public void setAverageClusteringCoefficient(double averageClusteringCoefficient) {
		this.averageClusteringCoefficient = averageClusteringCoefficient;
	}
	public int getNetworkCapacity() {
		return networkCapacity;
	}
	public void setNetworkCapacity(int networkCapacity) {
		this.networkCapacity = networkCapacity;
	}
}
