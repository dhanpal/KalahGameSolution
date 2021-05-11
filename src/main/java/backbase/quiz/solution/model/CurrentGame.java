package backbase.quiz.solution.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CurrentGame {
	private String id;
	private String uri;
	private Map<Integer, String> status;

}
