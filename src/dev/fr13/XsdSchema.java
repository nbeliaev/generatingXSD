package dev.fr13;

import org.apache.commons.cli.*;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.inst2xsd.Inst2Xsd;
import org.apache.xmlbeans.impl.inst2xsd.Inst2XsdOptions;
import org.apache.xmlbeans.impl.xb.xsdschema.SchemaDocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XsdSchema {

    private final File SOURCE_FILE;
    private final File OUTPUT_FILE;
    private final String NAME_SPACE;
    private final int SCHEMA_DESIGN;

    public static void main(String[] args) throws IOException, XmlException {

        XsdSchema xsdSchema = new XsdSchema(args);
        xsdSchema.checkSourceFile();

        XmlObject xmlObject = XmlObject.Factory.parse(xsdSchema.SOURCE_FILE);
        SchemaDocument schema = xsdSchema.getSchema(xmlObject);
        if (schema != null)
            schema.save(xsdSchema.OUTPUT_FILE);

    }

    private SchemaDocument getSchema(XmlObject xmlObject) {

        Inst2XsdOptions inst2XsdOptions = new Inst2XsdOptions();
        inst2XsdOptions.setDesign(SCHEMA_DESIGN);

        XmlObject [] xmlObjects = new XmlObject[1];
        xmlObjects[0] = xmlObject;

        SchemaDocument [] schemaDocuments = Inst2Xsd.inst2xsd(xmlObjects, inst2XsdOptions);
        if (schemaDocuments != null && schemaDocuments.length > 0) {
            schemaDocuments[0].getSchema().setTargetNamespace(NAME_SPACE);
            return schemaDocuments[0];
        }
        return null;
    }

    private XsdSchema(String[] args) {

        Options options = new Options();

        Option inputOption = new Option("i", "input", true, "input xml file path");
        inputOption.setRequired(true);
        options.addOption(inputOption);

        Option outputOption = new Option("o", "output", true, "output xsd file path");
        options.addOption(outputOption);

        Option nameSpaceOption = new Option("n", "name-space", true, "target name space");
        options.addOption(nameSpaceOption);

        Option schemaOption = new Option("s", "schema-design", true,
                "schema design: 1-Russian Doll, 2-Salami slice, 3-Venetian blind");
        options.addOption(schemaOption);

        CommandLineParser commandLineParser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helpFormatter.printHelp("This tool creates xsd file from xml.", options);
            System.exit(1);
        }

        SOURCE_FILE = new File(cmd.getOptionValue("input"));

        String outputFile = cmd.getOptionValue("output");
        if (outputFile == null) {
            this.OUTPUT_FILE = new File(SOURCE_FILE.getParent() + File.separatorChar + "schema.xsd");
        } else {
            this.OUTPUT_FILE = new File(outputFile);
        }

        String ns = cmd.getOptionValue("name-space");
        NAME_SPACE = ns != null ? ns : "http://v8.default.com";

        String schema = cmd.getOptionValue("schema-design");
        if (schema == null) {
            this.SCHEMA_DESIGN = 3;
        }
        else {
            switch (schema) {
                case "1":
                    this.SCHEMA_DESIGN = Inst2XsdOptions.DESIGN_RUSSIAN_DOLL;
                    break;
                case "2":
                    this.SCHEMA_DESIGN = Inst2XsdOptions.DESIGN_SALAMI_SLICE;
                    break;
                case "3":
                    this.SCHEMA_DESIGN = Inst2XsdOptions.DESIGN_VENETIAN_BLIND;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown value for design type.");
            }
        }
    }

    private void checkSourceFile() throws FileNotFoundException {

        Path path = Paths.get(SOURCE_FILE.toURI());
        if (Files.notExists(path)) {
            throw new FileNotFoundException("no such file " + SOURCE_FILE.getPath());
        }

    }
}
