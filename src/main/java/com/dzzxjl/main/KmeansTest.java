package com.dzzxjl.main;

import java.util.ArrayList;

import com.dzzxjl.algorithm.Kmeans;
import com.dzzxjl.data.DataReader;
import com.dzzxjl.data.DataShower;
import com.dzzxjl.data.Sample;


public class KmeansTest {
	public String execute(String filePath,int knum){
		String graphPath; //图片存储路径
		
		DataReader reader = new DataReader();
		reader.readData(filePath);
		ArrayList<Sample> samples = reader.getSamples();
		Kmeans k=new Kmeans(knum,samples);
		//执行算法
		k.execute();
		//得到聚类结果
		ArrayList<ArrayList<Sample>> clusterList = k.getCluster();
		System.out.println(clusterList.size());
		DataShower dataShower = new DataShower(clusterList);
		dataShower.showGraph();
		graphPath = dataShower.saveGraph("kmeans");
		
		return graphPath;
	}
	
}
