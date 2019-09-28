package pw.pfg.randomoresmod.modresource;

import java.util.Random;
import pw.pfg.randomoresmod.NameGenerator;
import java.util.HashMap;

public class ResourceNameGenerator {
	static NameGenerator P1 = NameGenerator.list(
		"e",
		"a",
		"u",
		"o",
		"i",
		"io",
		"oi",
		"ou",
		"ee",
		"oo",
		"ia",
		"ea",
		"ao"
	);
	static NameGenerator P2 = NameGenerator.list(
		"l",
		"c",
		"t",
		"vw",
		"f",
		"x",
		"p",
		"pw",
		"pr",
		"gw",
		"bw",
		"br",
		"b"
	);
	static NameGenerator P3 = NameGenerator.list(
		"l",
		"c",
		"cw",
		"vw",
		"z",
		"x",
		"cr",
		"v",
		"qu",
		"m",
		"bw",
		"br",
		"n"
	);
	static NameGenerator P4 = NameGenerator.list(
		"st",
		"sk",
		"sh",
		"th",
		"f",
		"fr",
		"fw",
		"g",
		"gr",
		"gw",
		"j",
		"h",
		"vr"
	);
	static NameGenerator P5 = NameGenerator.list(
		"w",
		"r",
		"t",
		"tr",
		"tw",
		"y",
		"p",
		"pw",
		"pr",
		"s",
		"sr",
		"sp",
		"b"
	);
	static NameGenerator PIum = NameGenerator.list("ite", "ium");

	static NameGenerator MAIN = NameGenerator.order(
		NameGenerator.optional(P2),
		P1,
		P2,
		NameGenerator.optional(
			NameGenerator.order(P2, NameGenerator.optional(PIum))
		)
	);

	static HashMap<String, Boolean> generatedNames = new HashMap<>();

	public static String generate(int seed) {
		String finalName = MAIN.generate(new Random(seed));
		while (generatedNames.getOrDefault(finalName, new Boolean(false))) {
			finalName += "_";
		}
		generatedNames.put(finalName, new Boolean(true));
		return finalName;
	}
}
