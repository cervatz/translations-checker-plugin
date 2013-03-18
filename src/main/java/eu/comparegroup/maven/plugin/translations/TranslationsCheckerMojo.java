package eu.comparegroup.maven.plugin.translations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.DirectoryScanner;

/**
 * Checks the translations.
 */
@Mojo( name = "check")
public class TranslationsCheckerMojo extends AbstractMojo {
	@Parameter( property = "jdbcConnectionUrl", required = true)
	private String jdbcConnectionUrl;

	@Parameter( property = "jdbcConnectionUsername", required = true)
	private String jdbcConnectionUsername;

	@Parameter( property = "jdbcConnectionPassword", required = true)
	private String jdbcConnectionPassword;

	@Parameter( property = "translationsQuery", required = true)
	private String translationsQuery;

	private List<FileTranslationsSummary> fileTranslationsSummaries = new ArrayList<>();

	public void execute() throws MojoExecutionException {
		getLog().info("Beginning ...");

		DirectoryScanner directoryScanner = new DirectoryScanner();

		String[] includes = {"**/*.xhtml"};
		directoryScanner.setBasedir(new File("src/main/webapp"));
		directoryScanner.setIncludes(includes);
		directoryScanner.scan();

		TranslationsLoader translationsLoader = new TranslationsLoader(jdbcConnectionUrl, jdbcConnectionUsername, jdbcConnectionPassword, translationsQuery);
		translationsLoader.loadTranslations();
		Map<String, String> translations = translationsLoader.getTranslations();

		String[] files = directoryScanner.getIncludedFiles();
		for (int i = 0; i < files.length; i++) {
			FileChecker fileChecker = new FileChecker("src/main/webapp/" + files[i], translations);
			FileTranslationsSummary fileTranslationSummary = fileChecker.processFile();
			fileTranslationsSummaries.add(fileTranslationSummary);
		}

		printOutcome();

		getLog().info("Ending ");
	}

	private void printOutcome() {
		for (FileTranslationsSummary fileTranslationsSummary : fileTranslationsSummaries) {
			String fileTranslationsSummaryString = fileTranslationsSummary.toString();
			getLog().info(fileTranslationsSummaryString);
		}
	}
}