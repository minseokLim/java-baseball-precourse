package baseball.model;

import java.util.List;

import baseball.model.dto.MatchResult;

public interface AnswerMatcher {
	MatchResult matches(List<Integer> inputNumbers);
}
