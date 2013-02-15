/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.passwordgen;

/**
 * Application config.
 * 
 * @author sk
 */
class Config {

	static final int DEFAULT_VALUE_FOR_DEFAULT_CHARACTER_LENGTH = 16;
	static final int DEFAULT_VALUE_FOR_MAX_CHARACTER_LENGTH = 30;

	public static Config configWithValues(int defaultCharacterLength, int maxCharacterLength) {
		return new Config(defaultCharacterLength, maxCharacterLength);
	}

	public static Config configWithDefaultValues() {
		return new Config(DEFAULT_VALUE_FOR_DEFAULT_CHARACTER_LENGTH, DEFAULT_VALUE_FOR_MAX_CHARACTER_LENGTH);
	}

	private Config(int defaultCharacterLength, int maxCharacterLength) {
		this.defaultCharacterLength = defaultCharacterLength;
		this.maxCharacterLength = maxCharacterLength;
	}
	
	private final int defaultCharacterLength;
	private final int maxCharacterLength;

	public int getDefaultCharacterLength() {
		return defaultCharacterLength;
	}

	public int getMaxCharacterLength() {
		return maxCharacterLength;
	}

}
