package com.dzzxjl.algorithm;

import java.util.ArrayList;

import com.dzzxjl.data.DBScanPoint;

public class DBScan {
	 	private double radius;  //扫描半径
	    private int minPts;  //最小包含点数

	    public DBScan(double radius,int minPts) {
	        this.radius = radius;
	        this.minPts = minPts;
	    }

	    public void process(ArrayList<DBScanPoint> DBScanPoints) {   //运用DBSCAN算法，对所有的点集进行处理
	        int size = DBScanPoints.size();  //点集中点的个数
	        int idx = 0;  //idx起到一个计数的作用？
	        int cluster = 1; //第一类
	        while (idx<size) {
	            DBScanPoint p = DBScanPoints.get(idx++);  //对每一个点进行遍历
	            //choose an unvisited DBScanPoint
	            if (!p.getVisit()) {
	                p.setVisit(true);//set visited
	                ArrayList<DBScanPoint> adjacentDBScanPoints = getAdjacentDBScanPoints(p, DBScanPoints);//邻接点
	                //set the DBScanPoint which adjacent DBScanPoints less than minPts noised
	                if (adjacentDBScanPoints != null && adjacentDBScanPoints.size() < minPts) {
	                    p.setNoised(true);    //设置为噪声点
	                } else {
	                    p.setCluster(cluster);  //设置 该点所属聚类
	                    for (int i = 0; i < adjacentDBScanPoints.size(); i++) {
	                        DBScanPoint adjacentDBScanPoint = adjacentDBScanPoints.get(i);
	                        //only check unvisited DBScanPoint, cause only unvisited have the chance to add new adjacent DBScanPoints
	                        if (!adjacentDBScanPoint.getVisit()) {
	                            adjacentDBScanPoint.setVisit(true);
	                            ArrayList<DBScanPoint> adjacentAdjacentDBScanPoints = getAdjacentDBScanPoints(adjacentDBScanPoint, DBScanPoints);
	                            //add DBScanPoint which adjacent DBScanPoints not less than minPts noised
	                            if (adjacentAdjacentDBScanPoints != null && adjacentAdjacentDBScanPoints.size() >= minPts) {
	                                adjacentDBScanPoints.addAll(adjacentAdjacentDBScanPoints);
	                            }
	                        }
	                        //add DBScanPoint which doest not belong to any cluster
	                        if (adjacentDBScanPoint.getCluster() == 0) {
	                            adjacentDBScanPoint.setCluster(cluster);
	                            //set DBScanPoint which marked noised before non-noised
	                            if (adjacentDBScanPoint.getNoised()) {
	                                adjacentDBScanPoint.setNoised(false);
	                            }
	                        }
	                    }
	                    cluster++;  //聚第二类
	                }
	            }
	        }
	    }

	    private ArrayList<DBScanPoint> getAdjacentDBScanPoints(DBScanPoint centerDBScanPoint,ArrayList<DBScanPoint> DBScanPoints) {
	        ArrayList<DBScanPoint> adjacentDBScanPoints = new ArrayList<DBScanPoint>();
	        for (DBScanPoint p:DBScanPoints) {
	            //include centerDBScanPoint itself
	            double distance = centerDBScanPoint.getDistance(p);
	            if (distance<=radius) {
	                adjacentDBScanPoints.add(p);
	            }
	        }
	        return adjacentDBScanPoints;
	    }
}
