package com.dzzxjl.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DataShower {
	ArrayList<ArrayList<Sample>> clusterList;
	XYSeriesCollection xyseriescollection1;
	JFreeChart chart;
	/*
	 * kmeans
	 */
	public DataShower(ArrayList<ArrayList<Sample>> clusterList) {
		ArrayList<XYSeries> xySeries = new ArrayList<>(); //�������ݼ�
		int clusterSize = clusterList.size();
		for(int i = 0 ;i < clusterSize;i++){
			XYSeries xyseries = new XYSeries("��" + i +"�������");
			xySeries.add(xyseries);
		}
		for(int i = 0;i < clusterSize; i++) {
			for(int j = 0;j<clusterList.get(i).size();j++) {
				double latitude = clusterList.get(i).get(j).getAttributes()[1];
				double longitude = clusterList.get(i).get(j).getAttributes()[0];
				xySeries.get(i).add(latitude,longitude);
			}
		}
		xyseriescollection1 = new XYSeriesCollection(); 
		for(int i = 0; i < clusterSize; i++){
			xyseriescollection1.addSeries(xySeries.get(i));
		}
	}
	/*
	 * չʾͼƬ
	 */
	public void showGraph() {
		chart = ChartFactory.createScatterPlot("��ֲ�ͼ", "����","γ��", xyseriescollection1, PlotOrientation.VERTICAL, true, false, false);
		XYPlot p = chart.getXYPlot();
		p.setBackgroundPaint(ChartColor.WHITE);		// ����ͼ�ı�����ɫ
		ChartFrame chartFrame=new ChartFrame("ŦԼ������γ��ɢ��ͼ",chart); 
        //chartҪ����Java��������У�ChartFrame�̳���java��Jframe�ࡣ�õ�һ�������������Ƿ��ڴ������Ͻǵģ��������м�ı��⡣
//        chartFrame.pack(); //�Ժ��ʵĴ�Сչ��ͼ��
//        chartFrame.setVisible(true);//ͼ���Ƿ�ɼ�        
	}
	public String saveGraph(String algorithm) {
	
      int width = 640; /* Width of the image */
      int height = 480; /* Height of the image */ 
      Date nowTime = new Date(System.currentTimeMillis());
      SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
      String retStrFormatNowDate = sdFormatter.format(nowTime);
      String graphPath = algorithm + retStrFormatNowDate;
//      File XYChart = new File("./graph/" + filePath +".jpeg"); /* ����ͼƬ�洢λ�� */
      File XYChart = new File("/Users/dzzxjl/Documents/data/graph/" + graphPath + ".jpeg");
      if(!XYChart.exists()){
    	  try {
			XYChart.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
      }
      try {
			ChartUtilities.saveChartAsJPEG( XYChart, chart, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
      return graphPath;
	}
	
}
