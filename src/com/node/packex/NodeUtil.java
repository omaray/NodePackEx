package com.node.packex;

public class NodeUtil {
    private static boolean IS_TESTING = true; 
    
    public static String getNodeDatasetName() {
        return NodeUtil.IS_TESTING ? NodeConstants.BQ_DATASET_TEST_NAME : NodeConstants.BQ_DATASET_NAME;
    }
    
    public static String getNodeTableName(String timeline) {
        return NodeUtil.IS_TESTING 
                ? String.format(NodeConstants.BQ_TABLE_TEST_NAME, timeline) 
                : String.format(NodeConstants.BQ_TABLE_NAME, timeline);
    }
}
