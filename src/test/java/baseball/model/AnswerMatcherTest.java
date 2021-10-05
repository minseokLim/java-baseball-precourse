package baseball.model;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import baseball.model.dto.MatchResult;
import baseball.model.impl.AnswerMatcherImpl.AnswerMatcherBuilder;
import baseball.util.CustomImmutableCollection;

public class AnswerMatcherTest {
	private static AnswerMatcher answerMatcher;

	@BeforeAll
	static void beforeAll() {
		List<Integer> answerNumbers = Arrays.asList(8, 1, 5);
		answerMatcher = new AnswerMatcherBuilder().withAnswerNumbers(answerNumbers).build();
	}

	@ParameterizedTest
	@DisplayName("다양한 입력값들에 대해 적절한 값을 반환하는지를 테스트")
	@CsvSource(value = {"274:낫싱:false", "152:2볼:false", "824:1스트라이크:false",
		"517:1스트라이크 1볼:false", "815:3스트라이크:true"}, delimiter = ':')
	void matches(String input, String expectedMatchResult, String fullyMatched) {
		List<Integer> inputNumbers = new CustomImmutableCollection<>(input.split("")).map(Integer::parseInt);
		boolean expectedFullyMatched = Boolean.parseBoolean(fullyMatched);

		MatchResult matchResult = answerMatcher.matches(inputNumbers);

		assertThat(matchResult.toRecognizableString()).isEqualTo(expectedMatchResult);
		assertThat(matchResult.isFullyMatched()).isEqualTo(expectedFullyMatched);
	}
}
