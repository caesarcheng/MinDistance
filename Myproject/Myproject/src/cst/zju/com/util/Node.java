package cst.zju.com.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Node {
	private String name;
	private Degree degree;
	private Distance distance;
	public Node(String name) {
		super();
		this.name=name;
		this.degree=new Degree();
		this.distance=new Distance(this);
	}
	public void initialNode(){
		this.distance.setDistanceLong(0);
		this.distance.setName(null);
		this.distance.getDistanceList().clear();
		this.distance.setDistanceList(this);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Degree getDegree() {
		return degree;
	}
	public void linkNode(Node node) {
		this.degree.setNode(node);
	}
	public Distance getDistance() {
		return distance;
	}
	public void setDistance(Distance distance) {
		if(this.distance.getDistanceLong()==distance.getDistanceLong()){
			this.distance.getDistanceList().addAll(distance.getDistanceList());
		}else if(this.distance.getDistanceLong()>distance.getDistanceLong()){
			for (HashSet<String> nodeSet : distance.getDistanceList()) {
				nodeSet.add(this.getName());
			}
			this.distance=distance;
		}
	}
	public void setDegree(Node node) {
		this.degree.setNode(node);
	}
	
}
