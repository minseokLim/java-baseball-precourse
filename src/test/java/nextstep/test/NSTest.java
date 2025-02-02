package nextstep.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import org.assertj.core.util.Strings;

public abstract class NSTest {
	private PrintStream standardOut;
	private OutputStream captor;

	protected void setUp() {
		standardOut = System.out;
		captor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(captor));
	}

	/**
	 * 사용자의 입력을 기다리는 상황에서 테스트 종료
	 * @param args
	 */
	protected void running(final String... args) {
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(
				() -> subject(args)
		);
	}

	/**
	 * 프로그램이 정상적으로 종료
	 * @param args
	 */
	protected void run(final String... args) {
		subject(args);
	}

	protected void verify(final String... args) {
		assertThat(output()).contains(args);
	}

	private void subject(final String... args) {
		command(args);
		runMain();
	}

	public abstract void runMain();

	private void command(final String... args) {
		final byte[] buf = Strings.join(args).with("\n").getBytes();
		System.setIn(new ByteArrayInputStream(buf));
	}

	protected String output() {
		return captor.toString().trim();
	}

	protected void outputStandard() {
		System.setOut(standardOut);
		System.out.println(output());
	}
}
