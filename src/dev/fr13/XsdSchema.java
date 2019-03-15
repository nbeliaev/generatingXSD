package dev.fr13;

import org.apache.xmlbeans.impl.inst2xsd.Inst2XsdOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XsdSchema {

    private File sourceFile;
    private boolean printHelp = false;

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        XsdSchema xsdSchema = new XsdSchema(args);
        if (xsdSchema.getPrintHelp()) {

        } else {
            xsdSchema.checkSourceFile();
        }

        Inst2XsdOptions inst2XsdOptions = new Inst2XsdOptions();
        //XmlObject.Factory.parse("src\\dev\\fr13\example.xml")
    }

    XsdSchema(String[] args) {

        int argsCount = args.length;
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
            }

            if (printHelp) {
                pathToFile = "";
                break;
            }
        }

        if (pathToFile.isEmpty()) {
            return;
        }

        sourceFile = new File(pathToFile);

    }

    private boolean getPrintHelp() {
        return printHelp;
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
}
