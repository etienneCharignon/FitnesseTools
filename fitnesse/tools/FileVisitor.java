package fitnesse.tools;

import java.io.File;
import java.io.IOException;

public interface FileVisitor {

	void visite(File file) throws IOException, ClassNotFoundException;

}