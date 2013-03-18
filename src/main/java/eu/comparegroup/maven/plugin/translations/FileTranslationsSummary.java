package eu.comparegroup.maven.plugin.translations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FileTranslationsSummary {
	private String filePath;

	private Map<String, String> existingTranslations = new HashMap<>();

	private List<String> missingTranslations = new ArrayList<>();

	public FileTranslationsSummary(String filePath) {
		this.filePath = filePath;
	}

	public void addExistingTranslation(String keyName, String keyValue) {
		existingTranslations.put(keyName, keyValue);
	}

	public void addMissingTranslations(String keyname) {
		missingTranslations.add(keyname);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(filePath);
		sb.append("\n");
		sb.append("---Existing translations size=" + existingTranslations.size());
		sb.append("\n");
		sb.append(existingTranslationsToString());
		sb.append("---Missing translations size=" + missingTranslations.size());
		sb.append("\n");
		sb.append(missingTranslationsToString());
		sb.append("\n\n");
		return sb.toString();
	}

	private String existingTranslationsToString() {
		StringBuilder sb = new StringBuilder();
		Iterator iterator = existingTranslations.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = (Map.Entry) iterator.next();
			sb.append(pair.getKey());
			sb.append("=");
			sb.append(pair.getValue());
			sb.append("\n");
		}
		return sb.toString();
	}

	private String missingTranslationsToString() {
		StringBuilder sb = new StringBuilder();
		for (String missingTranslation : missingTranslations) {
			sb.append(missingTranslation);
			sb.append("\n");
		}
		return sb.toString();
	}
}