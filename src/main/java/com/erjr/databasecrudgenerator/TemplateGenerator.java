package com.erjr.databasecrudgenerator;

import java.io.File;
import java.io.IOException;

import org.antlr.stringtemplate.StringTemplate;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateGenerator.class);
    private StringTemplate template;
    private String packageToWriteTo;
    private File outputDirectory;

    public TemplateGenerator(String packageToWriteTo, File outputDirectory) {
        this.packageToWriteTo = packageToWriteTo;
        this.outputDirectory = outputDirectory;
    }

    public void buildTemplateFromDatabaseStructure(StringTemplate template, DatabaseStructure databaseStructure) {
        this.template = template;
        for (Table table : databaseStructure.tables) {
            String filledInTemplate = generateTemplateForTable(table);
            writeTemplateToFile(filledInTemplate, table);
        }
    }

    private void writeTemplateToFile(String filledInTemplate, Table table) {
        String outputDirectoryPlusPackageDirectory = outputDirectory
                + packageToWriteTo.replaceAll("\\.", File.pathSeparator);
        File outputDirectory = new File(outputDirectoryPlusPackageDirectory);
        outputDirectory.mkdirs();
        File outputFile = new File(outputDirectoryPlusPackageDirectory + File.pathSeparator + table.name);
        try {
            FileUtils.write(outputFile, filledInTemplate);
        } catch (IOException e) {
            LOGGER.error("Failed to write " + table.name + " to a file.");
            e.printStackTrace();
        }
    }

    private String generateTemplateForTable(Table table) {
        template.setAttribute("packageName", packageToWriteTo);
        template.setAttribute("tableName", table.name);
        template.setAttribute("fields", table.fields);
        return template.toString();
    }
}
