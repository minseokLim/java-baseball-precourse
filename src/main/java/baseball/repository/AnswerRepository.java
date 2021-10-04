package baseball.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AnswerRepository {
	private final Map<String, List<Integer>> idToAnswerNumbers = new ConcurrentHashMap<>();

	public List<Integer> getById(String id) {
		return idToAnswerNumbers.get(id);
	}

	public String save(List<Integer> answerNumbers) {
		String id = UUID.randomUUID().toString();

		idToAnswerNumbers.put(id, answerNumbers);

		return id;
	}

	public void deleteById(String id) {
		idToAnswerNumbers.remove(id);
	}
}
