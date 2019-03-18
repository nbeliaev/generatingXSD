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

    private File sourceFile;
    private boolean printHelp = false;
    private final String nameSpace = "";
    private final Inst2XsdOptions schema = new Inst2XsdOptions();

    public static void main(String[] args) throws IOException, XmlException {
	// write your code here
        // delete setters and getters
        XsdSchema xsdSchema = new XsdSchema(args);
        /*if (xsdSchema.getPrintHelp()) {
            System.out.println("HELP");
            return;
        } else {
            xsdSchema.checkSourceFile();
        }

        //System.out.println(xsdSchema.getSourceFile().getPath());

        XmlObject [] xmlObjects = new XmlObject[1];
        xmlObjects[0] = XmlObject.Factory.parse(xsdSchema.getSourceFile());
        SchemaDocument schema = xsdSchema.getSchema(xmlObjects);
        System.out.println(schema);*/
    }

    private SchemaDocument getSchema(XmlObject [] xmlObjects) {
        Inst2XsdOptions inst2XsdOptions = new Inst2XsdOptions();
        inst2XsdOptions.setDesign(Inst2XsdOptions.DESIGN_VENETIAN_BLIND);
        SchemaDocument [] schemaDocuments = Inst2Xsd.inst2xsd(xmlObjects, inst2XsdOptions);
        if (schemaDocuments != null && schemaDocuments.length > 0) {
            return schemaDocuments[0];
        }

        return null;
    }

    XsdSchema(String[] args) {

        Options options = new Options();

        Option option = new Option("i", "input", true, "input file path");
        option.setRequired(true);
        options.addOption(option);

        CommandLineParser commandLineParser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helpFormatter.printHelp("bla bla", options);
            System.exit(1);
        }

        String input = cmd.getOptionValue("input");
        System.out.println(input);

        /*int argsCount = args.length;
        String pathToFile = "";

        if (argsCount == 0) {
            return;
        }

        for (int i=0; i < args.length; i++) {

            switch (args[i]) {
                case "-i":
                    pathToFile = args.length > 1 ? args[i + 1]:"";
                    break;
                case "--input":
                    pathToFile = args.length > 1 ? args[i + 1]:"";
                    break;
                case "-h":
                    setPrintHelp(true);
                    break;
                case "--help":
                    setPrintHelp(true);
                    break;
                case "-nm":
                    //name space
                    break;
                case "--namespace":
                    // name space
                    break;
                case "-s":
                    //schema
                    break;
                case "--schema":
                    // schema
                    break;
            }

            if (printHelp) {
                break;
            }
        }

        if (pathToFile.isEmpty()) {
            return;
        }

        sourceFile = new File(pathToFile);*/

    }

    private boolean getPrintHelp() {
        return printHelp;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    private void setPrintHelp(boolean printHelp) {
        this.printHelp = printHelp;
    }

    private void checkSourceFile() throws FileNotFoundException {

        Path path = Paths.get(sourceFile.toURI());
        if (Files.notExists(path)) {
            throw new FileNotFoundException("no such file " + sourceFile.getPath());
        }

    }

    enum SchemaDesign {
        RUSSIAN_DOLL,
        SALAMI_SLICE,
        VENETIAN_BLIND
    }
}
