package com.node.packex;

public class NodeConstants {
    public static final String DAY = "day";
    public static final String WEEK = "week";
    public static final String MONTH = "month";
    
    // Path to packages of interest
    public static final String NODE_PACKAGES_FILE_PATH = "./resources/node_packages.json";
    
    // Npm download URLs
    public static final String NODE_POINT_URL_TEMPLATE = "https://api.npmjs.org/downloads/point/%s:%s/%s";
    public static final String NODE_RANGE_URL_TEMPLATE = "https://api.npmjs.org/downloads/range/%s:%s/%s";
    
    // BigQuery constants
    public static final String BQ_DATASET_NAME = "node_package_downloads";
    public static final String BQ_DATASET_TEST_NAME = BQ_DATASET_NAME + "_test";
    public static final String BQ_TABLE_NAME = "%s_data";
    public static final String BQ_TABLE_TEST_NAME = BQ_TABLE_NAME + "_test";

    // BigQuery table fields
    public static final String YEAR_FIELD = "year";
    public static final String MONTH_FIELD = "month";
    public static final String COMPANY_FIELD = "company";
}
