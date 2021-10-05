package baseball.model.dto;

import java.util.StringJoiner;

public class MatchResult {
	private final int strikeCount;
	private final int ballCount;
	private final boolean fullyMatched;

	private final StringJoiner stringJoiner = new StringJoiner(" ");

	public MatchResult(final int strikeCount, final int ballCount, final boolean fullyMatched) {
		this.strikeCount = strikeCount;
		this.ballCount = ballCount;
		this.fullyMatched = fullyMatched;

		validateFields();
		setStringJoiner();
	}

	private void validateFields() {
		if (strikeCount < 0 || ballCount < 0) {
			throw new IllegalArgumentException("strikeCount, ballCount는 음수일 수 없습니다.");
		}
	}

	private void setStringJoiner() {
		stringJoiner.setEmptyValue("낫싱");

		if (strikeCount > 0) {
			stringJoiner.add(strikeCount + "스트라이크");
		}

		if (ballCount > 0) {
			stringJoiner.add(ballCount + "볼");
		}
	}

	public boolean isFullyMatched() {
		return fullyMatched;
	}

	public String toRecognizableString() {
		return stringJoiner.toString();
	}
}
