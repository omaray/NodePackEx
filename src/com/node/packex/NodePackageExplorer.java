package com.node.packex;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.node.packex.connector.NodeBigQueryConnector;
import com.node.packex.manager.NodeMonthManager;
import com.packex.loader.CompanyLoader;
import com.packex.model.company.CompanyPackages;
import com.packex.model.company.PackageInfo;

public class NodePackageExplorer {
    private static final Logger logger = Logger.getLogger(NodePackageExplorer.class.getName());
    
    public void execute() {
        NodeBigQueryConnector connector = NodeBigQueryConnector.getInstance();
        
        String datasetName = NodeUtil.getNodeDatasetName();
        String tableName = NodeUtil.getNodeTableName(NodeConstants.MONTH);
        connector.createDataset(datasetName);
        connector.createMonthTable(datasetName, tableName);
        
        CompanyLoader companyLoader = new CompanyLoader();
        companyLoader.load(NodeConstants.NODE_PACKAGES_FILE_PATH);
        LinkedList<CompanyPackages> companyPackagesList = companyLoader.getCompanyData();
        
        try {
            connector.begin(datasetName, tableName);
            
            for (CompanyPackages companyPackages : companyPackagesList) {
                for (PackageInfo pkg : companyPackages.getPackages()) {
                    
                    logger.log(Level.INFO, String.format("Saving the package info for %s in the %s language", 
                            pkg.getName().toUpperCase(), pkg.getLanguage().toUpperCase()));
                    
                    NodeMonthManager monthManager = 
                            new NodeMonthManager(connector, pkg.getName(), companyPackages.getCompany(), pkg.getCategory());
                    
                    monthManager.saveData();
                }
            }
            
            connector.commit();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "hit an issue while committing the rows", ex);
        }
    }
    
    public static void main(String[] args) {
        NodePackageExplorer nodePackEx = new NodePackageExplorer();
        nodePackEx.execute();
    }
}
