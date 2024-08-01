package de.dnb.ie.ddcTk;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;

import org.marc4j.MarcReader;
import org.marc4j.MarcStreamWriter;
import org.marc4j.MarcWriter;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.Record;

public class XML2Marc {

	public static void main(String[] args) throws FileNotFoundException {
		InputStream input = new FileInputStream("D:/Normdaten/ddc_zap.xml");
		MarcReader reader = new MarcXmlReader(input);
		PrintStream printStream = new PrintStream("D:/Normdaten/ddc.mrc");
		MarcWriter writer = new MarcStreamWriter(printStream);
        while (reader.hasNext()) {
            Record record = reader.next();
            writer.write(record);
        }
        writer.close();

	}

}
