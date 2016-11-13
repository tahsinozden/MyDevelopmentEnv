package streamapi;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

public class TestStreams {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Stream<String> s1 = Stream.empty();
		
		Stream<Integer> allNumbers = Stream.of(342, 424, 11, 53, 4564);
		Optional<Integer> number = allNumbers.min((n1, n2) -> n1 - n2);
		number.ifPresent(System.out::println);
		
		// start counting from 1
		Stream<Integer> countingNumbers = Stream.iterate(1, n1 -> n1 + 1);
		countingNumbers = countingNumbers.limit(100);
		countingNumbers.forEach(System.out::println);

		Stream<String> animals = Stream.generate(() -> "monkey" );
		animals.findAny().ifPresent(System.out::println);
		
		
		Stream<String> wordStream = Stream.of("Tahsin", "Ozden", "was", "here");
		String word = wordStream.reduce("", (s, s2) -> s + s2);
		System.out.println(word);
		
		List<String> someWords = Arrays.asList("my", "name", "nnn", "master");
		Stream<String> someWordsStream = someWords.stream();
		someWordsStream.filter(str -> str.startsWith("n")).forEach(System.out::println);
		
		Stream<String> stringNumbers = Stream.of("42", "4", "475675", "-2");
//		stringNumbers.map(strNum -> Integer.parseInt(strNum)).forEach(System.out::println);
		List<Integer> convertedNumbers = stringNumbers.map(strNum -> Integer.parseInt(strNum))
				.sorted()
				.collect(Collectors.toList());
		for (Integer integer : convertedNumbers) {
			System.out.println(integer);
		}
	}
	

}
