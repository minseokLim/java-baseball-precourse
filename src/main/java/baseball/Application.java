package baseball;

import baseball.controller.BaseballController;
import baseball.controller.response.MatchResultResponse;
import baseball.exception.BadRequestException;
import nextstep.utils.Console;

public class Application {
	public static final int ANSWER_LENGTH = 3;
	public static final int START_NUMBER = 1;
	public static final int END_NUMBER = 9;

	private static final BaseballController CONTROLLER = new BaseballController();

	public static void main(String[] args) {
		for (boolean exitFlag = false; !exitFlag; ) {
			String gameId = CONTROLLER.generateAnswerNumbers();
			startGame(gameId);
			exitFlag = getExitFlag();
		}
	}

	private static void startGame(String gameId) {
		for (boolean matchFlag = false; !matchFlag; ) {
			matchFlag = getMatchFlagFromUserInput(gameId);
		}

		CONTROLLER.removeGame(gameId);
	}

	private static boolean getMatchFlagFromUserInput(String gameId) {
		try {
			String input = getInputNumberFromUser();
			MatchResultResponse matchResult = CONTROLLER.getMatchResult(input, gameId);
			System.out.println(matchResult.result);
			return matchResult.fullyMatched;
		} catch (BadRequestException e) {
			System.out.println(e.getMessage());
			return getMatchFlagFromUserInput(gameId);
		}
	}

	private static String getInputNumberFromUser() {
		System.out.print("숫자를 입력해주세요 : ");
		return Console.readLine().trim();
	}

	private static boolean getExitFlag() {
		try {
			String input = getExitFlagStrFromUser();
			validateInput(input);

			return "2".equals(input);
		} catch (BadRequestException e) {
			System.out.println(e.getMessage());
			return getExitFlag();
		}
	}

	private static String getExitFlagStrFromUser() {
		System.out.println(ANSWER_LENGTH + "개의 숫자를 모두 맞히셨습니다! 게임 끝");
		System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
		return Console.readLine().trim();
	}

	private static void validateInput(String input) {
		if (!"1".equals(input) && !"2".equals(input)) {
			throw new BadRequestException("게임 종료 여부를 정하기 위해 1과 2만 입력해주세요.");
		}
	}
}
