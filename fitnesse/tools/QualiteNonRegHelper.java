package fitnesse.tools;

import static fitnesse.tools.DirectoryUtils.visitAllFiles;
import static java.util.Arrays.asList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;

public class QualiteNonRegHelper {
	private static final List<String> IGNORED_PAGES = asList("ConsulterListeCed", "ConsulterListeConditionnement",
			"ConsulterListeAdr", "ConsulterListeUnite", "ConsulterListePrestation", "ConsulterListeTgap");
	private static final String ROOT = "../lcwp-fitnesse/FitNesseRoot/";
	private static final String NON_REG = "NonRegression";
	public static final String SPECS = "SpecificationsDetaillees";

	public static List<String> listNonRegPages() throws FileNotFoundException, IOException, ClassNotFoundException {
		final List<String> nonRegTestPages = new ArrayList<String>();
		FilenameFilter propertiesFileFilter = new NameFileFilter("properties.xml");
		visitAllFiles(new File(QualiteNonRegHelper.ROOT + QualiteNonRegHelper.NON_REG), propertiesFileFilter,
				new FileVisitor() {

					@Override
					public void visite(File propertyFile) throws IOException {

						List<String> fileLines = IOUtils.readLines(new BufferedReader(new FileReader(propertyFile)));
						String aLine;
						Iterator<String> iter = fileLines.iterator();
						// search for symbolicLinks markup
						while (iter.hasNext()) {
							aLine = iter.next();
							if (aLine.contains("<SymbolicLinks>")) {
								break;
							}
						}
						// parse test pages until reaching SymbolicLinks ending markup
						while (iter.hasNext()) {
							aLine = iter.next();
							if (aLine.contains("</SymbolicLinks>")) {
								break;
							}
							if (aLine.contains(QualiteNonRegHelper.SPECS)) {
								nonRegTestPages.add(aLine.substring(aLine.lastIndexOf(QualiteNonRegHelper.SPECS)
										+ QualiteNonRegHelper.SPECS.length() + 1, aLine.lastIndexOf("</")));
							}
						}

					}
				});
		return nonRegTestPages;
	}

	public static List<String> listSpecDetaillesPages() {
		List<String> specDetailleesTestPages = new ArrayList<String>();
		File dir = new File(QualiteNonRegHelper.ROOT + QualiteNonRegHelper.SPECS);
		String[] files = dir.list(DirectoryFileFilter.INSTANCE);
		for (String dirName : files) {
			if (!QualiteNonRegHelper.IGNORED_PAGES.contains(dirName)) {
				specDetailleesTestPages.add(dirName);
			}
		}
		return specDetailleesTestPages;
	}

	public static List<String> getDifferences(List<String> expPages, final List<String> actualPages) {
		final List<String> fileNotInExpPages = new ArrayList<String>();
		for (String page : actualPages) {
			if (!expPages.contains(page)) {
				fileNotInExpPages.add(page);
			}
		}
		return fileNotInExpPages;
	}

}
