package baseball.model.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import baseball.model.AnswerGenerator;
import nextstep.utils.Randoms;

public class AnswerGeneratorImpl implements AnswerGenerator {
	private final int answerLength;
	private final int startInclusive;
	private final int endInclusive;

	private AnswerGeneratorImpl(final int answerLength, final int startInclusive, final int endInclusive) {
		this.answerLength = answerLength;
		this.startInclusive = startInclusive;
		this.endInclusive = endInclusive;

		validateFields();
	}

	private void validateFields() {
		validateAnswerLength();
		validateAnswerRange();
		validateAnswerCanBeGenerated();
	}

	private void validateAnswerLength() {
		if (answerLength <= 0) {
			throw new IllegalArgumentException("정답의 길이는 양수여야 합니다.");
		}
	}

	private void validateAnswerRange() {
		if (startInclusive > endInclusive) {
			throw new IllegalArgumentException("startInclusive가 endInclusive보다 클 수 없습니다.");
		}

		if (startInclusive < 0 || startInclusive > 9 || endInclusive < 0 || endInclusive > 9) {
			throw new IllegalArgumentException("startInclusive와 endInclusive는 0과 9 사이의 수만 가능합니다.");
		}
	}

	private void validateAnswerCanBeGenerated() {
		if (endInclusive - startInclusive + 1 < answerLength) {
			throw new IllegalArgumentException("정답은 서로 다른 수로 이루어져야하기 때문에, "
				+ "endInclusive - startInclusive + 1 >= answerLength 여야 합니다.");
		}
	}

	@Override
	public List<Integer> getAnswerNumbers() {
		Set<Integer> answerNumbers = new LinkedHashSet<>();

		while (answerNumbers.size() < answerLength) {
			int extracted = Randoms.pickNumberInRange(startInclusive, endInclusive);
			answerNumbers.add(extracted);
		}

		return new ArrayList<>(answerNumbers);
	}

	public static class AnswerGeneratorBuilder {
		private int answerLength;
		private int startInclusive;
		private int endInclusive;

		public AnswerGeneratorBuilder withAnswerLength(final int answerLength) {
			this.answerLength = answerLength;
			return this;
		}

		public AnswerGeneratorBuilder withRange(final int startInclusive, final int endInclusive) {
			this.startInclusive = startInclusive;
			this.endInclusive = endInclusive;
			return this;
		}

		public AnswerGenerator build() {
			return new AnswerGeneratorImpl(answerLength, startInclusive, endInclusive);
		}
	}
}
