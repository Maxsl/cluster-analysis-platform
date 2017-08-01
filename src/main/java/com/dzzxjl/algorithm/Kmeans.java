package com.dzzxjl.algorithm;

import java.util.ArrayList;
import java.util.Random;

import com.dzzxjl.data.Sample;

/**
 * K��ֵ�����㷨
 */
public class Kmeans {
	private int k;// �ֳɶ��ٴ�
	private int m;// ��������
	private int dataSetLength;// ���ݼ�Ԫ�ظ����������ݼ��ĳ���
	private ArrayList<Sample> samples; //���ݼ�
	private ArrayList<Sample> center;
	private ArrayList<ArrayList<Sample>> clusterList;
	private ArrayList<Double> jc;// ���ƽ���ͣ�kԽ�ӽ�dataSetLength�����ԽС����k==dataSetLengthʱ�����Ϊ0
	private Random random;//�����������������ʼʱk�����ӵ�

	/**
	 * ��ȡ�������
	 * 
	 * @return �����
	 */
	public ArrayList<ArrayList<Sample>> getCluster() {
		return clusterList;
	}

	/**
	 * ���캯����������Ҫ�ֳɵĴ�����
	 * 
	 * @param k
	 *            ������,��k<=0ʱ������Ϊ1����k��������Դ�ĳ���ʱ����Ϊ����Դ�ĳ���
	 */
	public Kmeans(int k, ArrayList<Sample> samples) {
		if (k <= 0) {
			k = 1;
		}
		this.k = k;
		this.samples = samples;
	}

	/**
	 * ��ʼ��
	 */
	private void init() {
		m = 0;
		random = new Random(); //���������Բ����
//		if (dataSet == null || dataSet.size() == 0) {
//			initDataSet();
//		}
		dataSetLength = samples.size();
		if (k > dataSetLength) {
			k = dataSetLength;
		}
		center = initCenters();
		clusterList = initCluster();
		jc = new ArrayList<Double>();  //jc��������ʽ��Ϊ���ĵĵط�
	}


	/**
	 * ��ʼ���������������ֳɶ��ٴؾ��ж��ٸ����ĵ�
	 * 
	 * @return ���ĵ㼯
	 */
	private ArrayList<Sample> initCenters() {
		ArrayList<Sample> center = new ArrayList<Sample>();
		int[] randoms = new int[k];//k�������
		boolean flag;  //flag������ʲô���
		int temp = random.nextInt(dataSetLength); //�����������Ϊ0��dataSetLength֮��������
		randoms[0] = temp; //��һ����Ϊtemp
		/*
		 * �����������ظ��������
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
			center.add(samples.get(randoms[i]));// ���ɳ�ʼ����������
		}
		return center;
	}

	/**
	 * ��ʼ���ؼ���
	 * 
	 * @return һ����Ϊk�صĿ����ݵĴؼ���
	 */
	private ArrayList<ArrayList<Sample>> initCluster() { 
		ArrayList<ArrayList<Sample>> clusterList = new ArrayList<ArrayList<Sample>>();
		for (int i = 0; i < k; i++) {
			clusterList.add(new ArrayList<Sample>());
		}
		return clusterList;
	}

	/**
	 * ����������֮��ľ���
	 * 
	 * @param element
	 *            ��1
	 * @param center
	 *            ��2
	 * @return ����
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
	 * ��ȡ���뼯������С�����λ��
	 * 
	 * @param distance
	 *            ��������
	 * @return ��С�����ھ��������е�λ��
	 */
	private int minDistance(double[] distance) {
		double minDistance = distance[0];
		int minLocation = 0;
		for (int i = 1; i < distance.length; i++) {
			if (distance[i] < minDistance) {
				minDistance = distance[i];
				minLocation = i;
			} else if (distance[i] == minDistance) // �����ȣ��������һ��λ��
			{
				if (random.nextInt(10) < 5) {
					minLocation = i;
				}
			}
		}

		return minLocation;
	}

	/**
	 * ���ģ�����ǰԪ�طŵ���С����������صĴ���
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
			clusterList.get(minLocation).add(samples.get(i));// ���ģ�����ǰԪ�طŵ���С����������صĴ���

		}
	}

	/**
	 * ���������ƽ���ķ���
	 * 
	 * @param element
	 *            ��1
	 * @param center
	 *            ��2
	 * @return ���ƽ��
	 */
	private double errorSquare(Sample element, Sample center) {
		double x = element.getAttributes()[0] - center.getAttributes()[0];
		double y = element.getAttributes()[1] - center.getAttributes()[1];

		double errSquare = x * x + y * y;

		return errSquare;
	}

	/**
	 * �������ƽ����׼��������
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
	 * �����µĴ����ķ���
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
				// ����һ��ƽ��ֵ�����������е����е���������֮�ͳ��Ը���
				newCenter[0] = newCenter[0] / n;
				newCenter[1] = newCenter[1] / n;
				Sample sample = new Sample(new double[]{newCenter[0],newCenter[1]});
				center.set(i, sample);
			}
		}
	}
	/**
	 * Kmeans�㷨���Ĺ��̷���
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
		 System.out.println("note:the times of repeat:m="+m);//�����������
	}

}

