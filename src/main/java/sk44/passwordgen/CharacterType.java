package sk44.passwordgen;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.text.RandomStringGenerator;

/**
 *
 * @author sk
 */
enum CharacterType {

    ASCII("ASCII") {
        @Override
        protected String generate(int length) {
            return ASCII_GENERATOR.generate(length);
        }
    }, ALPHA_NUMERIC("alpha-numeric") {
        @Override
        protected String generate(int length) {
            return ALPHA_NUMERIC_GENERATOR.generate(length);
        }
    }, ALPHABETIC("alphabetic") {
        @Override
        protected String generate(int length) {
            return CharacterType.ALPHABETIC_GENERATOR.generate(length);
        }
    };
    private static final int[] IGNORE_CHARS = new int[]{'1', 'I', 'l', '|', '0', 'O', 'o'};
    private static final RandomStringGenerator ASCII_GENERATOR = new RandomStringGenerator.Builder()
            .filteredBy(codePoint -> {
                if (isIgnoreChar(codePoint)) {
                    return false;
                }
                return ('!' <= codePoint && codePoint <= '~');
            })
            .build();
    private static final RandomStringGenerator ALPHA_NUMERIC_GENERATOR = new RandomStringGenerator.Builder()
            .filteredBy(codePoint -> {
                if (isIgnoreChar(codePoint)) {
                    return false;
                }
                return ('0' <= codePoint && codePoint <= '9') || ('A' <= codePoint && codePoint <= 'Z') || ('a' <= codePoint && codePoint <= 'z');
            })
            .build();
    private static final RandomStringGenerator ALPHABETIC_GENERATOR = new RandomStringGenerator.Builder()
            .filteredBy(codePoint -> {
                if (isIgnoreChar(codePoint)) {
                    return false;
                }
                return ('A' <= codePoint && codePoint <= 'Z') || ('a' <= codePoint && codePoint <= 'z');
            })
            .build();

    private static boolean isIgnoreChar(int codePoint) {
        for (int c : IGNORE_CHARS) {
            if (codePoint == c) {
                return true;
            }
        }
        return false;

    }
    final String text;

    private CharacterType(String text) {
        this.text = text;
    }

    protected abstract String generate(int length);

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
