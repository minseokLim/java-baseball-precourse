package baseball.controller;

import static baseball.Application.*;

import java.util.List;
import java.util.regex.Pattern;

import baseball.controller.response.MatchResultResponse;
import baseball.exception.BadRequestException;
import baseball.model.AnswerGenerator;
import baseball.model.AnswerMatcher;
import baseball.model.dto.MatchResult;
import baseball.model.impl.AnswerGeneratorImpl.AnswerGeneratorBuilder;
import baseball.model.impl.AnswerMatcherImpl.AnswerMatcherBuilder;
import baseball.repository.AnswerRepository;
import baseball.util.CustomImmutableCollection;

public class BaseballController {
	private final AnswerGenerator generator =
		new AnswerGeneratorBuilder().withAnswerLength(ANSWER_LENGTH).withRange(START_NUMBER, END_NUMBER).build();

	private final AnswerRepository answerRepository = new AnswerRepository();

	private static final Pattern USER_INPUT_PATTERN = Pattern.compile("\\d{" + ANSWER_LENGTH + "}");

	public String generateAnswerNumbers() {
		List<Integer> answerNumbers = generator.getAnswerNumbers();

		return answerRepository.save(answerNumbers);
	}

	public MatchResultResponse getMatchResult(String input, String gameId) {
		List<Integer> inputNumbers = getInputNumbers(input);
		List<Integer> answerNumbers = answerRepository.getById(gameId);

		AnswerMatcher answerMatcher = new AnswerMatcherBuilder().withAnswerNumbers(answerNumbers).build();

		MatchResult matchResult = answerMatcher.matches(inputNumbers);

		return new MatchResultResponse(matchResult.toRecognizableString(), matchResult.isFullyMatched());
	}

	public void removeGame(String gameId) {
		answerRepository.deleteById(gameId);
	}

	private List<Integer> getInputNumbers(String input) {
		validateInput(input);

		return new CustomImmutableCollection<>(input.split("")).map(Integer::parseInt);
	}

	private static void validateInput(String input) {
		validateNumberPattern(input);

		List<Integer> inputNumbers = new CustomImmutableCollection<>(input.split("")).map(Integer::parseInt);

		for (int number : inputNumbers) {
			validateRange(number);
		}

		validateDistinctness(inputNumbers);
	}

	private static void validateNumberPattern(final String input) {
		if (!USER_INPUT_PATTERN.matcher(input).matches()) {
			throw new BadRequestException(ANSWER_LENGTH + "자리 수를 입력해주세요.");
		}
	}

	private static void validateRange(final int number) {
		if (number < START_NUMBER || number > END_NUMBER) {
			throw new BadRequestException(START_NUMBER + "에서 " + END_NUMBER + "까지의 수만 입력해주세요.");
		}
	}

	private static void validateDistinctness(final List<Integer> inputNumbers) {
		boolean distinct = new CustomImmutableCollection<>(inputNumbers).isDistinct();

		if (!distinct) {
			throw new BadRequestException("서로 다른 수를 입력해주세요.");
		}
	}
}
