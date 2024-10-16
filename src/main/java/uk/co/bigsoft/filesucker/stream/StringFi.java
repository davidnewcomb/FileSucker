package uk.co.bigsoft.filesucker.stream;

import java.util.function.Function;
import java.util.function.Predicate;

public class StringFi {

	public static Predicate<String> notEmpty = (s) -> !"".equals(s);
	public static Function<String, String> trim = (s) -> s.trim();

}
