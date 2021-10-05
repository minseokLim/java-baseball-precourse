package baseball.model.impl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import baseball.model.AnswerMatcher;
import baseball.model.dto.MatchResult;
import baseball.util.CustomImmutableCollection;

public class AnswerMatcherImpl implements AnswerMatcher {
	private final CustomImmutableCollection<NumberElement> answerElements;

	private AnswerMatcherImpl(final List<Integer> answerNumbers) {
		Objects.requireNonNull(answerNumbers, "answerNumbers must not be null");

		if (answerNumbers.isEmpty()) {
			throw new IllegalArgumentException("answerNumbers must not be empty");
		}

		this.answerElements = new CustomImmutableCollection<>(NumberElement.toNumberElements(answerNumbers));
	}

	@Override
	public MatchResult matches(final List<Integer> inputNumbers) {
		CustomImmutableCollection<MatchType> matchTypes = getAllMatchTypes(inputNumbers);

		int strikeCount = matchTypes.countIf(MatchType.STRIKE::equals);
		int ballCount = matchTypes.countIf(MatchType.BALL::equals);

		return new MatchResult(strikeCount, ballCount, strikeCount == answerElements.size());
	}

	private CustomImmutableCollection<MatchType> getAllMatchTypes(final List<Integer> inputNumbers) {
		List<NumberElement> inputElements = NumberElement.toNumberElements(inputNumbers);
		List<MatchType> matchTypes = new CustomImmutableCollection<>(inputElements).mapFlat(this::getMatchTypes);

		return new CustomImmutableCollection<>(matchTypes);
	}

	private List<MatchType> getMatchTypes(final NumberElement inputElement) {
		return answerElements.map(answerElement -> answerElement.getMatchTypeByComparing(inputElement));
	}

	public static class AnswerMatcherBuilder {
		private List<Integer> answerNumbers;

		public AnswerMatcherBuilder withAnswerNumbers(final List<Integer> answerNumbers) {
			this.answerNumbers = answerNumbers;
			return this;
		}

		public AnswerMatcher build() {
			return new AnswerMatcherImpl(answerNumbers);
		}
	}

	private static class NumberElement {
		private final int index;
		private final int value;

		private NumberElement(final int index, final int value) {
			this.index = index;
			this.value = value;

			validateFields();
		}

		private void validateFields() {
			if (index < 0) {
				throw new IllegalArgumentException("index는 음수일 수 없습니다.");
			}

			if (value < 0 || value > 9) {
				throw new IllegalArgumentException("value 0과 9 사이의 수만 가능합니다.");
			}
		}

		private MatchType getMatchTypeByComparing(final NumberElement other) {
			if (this.equals(other)) {
				return MatchType.STRIKE;
			}

			if (value == other.value) {
				return MatchType.BALL;
			}

			return MatchType.NOTHING;
		}

		private static List<NumberElement> toNumberElements(final Collection<Integer> integers) {
			return new CustomImmutableCollection<>(integers).mapIndexed(NumberElement::new);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}

			NumberElement that = (NumberElement)obj;
			return index == that.index && value == that.value;
		}

		@Override
		public int hashCode() {
			return Objects.hash(index, value);
		}
	}

	private enum MatchType {
		STRIKE, BALL, NOTHING
	}
}
