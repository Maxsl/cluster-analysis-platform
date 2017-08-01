package com.dzzxjl.algorithm;

import java.util.ArrayList;
import java.util.Random;

import com.dzzxjl.data.Sample;

/**
 * K均值聚类算法
 */
public class Kmeans {
	private int k;// 分成多少簇
	private int m;// 迭代次数
	private int dataSetLength;// 数据集元素个数，即数据集的长度
	private ArrayList<Sample> samples; //数据集
	private ArrayList<Sample> center;
	private ArrayList<ArrayList<Sample>> clusterList;
	private ArrayList<Double> jc;// 误差平方和，k越接近dataSetLength，误差越小，当k==dataSetLength时，误差为0
	private Random random;//用来生成随机数，初始时k个种子点

	/**
	 * 获取结果分组
	 * 
	 * @return 结果集
	 */
	public ArrayList<ArrayList<Sample>> getCluster() {
		return clusterList;
	}

	/**
	 * 构造函数，传入需要分成的簇数量
	 * 
	 * @param k
	 *            簇数量,若k<=0时，设置为1，若k大于数据源的长度时，置为数据源的长度
	 */
	public Kmeans(int k, ArrayList<Sample> samples) {
		if (k <= 0) {
			k = 1;
		}
		this.k = k;
		this.samples = samples;
	}

	/**
	 * 初始化
	 */
	private void init() {
		m = 0;
		random = new Random(); //种子数可以不添加
//		if (dataSet == null || dataSet.size() == 0) {
//			initDataSet();
//		}
		dataSetLength = samples.size();
		if (k > dataSetLength) {
			k = dataSetLength;
		}
		center = initCenters();
		clusterList = initCluster();
		jc = new ArrayList<Double>();  //jc即误差，即公式最为核心的地方
	}


	/**
	 * 初始化中心数据链表，分成多少簇就有多少个中心点
	 * 
	 * @return 中心点集
	 */
	private ArrayList<Sample> initCenters() {
		ArrayList<Sample> center = new ArrayList<Sample>();
		int[] randoms = new int[k];//k个随机数
		boolean flag;  //flag用来做什么标记
		int temp = random.nextInt(dataSetLength); //产生的随机数为0至dataSetLength之间的随机数
		randoms[0] = temp; //第一个数为temp
		/*
		 * 产生不包含重复的随机数
		 */
		for (int i = 1; i < k; i++) {
			flag = true;
			while (flag) {
				temp = random.nextInt(dataSetLength);
				int j = 0;
				while (j < i) {
					if (temp == randoms[j]) {
						break;
					}
					j++;
				}
				if (j == i) {
					flag = false;
				}
			}
			randoms[i] = temp;
		}
		for (int i = 0; i < k; i++) {
			center.add(samples.get(randoms[i]));// 生成初始化中心链表
		}
		return center;
	}

	/**
	 * 初始化簇集合
	 * 
	 * @return 一个分为k簇的空数据的簇集合
	 */
	private ArrayList<ArrayList<Sample>> initCluster() { 
		ArrayList<ArrayList<Sample>> clusterList = new ArrayList<ArrayList<Sample>>();
		for (int i = 0; i < k; i++) {
			clusterList.add(new ArrayList<Sample>());
		}
		return clusterList;
	}

	/**
	 * 计算两个点之间的距离
	 * 
	 * @param element
	 *            点1
	 * @param center
	 *            点2
	 * @return 距离
	 */
	private double distance(Sample element, Sample center) {
		double distance = 0.0f;
		double x = element.getAttributes()[0] - center.getAttributes()[0];
		double y = element.getAttributes()[1] - center.getAttributes()[1];
		double z = x * x + y * y;
		distance = (double) Math.sqrt(z);
		return distance;
	}

	/**
	 * 获取距离集合中最小距离的位置
	 * 
	 * @param distance
	 *            距离数组
	 * @return 最小距离在距离数组中的位置
	 */
	private int minDistance(double[] distance) {
		double minDistance = distance[0];
		int minLocation = 0;
		for (int i = 1; i < distance.length; i++) {
			if (distance[i] < minDistance) {
				minDistance = distance[i];
				minLocation = i;
			} else if (distance[i] == minDistance) // 如果相等，随机返回一个位置
			{
				if (random.nextInt(10) < 5) {
					minLocation = i;
				}
			}
		}

		return minLocation;
	}

	/**
	 * 核心，将当前元素放到最小距离中心相关的簇中
	 */
	private void clusterSet() {
		double[] distance = new double[k];
		for (int i = 0; i < dataSetLength; i++) {
			for (int j = 0; j < k; j++) {
				distance[j] = distance(samples.get(i), center.get(j));
				// System.out.println("test2:"+"dataSet["+i+"],center["+j+"],distance="+distance[j]);
			}
			int minLocation = minDistance(distance);
			// System.out.println("test3:"+"dataSet["+i+"],minLocation="+minLocation);
			// System.out.println();
			clusterList.get(minLocation).add(samples.get(i));// 核心，将当前元素放到最小距离中心相关的簇中

		}
	}

	/**
	 * 求两点误差平方的方法
	 * 
	 * @param element
	 *            点1
	 * @param center
	 *            点2
	 * @return 误差平方
	 */
	private double errorSquare(Sample element, Sample center) {
		double x = element.getAttributes()[0] - center.getAttributes()[0];
		double y = element.getAttributes()[1] - center.getAttributes()[1];

		double errSquare = x * x + y * y;

		return errSquare;
	}

	/**
	 * 计算误差平方和准则函数方法
	 */
	private void countRule() {
		double jcF = 0;
		for (int i = 0; i < clusterList.size(); i++) {
			for (int j = 0; j < clusterList.get(i).size(); j++) {
				jcF += errorSquare(clusterList.get(i).get(j), center.get(i));
			}
		}
		jc.add(jcF);
	}

	/**
	 * 设置新的簇中心方法
	 */
	private void setNewCenter() {
		for (int i = 0; i < k; i++) {
			int n = clusterList.get(i).size();
			if (n != 0) {
				double[] newCenter = { 0, 0 };
				for (int j = 0; j < n; j++) {
					
					newCenter[0] += clusterList.get(i).get(j).getAttributes()[0];
					newCenter[1] += clusterList.get(i).get(j).getAttributes()[1];
				}
				// 设置一个平均值，即将聚类中的所有点横坐标相加之和除以个数
				newCenter[0] = newCenter[0] / n;
				newCenter[1] = newCenter[1] / n;
				Sample sample = new Sample(new double[]{newCenter[0],newCenter[1]});
				center.set(i, sample);
			}
		}
	}
	/**
	 * Kmeans算法核心过程方法
	 */
	public void execute() {
		init();
		while (true) {
			clusterSet();
			countRule();
			if (m != 0) {
				if (jc.get(m) - jc.get(m - 1) == 0) {
					break;
				}
			}
			setNewCenter();
			m++;
			clusterList.clear();
			clusterList = initCluster();
		}
		 System.out.println("note:the times of repeat:m="+m);//输出迭代次数
	}

}

