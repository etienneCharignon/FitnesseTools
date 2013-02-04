package fitnesse.tools;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public final class DirectoryUtils {

	private DirectoryUtils() {
		// private constructor for helper class.
	}

	private static class DirectoryFileFilterDecorator implements FilenameFilter {

		private final FilenameFilter fileNameFilter;

		public DirectoryFileFilterDecorator(FilenameFilter fileNameFilter) {
			this.fileNameFilter = fileNameFilter;
		}

		@Override
		public boolean accept(File dir, String name) {
			return new File(dir, name).isDirectory() || fileNameFilter.accept(dir, name);
		}

	}

	public static void visitAllFiles(File dir, FilenameFilter filter, FileVisitor visitor) throws IOException,
			ClassNotFoundException {
		if (dir.isDirectory()) {
			for (String children : dir.list(new DirectoryFileFilterDecorator(filter))) {
				visitAllFiles(new File(dir, children), filter, visitor);
			}
		}
		else {
			visitor.visite(dir);
		}
	}
}
