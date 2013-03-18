package eu.comparegroup.maven.plugin.translations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileChecker {
	private static final Pattern MESSAGES_PATTERN = Pattern.compile("messages.([a-zA-Z_0-9]+)");

	private String filePath;

	private Map<String, String> translations;

	public FileChecker(String filePath, Map<String, String> translations) {
		this.filePath = filePath;
		this.translations = translations;
	}

	public FileTranslationsSummary processFile() {
		FileTranslationsSummary fileTranslationsSummary = new FileTranslationsSummary(filePath);
		String line = "";
		try {
			FileReader fileReader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				Matcher matcher = MESSAGES_PATTERN.matcher(line);
				while (matcher.find()) {
					String matchingString = matcher.group();
					String keyName = extractTranslationKey(matchingString);
					processTranslation(keyName, translations, fileTranslationsSummary);
				}
			}
			bufferedReader.close();
			fileReader.close();
		} catch (Exception ex) {
			Logger.getLogger(TranslationsCheckerMojo.class.getName()).log(Level.SEVERE, null, ex);
		}
		return fileTranslationsSummary;
	}

	private String extractTranslationKey(String matchingString) {
		return matchingString.replace("messages.", "");
	}

	private void processTranslation(String keyName, Map<String, String> translations, FileTranslationsSummary fileTranslationsSummary) {
		if (translations.containsKey(keyName)) {
			String keyValue = translations.get(keyName);
			fileTranslationsSummary.addExistingTranslation(keyName, keyValue);
		} else {
			fileTranslationsSummary.addMissingTranslations(keyName);
		}

	}
}
