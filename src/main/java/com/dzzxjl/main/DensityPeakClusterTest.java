package com.dzzxjl.main;

import java.util.ArrayList;

import com.dzzxjl.algorithm.DensityPeakCluster;
import com.dzzxjl.data.DataReader;
import com.dzzxjl.data.DataShower;
import com.dzzxjl.data.Sample;

public class DensityPeakClusterTest {
	public String execute(String filePath) {
			String graphPath;
			DataReader reader = new DataReader();
			reader.readData(filePath);
			ArrayList<Sample> samples = reader.getSamples();
			DensityPeakCluster cluster = new DensityPeakCluster(samples);
			cluster.calPairDistance();
			double dc = cluster.findDC();
			System.out.println(dc);
			cluster.calRho(dc);//由得到的dc值计算rho值
			cluster.calDelta();//计算delta值
			cluster.clustering(0.0005, 6);//test500阈值
//			cluster.clustering(0.9, 60);//原始数据集阈值
//			cluster.clustering(0.95, 10);
//			cluster.clustering(0.999999, 17);//论文中A图使用数据集
//			cluster.clustering(1, 8);
			System.out.println("cluster center index list is "+cluster.getCenterList());
			cluster.predictLabel();

			DataShower dataShower = new DataShower(cluster.getClusterList());
			dataShower.showGraph();
			graphPath = dataShower.saveGraph("cfsdp");
			System.out.println("cluster center index list is "+cluster.getCenterList());
			return graphPath;
		}
		
//		Evaluation evaluation = new Evaluation(samples);
//		evaluation.precision();
}

