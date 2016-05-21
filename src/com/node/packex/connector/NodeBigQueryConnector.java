package com.node.packex.connector;

import java.util.ArrayList;
import java.util.logging.Level;

import com.google.cloud.bigquery.Field;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.StandardTableDefinition;
import com.google.cloud.bigquery.Table;
import com.google.cloud.bigquery.TableId;
import com.google.cloud.bigquery.TableInfo;
import com.packex.Constants;
import com.node.packex.NodeConstants;
import com.packex.connector.*;

public class NodeBigQueryConnector extends BigQueryConnector {
    protected static NodeBigQueryConnector instance;
    
    private NodeBigQueryConnector() {}
    
    public static NodeBigQueryConnector getInstance() {
        if (instance == null) {
            instance = new NodeBigQueryConnector();
        }
        
        return instance;
    }
    
    public void createMonthTable(String datasetName, String tableName) {
        TableId tableId = TableId.of(datasetName, tableName);
        Table table = this.bigQuery.getTable(tableId);
        if (table != null) {
            logger.log(Level.WARNING, String.format("Table \"%s\" already exists so NOT creating it", tableName));
            return;
        }
        
        ArrayList<Field> fields = new ArrayList<Field>();
        
        // Core fields
        Field dateField = Field.of(Constants.DATE_FIELD, Field.Type.timestamp());
        fields.add(dateField);
        Field yearField = Field.of(NodeConstants.YEAR_FIELD, Field.Type.integer());
        fields.add(yearField);
        Field monthField = Field.of(NodeConstants.MONTH_FIELD, Field.Type.string());
        fields.add(monthField);
        Field companyField = Field.of(NodeConstants.COMPANY_FIELD, Field.Type.string());
        fields.add(companyField);
        Field nameField = Field.of(Constants.PACKAGE_NAME_FIELD, Field.Type.string());
        fields.add(nameField);
        Field versionField = Field.of(Constants.VERSION_FIELD, Field.Type.string());
        fields.add(versionField);
        Field categoryField = Field.of(Constants.CATEGORY_FIELD, Field.Type.string());
        fields.add(categoryField);
        Field totalDownloadsField = Field.of(Constants.TOTAL_DOWNLOADS_FIELD, Field.Type.integer());
        fields.add(totalDownloadsField);
        
        Schema schema = Schema.of(fields);
        StandardTableDefinition tableDefinition = StandardTableDefinition.of(schema);
        
        // Create the table
        this.bigQuery.create(TableInfo.of(tableId, tableDefinition));
        logger.log(Level.INFO, String.format("Create table \"%s\"", tableName));
    }
}
