/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.passwordgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * Config loader.
 *
 * @author sk
 */
class ConfigLoader {

	private static final String CONFIG_FILE_NAME = "config.properties";
	private static final String CONFIG_ENCODING = "UTF-8";

	static String propertyNameOfDefaultCharacterLength() {
		return "character.length.default";
	}

	static String propertyNameOfMaxCharacterLength() {
		return "character.length.max";
	}

	/**
	 * Load config and return its instance.
	 *
	 * <p>if invalid values loaded, then use default value.</p>
	 *
	 * @return config
	 */
	Config load() {
		Properties p = readFromConfigFile();
		String def = p.getProperty(propertyNameOfDefaultCharacterLength());
		String max = p.getProperty(propertyNameOfMaxCharacterLength());

		// TODO validate...
		try {
			return Config.configWithValues(Integer.valueOf(def).intValue(), Integer.valueOf(max).intValue());
		} catch (Exception e) {
			// TODO logging
			System.out.print(e.getMessage());
			return Config.configWithDefaultValues();
		}
	}

	Properties readFromConfigFile() {

		File userConfig = new File(CONFIG_FILE_NAME);
		if (userConfig.exists() == false) {
			writeConfigTo(userConfig, Config.configWithDefaultValues());
		}
		Properties p = new Properties();
		try (Reader reader = new InputStreamReader(new FileInputStream(userConfig), CONFIG_ENCODING)) {
			p.load(reader);
			return p;
		} catch (IOException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	void writeConfigTo(File userConfig, Config config) {

		Properties userProperties = new Properties();
		userProperties.put(
			propertyNameOfDefaultCharacterLength(),
			String.valueOf(config.getDefaultCharacterLength()));
		userProperties.put(
			propertyNameOfMaxCharacterLength(),
			String.valueOf(config.getMaxCharacterLength()));

		try (OutputStream os = new FileOutputStream(userConfig)) {
			userProperties.store(os, CONFIG_ENCODING);
		} catch (IOException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}
}
