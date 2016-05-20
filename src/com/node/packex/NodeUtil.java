package com.node.packex;

import java.util.ArrayList;
import java.util.List;

import com.node.packex.model.Month;

public class NodeUtil {
    private static boolean IS_TESTING = true; 
    private static List<Month> monthList = null;
    
    public static String getNodeDatasetName() {
        return NodeUtil.IS_TESTING ? NodeConstants.BQ_DATASET_TEST_NAME : NodeConstants.BQ_DATASET_NAME;
    }
    
    public static String getNodeTableName(String timeline) {
        return NodeUtil.IS_TESTING 
                ? String.format(NodeConstants.BQ_TABLE_TEST_NAME, timeline) 
                : String.format(NodeConstants.BQ_TABLE_NAME, timeline);
    }
    
    public static List<Month> getMonthList() {
        if (monthList == null) {
            monthList = new ArrayList<Month>();
            monthList.add(new Month(0, "DUMMY", "", ""));
            monthList.add(new Month(1, "JANUARY", "%s-01-01", "%s-01-31"));
            monthList.add(new Month(2, "FEBUARY", "%s-02-01", "%s-02-28"));
            monthList.add(new Month(3, "MARCH", "%s-03-01", "%s-03-31"));
            monthList.add(new Month(4, "APRIL", "%s-04-01", "%s-04-30"));
            monthList.add(new Month(5, "MAY", "%s-05-01", "%s-05-31"));
            monthList.add(new Month(6, "JUNE", "%s-06-01", "%s-06-30"));
            monthList.add(new Month(7, "JULY", "%s-07-01", "%s-07-31"));
            monthList.add(new Month(8, "AUGUST", "%s-08-01", "%s-08-31"));
            monthList.add(new Month(9, "SEPTEMBER", "%s-09-01", "%s-09-30"));
            monthList.add(new Month(10, "OCTOBER", "%s-10-01", "%s-10-31"));
            monthList.add(new Month(11, "NOVEMBER", "%s-11-01", "%s-11-30"));
            monthList.add(new Month(12, "DECEMBER", "%s-12-01", "%s-12-31"));
        }
        
        return monthList;
    }
}
