import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import main.WordNetReader;
import org.junit.jupiter.api.Test;
import main.AutograderBuddy;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

/** Tests the most basic case for Hyponyms where the list of words is one word long, and k = 0.*/
public class TestOneWordK0Hyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    public static final String WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";

    @Test
    public void testActK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("act");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[act, action, change, demotion, human_action, human_activity, variation]";
        assertThat(actual).isEqualTo(expected);
    }

    // TODO: Add more unit tests (including edge case tests) here.
    @Test
    public void testWordNetReaderMapping() {
        WordNetReader wnr = new WordNetReader(SMALL_HYPONYM_FILE, SMALL_SYNSET_FILE);

        // 测试一词多义：确保 "change" 对应了多个 ID
        // 假设在 synsets16.txt 中，change 出现在两个不同的行
        Set<String> hyponyms = wnr.getCommonHyponyms(Collections.singletonList("change"));
        assertThat(hyponyms).contains("variation"); // 语义1的下位词
        assertThat(hyponyms).contains("modification"); // 语义2的下位词（如果有的话）
    }

    @Test
    public void testMultiWordIntersection() {
        WordNetReader wnr = new WordNetReader(SMALL_HYPONYM_FILE, SMALL_SYNSET_FILE);

        // 查询 "occurrence" 和 "change" 的共同下位词
        List<String> words = List.of("occurrence", "change");
        Set<String> result = wnr.getCommonHyponyms(words);

        // 预期结果应该是两个集合的交集
        assertThat(result).containsExactly("alteration", "change", "variation").inOrder();
        // 确保不包含只属于其中一个词的下位词
        assertThat(result).doesNotContain("action");
    }

    @Test
    public void testEmptyIntersection() {
        WordNetReader wnr = new WordNetReader(SMALL_HYPONYM_FILE, SMALL_SYNSET_FILE);

        // 两个风马牛不相及的词
        List<String> words = List.of("apple", "computer");
        Set<String> result = wnr.getCommonHyponyms(words);

        assertThat(result).isEqualTo("");
    }
}
