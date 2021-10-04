package baseball.controller.response;

public class MatchResultResponse {
	public final String result;
	public final boolean fullyMatched;

	public MatchResultResponse(final String result, final boolean fullyMatched) {
		this.result = result;
		this.fullyMatched = fullyMatched;
	}
}
