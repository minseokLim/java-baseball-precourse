package baseball.model;

import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import baseball.model.impl.AnswerGeneratorImpl.AnswerGeneratorBuilder;

public class AnswerGeneratorTest {
	private final AnswerGenerator defaultAnswerGenerator =
		new AnswerGeneratorBuilder().withAnswerLength(3).withRange(1, 9).build();

	@Test
	@DisplayName("추출된 정답이 적절한 길이를 가지는지를 테스트")
	void getAnswerWithProperLength() {
		List<Integer> answerNumbers = defaultAnswerGenerator.getAnswerNumbers();

		assertThat(answerNumbers).hasSize(3);
	}

	@Test
	@DisplayName("추출된 정답이 적절한 범위의 값을 가지고 있는지를 테스트")
	void getAnswerWithProperRange() {
		List<Integer> answerNumbers = defaultAnswerGenerator.getAnswerNumbers();

		assertThat(answerNumbers).allMatch(num -> num >= 1 && num <= 9);
	}

	@Test
	@DisplayName("추출된 정답이 서로 다른 수들로 이루어져 있는지를 테스트")
	void getAnswerWithDistinctNumbers() {
		List<Integer> answerNumbers = defaultAnswerGenerator.getAnswerNumbers();
		Set<Integer> distinctNumbers = new HashSet<>(answerNumbers);

		assertThat(answerNumbers).hasSameSizeAs(distinctNumbers);
	}

	@ParameterizedTest
	@DisplayName("적절하지 않은 인수를 통해 AnswerGenerator를 생성할 때 익셉션이 발생하는지 테스트")
	@CsvSource(value = {"-3:1:9", "3:9:1", "3:-1:40", "5:1:3"}, delimiter = ':')
	void constructWithIllegalArguments(String answerLength, String start, String end) {
		int actualAnswerLength = Integer.parseInt(answerLength);
		int actualStart = Integer.parseInt(start);
		int actualEnd = Integer.parseInt(end);

		assertThatIllegalArgumentException().isThrownBy(() ->
			new AnswerGeneratorBuilder().withAnswerLength(actualAnswerLength).withRange(actualStart, actualEnd).build()
		);
	}
}
