package cst.zju.com.util;

import java.util.ArrayList;
import java.util.HashSet;

public class Distance {
	private int distanceLong;
	private String name;
	private ArrayList<HashSet<String>> distanceList;
	public Distance(int distanceLong, String name, ArrayList<HashSet<String>> distanceList) {
		super();
		this.distanceLong = distanceLong;
		this.name = name;
		this.distanceList =distanceList;
	}
	public Distance(String  name){
		this.distanceLong=0;
		this.name=name;
		this.distanceList= new ArrayList<HashSet<String>>();
	}
	public Distance(){
		this.distanceLong=0;
		this.name=null;
		this.distanceList= new ArrayList<HashSet<String>>();
	}
	public Distance( Node node){
		super();
		this.distanceLong=0;
		this.name=node.getName();
		this.distanceList= new ArrayList<HashSet<String>>();
		this.setDistanceList(node);;
	}
	public int getDistanceLong() {
		return distanceLong;
	}

	public void setDistanceLong(int distanceLong) {
		this.distanceLong = distanceLong;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<HashSet<String>> getDistanceList() {
		return distanceList;
	}

	public void setDistanceList(Node node) {
		if (this.distanceList.isEmpty()) {
			HashSet<String> nodeSet = new HashSet<String>();
			nodeSet.add(node.getName());
			distanceList.add(nodeSet);
		}else { 
			for (HashSet<String> hashSet : node.getDistance().getDistanceList()) {
				for (HashSet<String> set : this.distanceList) {
					set.addAll(hashSet);
				}
				//this.distanceList.add(hashSet);
			}
			this.setDistanceLong(this.distanceList.get(0).size()-1);
		}
	}
	public void addDistance(HashSet<String> hashset){
		this.distanceList.add(hashset);
		this.setDistanceLong(hashset.size()-1);
	}

}
