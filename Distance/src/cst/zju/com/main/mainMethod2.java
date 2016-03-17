package cst.zju.com.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import cst.zju.com.util.Distance;
import cst.zju.com.util.LineChart;
import cst.zju.com.util.NetworkFeathurIndex;
import cst.zju.com.util.Node;

public class mainMethod2 {
	public static int limit = 1;
	public static int coefficient = 1000;
	public static String url = "data/dataForMicro2.txt";
	public static Map<String, Integer> NodePairCount = new HashMap<String, Integer>();

	public static void main(String[] args) throws IOException {

		Map<String, ArrayList<NetworkFeathurIndex>> networkFeathurIndexsMap = networkIndex();
		int length = networkFeathurIndexsMap.get("initialDegree").size(), cols = 0;
		;
		double[][] data0 = new double[4][length];
		double[][] data1 = new double[4][length];
		double[][] data2 = new double[4][length];
		Iterator<Map.Entry<String, ArrayList<NetworkFeathurIndex>>> iterator = networkFeathurIndexsMap.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, ArrayList<NetworkFeathurIndex>> networkFeathur = iterator.next();
			String keyString = networkFeathur.getKey();
			ArrayList<NetworkFeathurIndex> networkFeathurIndex = networkFeathur.getValue();
			if (keyString.equals("initialDegree")) {
				cols = 0;
			} else if (keyString.equals("initialBetweenness")) {
				cols = 1;
			} else if (keyString.equals("recalculatedDegree")) {
				cols = 2;
			} else {
				cols = 3;
			}
			Iterator<NetworkFeathurIndex> networkFeathurIndexIterator = networkFeathurIndex.iterator();
			int Ycols = 0;
			while (networkFeathurIndexIterator.hasNext()) {
				NetworkFeathurIndex index = networkFeathurIndexIterator.next();
				data0[cols][Ycols] = index.getAveragePathLength();
				data1[cols][Ycols] = index.getAverageClusteringCoefficient();
				data2[cols][Ycols++] = index.getNetworkCapacity();
			}
		}
		String[] rowKeys = { "initialDegree", "initialBetweenness", "recalculatedDegree", "recalculatedBetweenness" };
		String[] colKeys = new String[length];
		for (int i = 1; i <= length; i++) {

			String str = "" + i;
			colKeys[i - 1] = str;
		}
		CategoryDataset categoryDataset0 = LineChart.createDataset(rowKeys, colKeys, data0);
		CategoryDataset categoryDataset1 = LineChart.createDataset(rowKeys, colKeys, data1);
		CategoryDataset categoryDataset2 = LineChart.createDataset(rowKeys, colKeys, data2);

		JFreeChart chartline0 = LineChart.createLineChart("模拟网络客户流失网络参数", "删除节点所占个数（X1000）", "AveragePathLength",
				categoryDataset0);
		JFreeChart chartline1 = LineChart.createLineChart("模拟网络客户流失网络参数", "删除节点所占个数（X1000）",
				"AverageClusteringCoefficient", categoryDataset1);
		JFreeChart chartline2 = LineChart.createLineChart("模拟网络客户流失网络参数", "删除节点所占个数（X1000）", "NetworkCapacity",
				categoryDataset2);
		ChartFrame frame0 = new ChartFrame("折线图 ", chartline0, true);
		ChartFrame frame1 = new ChartFrame("折线图 ", chartline1, true);
		ChartFrame frame2 = new ChartFrame("折线图 ", chartline2, true);
		frame0.pack();
		frame0.setVisible(true);
		frame1.pack();
		frame1.setVisible(true);
		frame2.pack();
		frame2.setVisible(true);
	}

	public static Map<String, ArrayList<NetworkFeathurIndex>> networkIndex()
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		Map<String, Node> nodesMap = ReadDataFromFile(url);
		ArrayList<Entry<String, Node>> sortedDegreeCentrality = degreeCentrality(nodesMap);
		Map<String, ArrayList<NetworkFeathurIndex>> networkFeathurIndexsMap = new HashMap<String, ArrayList<NetworkFeathurIndex>>();

		ArrayList<NetworkFeathurIndex> networkFeathurIndexs1 = initialDegree(sortedDegreeCentrality, nodesMap);
		networkFeathurIndexsMap.put("initialDegree", networkFeathurIndexs1);

		nodesMap = ReadDataFromFile(url);
		ArrayList<Entry<String, Integer>> sortedBetweenessCentrality = betweennessCentrality(nodesMap);
		ArrayList<NetworkFeathurIndex> networkFeathurIndexs2 = initialBetweenness(sortedBetweenessCentrality, nodesMap);
		networkFeathurIndexsMap.put("initialBetweenness", networkFeathurIndexs2);

		nodesMap = ReadDataFromFile(url);
		sortedDegreeCentrality = degreeCentrality(nodesMap);
		ArrayList<NetworkFeathurIndex> networkFeathurIndexs3 = recalculatedDegree(sortedDegreeCentrality, nodesMap);
		networkFeathurIndexsMap.put("recalculatedDegree", networkFeathurIndexs3);

		nodesMap = ReadDataFromFile(url);
		sortedBetweenessCentrality = betweennessCentrality(nodesMap);
		ArrayList<NetworkFeathurIndex> networkFeathurIndexs4 = recalculatedBetweenness(sortedBetweenessCentrality,
				nodesMap);
		networkFeathurIndexsMap.put("recalculatedBetweenness", networkFeathurIndexs4);
		return networkFeathurIndexsMap;
	}

	private static Map<String, Node> ReadDataFromFile(String url)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		File file = new File(url);
		BufferedReader bR = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		String data = null;
		Map<String, Node> nodesMap = new HashMap<String, Node>();
		Node nodeFrom = null, nodeTo = null;
		try {
			while ((data = bR.readLine()) != null) {
				String[] bRdata = data.split(",");
				String stringFrom = bRdata[5], stringTo = bRdata[7];
				String regex = "0+";
				if (!stringFrom.matches(regex) && !stringTo.matches(regex)) {

					if (!nodesMap.containsKey(stringFrom)) {
						nodeFrom = new Node(stringFrom);
						nodesMap.put(stringFrom, nodeFrom);
					} else {
						nodeFrom = nodesMap.get(stringFrom);
					}
					if (!nodesMap.containsKey(stringTo)) {
						nodeTo = new Node(stringTo);
						nodesMap.put(stringTo, nodeTo);
					} else {
						nodeTo = nodesMap.get(stringTo);
					}
					if (!nodeFrom.getDegree().getNode().contains(nodeTo)) {
						nodeFrom.linkNode(nodeTo);
					}
					if (!nodeTo.getDegree().getNode().contains(nodeFrom)) {
						nodeTo.linkNode(nodeFrom);
					}
					String nodePairString = "";
					nodePairString = stringFrom.compareToIgnoreCase(stringTo) > 0 ? (stringFrom + '_' + stringTo)
							: (stringTo + '_' + stringFrom);
					if (NodePairCount.containsKey(nodePairString)) {
						int NPcount = NodePairCount.get(nodePairString);
						NPcount++;
						NodePairCount.put(nodePairString, NPcount);
					} else {
						NodePairCount.put(nodePairString, 0);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			bR.close();
		}
		return nodesMap;
	}

	public static Map<String, Distance> hierarchyTravel(Map<String, Node> nodesMap) {
		Map<String, Distance> minDistanceSet = new HashMap<String, Distance>();
		HashSet<String> visitedNodeSet = new HashSet<String>();
		ConcurrentLinkedQueue<String> hierarchyQueue = new ConcurrentLinkedQueue<String>();
		Iterator<Map.Entry<String, Node>> entriesIterator = nodesMap.entrySet().iterator();
		while (entriesIterator.hasNext()) {
			HashSet<String> visitedIntermediateNode = new HashSet<String>();
			Map.Entry<String, Node> entry = entriesIterator.next();
			Iterator<Node> iteratorNode = entry.getValue().getDegree().getNode().iterator();
			while (iteratorNode.hasNext()) {
				Node nodeInList = iteratorNode.next();
				Distance existInMinDistance = existInMinDistance(entry.getKey(), nodeInList.getName(), minDistanceSet);
				if (existInMinDistance == null) {
					Distance distanceLengthOfOne = new Distance(entry.getValue());
					distanceLengthOfOne.setDistanceList(nodeInList);
					String keyString = entry.getKey() + "<==>" + nodeInList.getName();
					distanceLengthOfOne.setName(keyString);
					minDistanceSet.put(keyString, distanceLengthOfOne);
				}
				hierarchyQueue.add(nodeInList.getName());
			}
			visitedNodeSet.add(entry.getKey());
			while (!hierarchyQueue.isEmpty()) {
				Node intermediateNode = nodesMap.get(hierarchyQueue.poll());
				visitedIntermediateNode.add(intermediateNode.getName());
				Distance currentDistance = new Distance();
				Iterator<Node> intermediateNodeIterator = intermediateNode.getDegree().getNode().iterator();
				while (intermediateNodeIterator.hasNext()) {
					Node _node = intermediateNodeIterator.next();
					if (!visitedIntermediateNode.contains(_node.getName())
							&& !visitedNodeSet.contains(_node.getName())) {
						Distance existDistanceInSet = existInMinDistance(entry.getKey(), _node.getName(),
								minDistanceSet);
						currentDistance = setCurrentDistance(minDistanceSet, entry, intermediateNode);
						currentDistance.setDistanceList(_node);
						if (existDistanceInSet == null) {
							String stringKey = entry.getKey() + "<==>" + _node.getName();
							currentDistance.setName(stringKey);
							minDistanceSet.put(stringKey, currentDistance);
						} else if (existDistanceInSet != null) {
							currentDistance.setName(existDistanceInSet.getName());
							if (existDistanceInSet.getDistanceLong() == currentDistance.getDistanceLong()) {
								for (HashSet<String> distanceSet : currentDistance.getDistanceList()) {
									existDistanceInSet.addDistance(distanceSet);
								}
							}
						}
						if (!hierarchyQueue.contains(_node.getName())) {
							hierarchyQueue.add(_node.getName());
						}
					}
				}
			}
		}
		return minDistanceSet;
	}

	private static Distance existInMinDistance(String key, String name, Map<String, Distance> minDistanceSet) {
		String keyStringInSet1 = key + "<==>" + name;
		String keyStringInSet2 = name + "<==>" + key;
		return minDistanceSet.get(keyStringInSet1) != null ? minDistanceSet.get(keyStringInSet1)
				: minDistanceSet.get(keyStringInSet2);
	}

	private static Distance setCurrentDistance(Map<String, Distance> minDistanceSet, Map.Entry<String, Node> entry,
			Node intermediateNode) {
		Distance distance = existInMinDistance(entry.getKey(), intermediateNode.getName(), minDistanceSet);
		Distance currentDistance = new Distance();
		currentDistance.setDistanceLong(distance.getDistanceLong());
		currentDistance.setName(distance.getName());
		for (HashSet<String> distanceset : distance.getDistanceList()) {
			HashSet<String> set = new HashSet<String>(distanceset);
			currentDistance.addDistance(set);
		}
		return currentDistance;
	}

	public static ArrayList<Entry<String, Node>> degreeCentrality(Map<String, Node> mapNodes) {
		ArrayList<Map.Entry<String, Node>> sortedDegreeCentrality = new ArrayList<Map.Entry<String, Node>>(
				mapNodes.entrySet());
		Collections.sort(sortedDegreeCentrality, new Comparator<Map.Entry<String, Node>>() {

			@Override
			public int compare(Entry<String, Node> o1, Entry<String, Node> o2) {
				return o2.getValue().getDegree().getCount() - o1.getValue().getDegree().getCount();

			}
		});
		return sortedDegreeCentrality;
	}

	public static ArrayList<Entry<String, Integer>> betweennessCentrality(Map<String, Node> nodesMap) {
		Map<String, Distance> minDistanceSet = hierarchyTravel(nodesMap);
		Map<String, Integer> frequencyOfNodeInDistance = new HashMap<String, Integer>();
		Iterator<Distance> iteratorOfDistance = minDistanceSet.values().iterator();
		while (iteratorOfDistance.hasNext()) {
			Iterator<HashSet<String>> iteratorSetDistance = iteratorOfDistance.next().getDistanceList().iterator();
			while (iteratorSetDistance.hasNext()) {
				Iterator<String> iteratorString = iteratorSetDistance.next().iterator();
				while (iteratorString.hasNext()) {
					String string = iteratorString.next();
					if (frequencyOfNodeInDistance.containsKey(string)) {
						int time = (frequencyOfNodeInDistance.get(string).intValue());
						frequencyOfNodeInDistance.put(string, ++time);
					} else {
						frequencyOfNodeInDistance.put(string, new Integer(1));
					}
				}
			}
		}
		Iterator<Map.Entry<String, Node>> nodesMapIterator = nodesMap.entrySet().iterator();
		while (nodesMapIterator.hasNext()) {
			Map.Entry<String, Node> nodemap = nodesMapIterator.next();
			if (!frequencyOfNodeInDistance.containsKey(nodemap.getKey())) {
				frequencyOfNodeInDistance.put(nodemap.getKey(), 0);
			}
		}
		ArrayList<Map.Entry<String, Integer>> sortedBetweenness = new ArrayList<Map.Entry<String, Integer>>(
				frequencyOfNodeInDistance.entrySet());
		Collections.sort(sortedBetweenness, new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		});
		return sortedBetweenness;
	}

	public static double averagePathLength(int nodeCount, Map<String, Distance> minDistanceSet) {
		Iterator<Distance> iterator = minDistanceSet.values().iterator();
		int sum = 0;
		while (iterator.hasNext()) {
			Distance distanceSet = iterator.next();
			sum += distanceSet.getDistanceLong();
		}
		double dividend = (nodeCount * (nodeCount - 1));
		return sum * coefficient / dividend;
	}

	public static double averageClusteringCoefficient(Map<String, Node> mapNodes) {
		Iterator<Map.Entry<String, Node>> iterator = mapNodes.entrySet().iterator();
		double averageClusteringCoeffient = 0.0;
		Map<String, Double> MapClusteringCoefficient = new HashMap<String, Double>();
		while (iterator.hasNext()) {
			int E = 0;
			Node node = iterator.next().getValue();
			int k = node.getDegree().getCount();
			List<Node> nodeList = node.getDegree().getNode();
			Iterator<Node> listIterator = nodeList.iterator();
			Queue<Node> queueNode = new ConcurrentLinkedQueue<Node>();
			Set<String> setNode = new HashSet<String>();
			while (listIterator.hasNext()) {
				Node _node = listIterator.next();
				queueNode.add(_node);
				setNode.add(_node.getName());
			}
			while (!queueNode.isEmpty()) {
				Node __node = queueNode.poll();
				Iterator<Node> iteratorNode = __node.getDegree().getNode().iterator();
				while (iteratorNode.hasNext()) {
					String stringName = iteratorNode.next().getName();
					if (setNode.contains(stringName)) {
						E++;
					}
				}
			}
			if (k > 1) {
				Double clusteringCoefficient = (((double) E / ((double) (k * (k - 1)))));
				averageClusteringCoeffient += clusteringCoefficient;
				MapClusteringCoefficient.put(node.getName(), clusteringCoefficient);
			} else {
				MapClusteringCoefficient.put(node.getName(), 0.0);
			}
		}
		return averageClusteringCoeffient * coefficient / mapNodes.size();
	}

	public static int networkCapacity(Map<String, Integer> NodePairCount) {
		Iterator<Map.Entry<String, Integer>> iterator = NodePairCount.entrySet().iterator();
		int nodeCount = 0;
		while (iterator.hasNext()) {
			int _nodeCount = iterator.next().getValue();
			nodeCount += _nodeCount;
		}
		return nodeCount;
	}

	public static ArrayList<NetworkFeathurIndex> initialDegree(ArrayList<Entry<String, Node>> sortedDegreeCentrality,
			Map<String, Node> nodesMap) {
		HashSet<String> deletedNodeNameByID = new HashSet<String>();
		Map<String, Distance> minDistanceSet = new HashMap<String, Distance>();
		ArrayList<NetworkFeathurIndex> networkIndexArray = new ArrayList<NetworkFeathurIndex>();
		int i = 0;
		do {
			int j = 0;
			NetworkFeathurIndex networkFeathurIndex = new NetworkFeathurIndex();
			minDistanceSet = hierarchyTravel(nodesMap);
			networkFeathurIndex.setAveragePathLength(averagePathLength(nodesMap.size(), minDistanceSet));
			networkFeathurIndex.setAverageClusteringCoefficient(averageClusteringCoefficient(nodesMap));
			networkFeathurIndex.setNetworkCapacity(networkCapacity(NodePairCount));
			networkIndexArray.add(networkFeathurIndex);
			Iterator<Map.Entry<String, Node>> iteratorSortedDegree = sortedDegreeCentrality.iterator();
			while (iteratorSortedDegree.hasNext() && (j++) < limit) {
				Entry<String, Node> deletedNode = iteratorSortedDegree.next();
				deletedNodeNameByID.add(deletedNode.getKey());
				iteratorSortedDegree.remove();
			}
			nodesMap = rebuidRelationBetweenNode(deletedNodeNameByID, nodesMap);
		} while (nodesMap.size() > limit);
		return networkIndexArray;
	}

	public static ArrayList<NetworkFeathurIndex> recalculatedDegree(
			ArrayList<Entry<String, Node>> sortedDegreeCentrality, Map<String, Node> nodesMap) {
		HashSet<String> deletedNodeNameByID = new HashSet<String>();
		Map<String, Distance> minDistanceSet = new HashMap<String, Distance>();
		ArrayList<NetworkFeathurIndex> networkIndexArray = new ArrayList<NetworkFeathurIndex>();
		int i = 0;
		do {
			int j = 0;
			minDistanceSet = hierarchyTravel(nodesMap);
			sortedDegreeCentrality = degreeCentrality(nodesMap);
			NetworkFeathurIndex networkFeathurIndex = new NetworkFeathurIndex();
			networkFeathurIndex.setAveragePathLength(averagePathLength(nodesMap.size(), minDistanceSet));
			networkFeathurIndex.setAverageClusteringCoefficient(averageClusteringCoefficient(nodesMap));
			networkFeathurIndex.setNetworkCapacity(networkCapacity(NodePairCount));
			networkIndexArray.add(networkFeathurIndex);
			Iterator<Map.Entry<String, Node>> iteratorSortedDegree = sortedDegreeCentrality.iterator();
			while (iteratorSortedDegree.hasNext() && (j++ < limit)) {
				Entry<String, Node> deletedNode = iteratorSortedDegree.next();
				deletedNodeNameByID.add(deletedNode.getKey());
				iteratorSortedDegree.remove();
			}
			nodesMap = rebuidRelationBetweenNode(deletedNodeNameByID, nodesMap);
		} while (nodesMap.size() > limit);
		return networkIndexArray;
	}

	public static ArrayList<NetworkFeathurIndex> initialBetweenness(
			ArrayList<Map.Entry<String, Integer>> sortedBetweenness, Map<String, Node> nodesMap) {
		HashSet<String> deletedNodeNameByIB = new HashSet<String>();
		Map<String, Distance> minDistanceSet = new HashMap<String, Distance>();
		ArrayList<NetworkFeathurIndex> networkIndexArray = new ArrayList<NetworkFeathurIndex>();
		int i = 0;
		do {
			int j = 0;
			NetworkFeathurIndex networkFeathurIndex = new NetworkFeathurIndex();
			minDistanceSet = hierarchyTravel(nodesMap);
			networkFeathurIndex.setAveragePathLength(averagePathLength(nodesMap.size(), minDistanceSet));
			networkFeathurIndex.setAverageClusteringCoefficient(averageClusteringCoefficient(nodesMap));
			networkFeathurIndex.setNetworkCapacity(networkCapacity(NodePairCount));
			networkIndexArray.add(networkFeathurIndex);
			Iterator<Map.Entry<String, Integer>> iteratorSortedBetweenness = sortedBetweenness.iterator();
			while (iteratorSortedBetweenness.hasNext() && (j++) < limit) {
				Map.Entry<String, Integer> deletedNode = iteratorSortedBetweenness.next();
				deletedNodeNameByIB.add(deletedNode.getKey());
				iteratorSortedBetweenness.remove();
			}
			nodesMap = rebuidRelationBetweenNode(deletedNodeNameByIB, nodesMap);
		} while (nodesMap.size() > limit);
		return networkIndexArray;
	}

	public static ArrayList<NetworkFeathurIndex> recalculatedBetweenness(
			ArrayList<Map.Entry<String, Integer>> sortedBetweenness, Map<String, Node> nodesMap) {
		HashSet<String> deletedNodeNameByIB = new HashSet<String>();
		Map<String, Distance> minDistanceSet = new HashMap<String, Distance>();
		ArrayList<NetworkFeathurIndex> networkIndexArray = new ArrayList<NetworkFeathurIndex>();
		int i = 0;
		do {
			int j = 0;
			minDistanceSet = hierarchyTravel(nodesMap);
			sortedBetweenness = betweennessCentrality(nodesMap);
			NetworkFeathurIndex networkFeathurIndex = new NetworkFeathurIndex();
			networkFeathurIndex.setAveragePathLength(averagePathLength(nodesMap.size(), minDistanceSet));
			networkFeathurIndex.setAverageClusteringCoefficient(averageClusteringCoefficient(nodesMap));
			networkFeathurIndex.setNetworkCapacity(networkCapacity(NodePairCount));
			networkIndexArray.add(networkFeathurIndex);
			Iterator<Map.Entry<String, Integer>> iteratorSortedBetweenness = sortedBetweenness.iterator();
			while (iteratorSortedBetweenness.hasNext() && (j++) < limit) {
				Map.Entry<String, Integer> deletedNode = iteratorSortedBetweenness.next();
				deletedNodeNameByIB.add(deletedNode.getKey());
				iteratorSortedBetweenness.remove();
			}
			nodesMap = rebuidRelationBetweenNode(deletedNodeNameByIB, nodesMap);
		} while (nodesMap.size() > limit);
		return networkIndexArray;
	}

	public static Map<String, Node> rebuidRelationBetweenNode(HashSet<String> deletedNode, Map<String, Node> nodesMap) {
		Iterator<Map.Entry<String, Node>> iteratorNodeMap = nodesMap.entrySet().iterator();
		while (iteratorNodeMap.hasNext()) {
			Map.Entry<String, Node> nodeMap = iteratorNodeMap.next();
			Node node = nodeMap.getValue();
			LinkedList<Node> nodeLinkedList = node.getDegree().getNode();
			Iterator<Node> nodeIterator = nodeLinkedList.iterator();
			if (!deletedNode.contains(node.getName())) {
				while (nodeIterator.hasNext()) {
					Node nodeInList = nodeIterator.next();
					if (deletedNode.contains(nodeInList.getName())) {
						String f1String = node.getName();
						String f2String = nodeInList.getName();
						String keyString = f1String.compareToIgnoreCase(f2String) > 0 ? f1String + "_" + f2String
								: f2String + "_" + f1String;
						if (NodePairCount.containsKey(keyString)) {
							NodePairCount.remove(keyString);
						}
						nodeIterator.remove();
						node.getDegree().removeNode(nodeInList);
					}
				}
			} else {
				String f1String = node.getName();
				while (nodeIterator.hasNext()) {
					Node nodeInList = nodeIterator.next();
					String f2String = nodeInList.getName();
					String keyString = f1String.compareToIgnoreCase(f2String) > 0 ? f1String + "_" + f2String
							: f2String + "_" + f1String;
					if (NodePairCount.containsKey(keyString)) {
						NodePairCount.remove(keyString);
					}
				}
				iteratorNodeMap.remove();
			}
		}
		return nodesMap;
	}
}
