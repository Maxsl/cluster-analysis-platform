package com.dzzxjl.algorithm;

import java.util.ArrayList;

import com.dzzxjl.data.DBScanPoint;

public class DBScan {
	 	private double radius;  //ɨ��뾶
	    private int minPts;  //��С��������

	    public DBScan(double radius,int minPts) {
	        this.radius = radius;
	        this.minPts = minPts;
	    }

	    public void process(ArrayList<DBScanPoint> DBScanPoints) {   //����DBSCAN�㷨�������еĵ㼯���д���
	        int size = DBScanPoints.size();  //�㼯�е�ĸ���
	        int idx = 0;  //idx��һ�����������ã�
	        int cluster = 1; //��һ��
	        while (idx<size) {
	            DBScanPoint p = DBScanPoints.get(idx++);  //��ÿһ������б���
	            //choose an unvisited DBScanPoint
	            if (!p.getVisit()) {
	                p.setVisit(true);//set visited
	                ArrayList<DBScanPoint> adjacentDBScanPoints = getAdjacentDBScanPoints(p, DBScanPoints);//�ڽӵ�
	                //set the DBScanPoint which adjacent DBScanPoints less than minPts noised
	                if (adjacentDBScanPoints != null && adjacentDBScanPoints.size() < minPts) {
	                    p.setNoised(true);    //����Ϊ������
	                } else {
	                    p.setCluster(cluster);  //���� �õ���������
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
	                    cluster++;  //�۵ڶ���
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
