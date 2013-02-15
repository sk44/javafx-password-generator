/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.passwordgen;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author sk
 */
 enum CharacterType {

	ASCII("ASCII") {
		@Override
		protected String generate(int count) {
			return RandomStringUtils.randomAscii(count);
		}
	}, ALPHA_NUMERIC("alpha-numeric") {
		@Override
		protected String generate(int count) {
			return RandomStringUtils.randomAlphanumeric(count);
		}
	}, ALPHABETIC("alphabetic") {
		@Override
		protected String generate(int count) {
			return RandomStringUtils.randomAlphabetic(count);
		}
	};
	final String text;

	private CharacterType(String text) {
		this.text = text;
	}

	protected abstract String generate(int count);

	static CharacterType characterTypeOfText(String text) {
		for (CharacterType t : CharacterType.values()) {
			if (t.text.equals(text)) {
				return t;
			}
		}
		throw new IllegalArgumentException("text " + text + "is not specified.");
	}

	static List<String> allTextValues() {
		List<String> results = new ArrayList<>(CharacterType.values().length);
		for (CharacterType t : CharacterType.values()) {
			results.add(t.text);
		}
		return results;
	}
	
}
