package fitnesse.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class FitnesseTest {

	@Test
	public void runFitnesseSuite() throws Exception {
		ProcessBuilder processBuilder = new ProcessBuilder(
				"java",
				"-jar",
				"fitnesse.jar",
				"-c",
				"DetailedSpecifications?suite&format=text");
		processBuilder.directory(new File("../lcwp-fitnesse"));
		Process process = processBuilder.start();
		assertThat(parseResult(new InputStreamReader(process.getInputStream()))).isEqualTo("\n");
	}

	private String parseResult(Reader reader) throws IOException {
		StringBuilder result = new StringBuilder("\n");
		BufferedReader br = new BufferedReader(reader);
		String ligne;
		boolean testDone = false;
		StringBuilder fullMessage = new StringBuilder();
		while ((ligne = br.readLine()) != null) {
			if (ligne.matches("[^.].*seconds$")) {
				result.append(ligne).append("\n");
			}
			if (ligne.matches(".*Exit-Code.*")) {
				testDone = true;
			}
			fullMessage.append(ligne).append("\n");
		}
		br.close();
		if (testDone) return result.toString();
		return fullMessage.toString();
	}

  // ***********
  // The following tests are testing the parseResult() methode here above.
  // ***********
	@Test
	public void parseNoTestDone() throws Exception {
		String error404 = "FitNesse (v20110104) Started...\n"//
				+ "	port:              9123\n"//
				+ "	root page:         fitnesse.wiki.FileSystemPage at ./FitNesseRoot\n"//
				+ "	logger:            none\n"//
				+ "	authenticator:     fitnesse.authentication.PromiscuousAuthenticator\n"//
				+ "	html page factory: fitnesse.html.HtmlPageFactory\n"//
				+ "	page version expiration set to 14 days.\n"//
				+ "Executing command: \"DetailedSpecifications?suite&format=text\"\n"//
				+ "-----Command Output-----\n"//
				+ "HTTP/1.1 404 Not Found\n"//
				+ "Content-Length: 836\n"//
				+ "Connection: close\n"//
				+ "Server: FitNesse-v20110104\n"//
				+ "Content-Type: text/html; charset=utf-8\n"//
				+ "\n"//
				+ "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"//
				+ "<html>\n"//
				+ "	<head>\n"//
				+ "		<title>Not Found:\"DetailedSpecifications</title>\n"//
				+ "		<link rel=\"stylesheet\" type=\"text/css\" href=\"/files/css/fitnesse.css\" media=\"screen\"/>\n"//
				+ "		<link rel=\"stylesheet\" type=\"text/css\" href=\"/files/css/fitnesse_print.css\" media=\"print\"/>\n"//
				+ "		<script src=\"/files/javascript/fitnesse.js\" type=\"text/javascript\"></script>\n"//
				+ "	</head>\n"//
				+ "	<body>\n"//
				+ "		<div class=\"sidebar\">\n"//
				+ "			<div class=\"art_niche\" onclick=\"document.location='FrontPage'\"></div>\n"//
				+ "			<div class=\"actions\"></div>\n"//
				+ "		</div>\n"//
				+ "		<div class=\"mainbar\">\n"//
				+ "			<div class=\"header\">\n"//
				+ "				<span class=\"page_title\">Not Found:\"DetailedSpecifications</span>\n"//
				+ "			</div>\n"//
				+ "			<div class=\"main\">The requested resource: <i>\"DetailedSpecifications</i> was not found.</div>\n"//
				+ "		</div>\n"//
				+ "	</body>\n"//
				+ "</html>\n"//
				+ "-----Command Complete-----\n";
		assertThat(parseResult(new StringReader(error404))).isEqualTo(error404);
	}

	@Test
	public void parseFailedResult() throws Exception {
		assertThat(
				parseResult(new StringReader(
						"FitNesse (v20110104) Started...\n"
								+ "	port:              9123\n"
								+ "	root page:         fitnesse.wiki.FileSystemPage at ./FitNesseRoot\n"
								+ "	logger:            none\n"
								+ "	authenticator:     fitnesse.authentication.PromiscuousAuthenticator\n"
								+ "	html page factory: fitnesse.html.HtmlPageFactory\n"
								+ "	page version expiration set to 0 days.\n"
								+ "Executing command: DetailedSpecifications?suite&format=text\n"
								+ "-----Command Output-----\n"
								+ "\n"
								+ "Starting Test System: slim using fitnesse.slim.SlimService.\n"
								+ "F 14:44:34 R:109  W:0    I:0    E:0    CancelOrderWithControl	(DetailedSpecifications.CancelOrderWithControl)	14,797 seconds\n"
								+ ". 14:44:49 R:42   W:0    I:0    E:0    CancellableOrderStatus	(DetailedSpecifications.CancellableOrderStatus)	0,093 seconds\n"
								+ "--------\n" //
								+ "37 Tests,	0 Failures	24,438 seconds.\n" //
								+ "0\n" //
								+ "Exit-Code: 1\n" //
								+ "\n" + "-----Command Complete-----\n")))
				.isEqualTo(
						"\n"
								+ "F 14:44:34 R:109  W:0    I:0    E:0    CancelOrderWithControl	(DetailedSpecifications.CancelOrderWithControl)	14,797 seconds\n");
	}

	@Test
	public void parseSuccessResult() throws Exception {
		assertThat(
				parseResult(new StringReader(
						"FitNesse (v20110104) Started...\n" //
								+ "	port:              9123\n"//
								+ "	root page:         fitnesse.wiki.FileSystemPage at ./FitNesseRoot\n"//
								+ "	logger:            none\n"//
								+ "	authenticator:     fitnesse.authentication.PromiscuousAuthenticator\n"//
								+ "	html page factory: fitnesse.html.HtmlPageFactory\n"//
								+ "	page version expiration set to 0 days.\n"//
								+ "Executing command: DetailedSpecifications?suite&format=text\n"//
								+ "-----Command Output-----\n"//
								+ "\n"//
								+ "Starting Test System: slim using fitnesse.slim.SlimService.\n"//
								+ ". 14:44:34 R:109  W:0    I:0    E:0    CancelOrderWithControl	(DetailedSpecifications.CancelOrderWithControl)	14,797 seconds\n"//
								+ ". 14:44:49 R:42   W:0    I:0    E:0    CancellableOrderStatus	(DetailedSpecifications.CancellableOrderStatus)	0,093 seconds\n"//
								+ "--------\n" //
								+ "37 Tests,	0 Failures	24,438 seconds.\n" //
								+ "0\n" //
								+ "Exit-Code: 0\n" //
								+ "\n"//
								+ "-----Command Complete-----\n"//
				))).isEqualTo("\n");
	}
}
