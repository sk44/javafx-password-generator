/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.passwordgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

/**
 *
 * @author sk
 */
public class ConfigLoaderTest {

	private static final String USER_CONFIG_FILE_NAME = "config.properties";
	
	public ConfigLoaderTest() {
	}

	@Before
	public void setUp() {
		clearUserConfig();
	}

	@After
	public void tearDown() {
		clearUserConfig();
	}

	private void clearUserConfig() {
		File userConfig = new File(USER_CONFIG_FILE_NAME);
		if (userConfig.exists() && userConfig.delete() == false) {
			throw new RuntimeException("faled to delete user config.");
		}
	}

	@Test
	public void loadでユーザー設定ファイルがない場合はデフォルト値で作成する() throws Exception {

		File userConfig = new File(USER_CONFIG_FILE_NAME);
		assertThat(userConfig.exists(), is(false));

		ConfigLoader sut = new ConfigLoader();
		Config config = sut.load();

		assertThat(userConfig.exists(), is(true));

		Properties props = new Properties();
		try (Reader r = new InputStreamReader(new FileInputStream(userConfig))) {
			props.load(r);
			assertThat(props.getProperty(ConfigLoader.propertyNameOfDefaultCharacterLength()), 
				is(String.valueOf(Config.DEFAULT_VALUE_FOR_DEFAULT_CHARACTER_LENGTH)));
			assertThat(props.getProperty(ConfigLoader.propertyNameOfMaxCharacterLength()), 
				is(String.valueOf(Config.DEFAULT_VALUE_FOR_MAX_CHARACTER_LENGTH)));
		}
		assertThat(config.getDefaultCharacterLength(), is(Config.DEFAULT_VALUE_FOR_DEFAULT_CHARACTER_LENGTH));
		assertThat(config.getMaxCharacterLength(), is(Config.DEFAULT_VALUE_FOR_MAX_CHARACTER_LENGTH));
	}

	@Test
	public void loadでユーザー設定ファイルがある場合はファイルの値を読み込む() {
		File userConfig = new File(USER_CONFIG_FILE_NAME);
		ConfigLoader sut = new ConfigLoader();
		final int def = 15;
		final int max = 20;
		sut.writeConfigTo(userConfig, Config.configWithValues(def, max));
		assertThat(userConfig.exists(), is(true));

		Config config = sut.load();
		assertThat(config.getDefaultCharacterLength(), is(def));
		assertThat(config.getMaxCharacterLength(), is(max));
	}

	@Test
	public void loadで不正な値が取得された場合はデフォルト値を使用する() {

		ConfigLoader sut = new ConfigLoader() {
			@Override
			protected Properties readFromConfigFile() {
				return createProperties("x", "y");
			}
		};
		Config config = sut.load();
		assertThat(config.getDefaultCharacterLength(), is(Config.DEFAULT_VALUE_FOR_DEFAULT_CHARACTER_LENGTH));
		assertThat(config.getMaxCharacterLength(), is(Config.DEFAULT_VALUE_FOR_MAX_CHARACTER_LENGTH));
	}

	private Properties createProperties(String defaultCharacterLength, String maxCharacterLength) {
		Properties p = new Properties();
		p.setProperty(ConfigLoader.propertyNameOfDefaultCharacterLength(), defaultCharacterLength);
		p.setProperty(ConfigLoader.propertyNameOfMaxCharacterLength(), maxCharacterLength);
		return p;
	}

}
