package baseball;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import nextstep.test.NSTest;
import nextstep.utils.Randoms;

public class ApplicationTest extends NSTest {
	@BeforeEach
	void beforeEach() {
		super.setUp();
	}

	@Test
	void 낫싱() {
		try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
			mockRandoms
				.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
				.thenReturn(1, 3, 5);
			running("246");
			verify("낫싱");
		}
	}

	@Test
	void 게임종료_후_재시작() {
		try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
			mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
				.thenReturn(7, 1, 3)
				.thenReturn(5, 8, 9);
			run("713", "1", "597", "589", "2");
			verify("3스트라이크", "게임 끝", "1스트라이크 1볼");
		}
	}

	@Test
	void 유효하지_않은_사용자_입력() {
		try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
			mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
				.thenReturn(7, 1, 3);
			running("1234", "109", "131", "713", "3");
			verify("[ERROR]", "3자리 수를 입력해주세요.", "1에서 9까지의 수만 입력해주세요.",
				"서로 다른 수를 입력해주세요.", "게임 종료 여부를 정하기 위해 1과 2만 입력해주세요.");
		}
	}

	@Test
	void 원볼() {
		testSingleMatchResult("246", "1볼");
	}

	@Test
	void 투볼() {
		testSingleMatchResult("216", "2볼");
	}

	@Test
	void 쓰리볼() {
		testSingleMatchResult("231", "3볼");
	}

	@Test
	void 원스트라이크() {
		testSingleMatchResult("145", "1스트라이크");
	}

	@Test
	void 원스트라이크_원볼() {
		testSingleMatchResult("152", "1스트라이크 1볼");
	}

	@Test
	void 원스트라이크_투볼() {
		testSingleMatchResult("132", "1스트라이크 2볼");
	}

	@Test
	void 투스트라이크() {
		testSingleMatchResult("126", "2스트라이크");
	}

	@Test
	void 쓰리스트라이크() {
		testSingleMatchResult("123", "3스트라이크");
	}

	private void testSingleMatchResult(final String input, final String output) {
		try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
			mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
				.thenReturn(1, 2, 3);
			running(input);
			verify(output);
		}
	}

	@AfterEach
	void tearDown() {
		outputStandard();
	}

	@Override
	public void runMain() {
		Application.main(new String[] {});
	}
}
