package lambdas;

import java.util.function.*;

import org.junit.Before;
import org.junit.Test;

public class TestLambdas {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Consumer<String> p = System.out::println;
		p.accept("print using lamdba!");
		
		Consumer<String> cons = x -> System.out.println(x);
		cons.accept("now the same but different!");
		
		BiConsumer<Integer, Integer> max =  (n1, n2) -> { System.out.println((n1 > n2 ? n1 : n2) + " is greater!"); };
		max.accept(213, 1);
		
		Predicate<String> stringEqual = x -> x.equals("mydata");
		System.out.println(stringEqual.test("mew"));
		System.out.println(stringEqual.test("mydata"));
		
		Predicate<String> checkEmpty = String::isEmpty;
		System.out.println(checkEmpty.test(""));
		System.out.println(checkEmpty.test("da"));
		
		Predicate<String> egg = x -> x.contains("egg");
		Predicate<String> brown = c -> c.contains("brown");
		Predicate<String> brownEgg = egg.and(brown);
		
		System.out.println("brown egg : " + brownEgg.test("brown egg"));
		System.out.println("yellow egg : " + brownEgg.test("yellow egg"));
	}

}
