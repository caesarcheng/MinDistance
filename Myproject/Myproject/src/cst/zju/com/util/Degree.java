package cst.zju.com.util;

import java.util.LinkedList;

public class Degree {
	private LinkedList<Node> node;
	private int count;
	public Degree() {
		super();
		this.node=new LinkedList<Node>();
		this.count=0;
	}
	public LinkedList<Node> getNode() {
		return node;
	} 
	public void setNode(Node addnode) {
		this.node.add(addnode);
		this.setCount(this.getCount()+1);
	}
	public  void removeNode(Node removenode ){
//		this.node.remove(removenode);
		this.setCount(this.getCount()-1);
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
