package pw.pfg.randomoresmod;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

class StringNameGenerator extends NameGenerator {
	String string;

	public StringNameGenerator(String string){
		this.string = string;
	}

	static StringNameGenerator from(String string) {
		return new StringNameGenerator(string);
	}

	@Override
	public String generate(Random random) {
		return string;
	}

}

class RandomFromListNameGenerator extends NameGenerator {
	NameGenerator[] names;

	public RandomFromListNameGenerator(NameGenerator[] names) {
		super();
		this.names = names;
	}

	static RandomFromListNameGenerator from(Object... names) {
		NameGenerator[] resultNames = new NameGenerator[names.length];
		int i = 0;
		for (Object o : names) {
			if (o instanceof NameGenerator) { // this should be a type guard
				resultNames[i] = (NameGenerator) o;
			} else if (o instanceof String) {
				resultNames[i] = StringNameGenerator.from((String) o);
			} else {
				assert false;
			}
			i++;
		}
		return new RandomFromListNameGenerator(resultNames);
	}

	@Override
	public String generate(Random random) {
		return names[random.nextInt(names.length - 1)].generate(random);
	}
}

class OrderedNameGenerator extends NameGenerator {
	NameGenerator[] names;

	public OrderedNameGenerator(NameGenerator[] names) {
		super();
		this.names = names;
	}

	static OrderedNameGenerator from(Object... names) {
		NameGenerator[] resultNames = new NameGenerator[names.length];
		int i = 0;
		for (Object o : names) {
			if (o instanceof NameGenerator) { // this should be a type guard
				resultNames[i] = (NameGenerator) o;
			} else if (o instanceof String) {
				resultNames[i] = StringNameGenerator.from((String) o);
			} else {
				assert false;
			}
			i++;
		}
		return new OrderedNameGenerator(resultNames);
	}

	@Override
	public String generate(Random random) {
		List<String> result = new ArrayList<>();
		for(NameGenerator generator : names){
			result.add(generator.generate(random));
		}
		return String.join("", result); 
	}
}

public abstract class NameGenerator {
	static NameGenerator EMPTY_GENERATOR = StringNameGenerator.from("");

	public NameGenerator() {}

	public static StringNameGenerator string(String string){
		return StringNameGenerator.from(string);
	}
	public static RandomFromListNameGenerator optional(String string){
		return RandomFromListNameGenerator.from(string, EMPTY_GENERATOR);
	}
	public static RandomFromListNameGenerator optional(NameGenerator nameGenerator){
		return RandomFromListNameGenerator.from(nameGenerator, EMPTY_GENERATOR);
	}
	public static RandomFromListNameGenerator list(Object... names){
		return RandomFromListNameGenerator.from(names);
	}
	public static OrderedNameGenerator order(Object... values){
		return OrderedNameGenerator.from(values);
	}

	public abstract String generate(Random random);
}
