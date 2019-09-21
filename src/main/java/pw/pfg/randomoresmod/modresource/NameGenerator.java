package pw.pfg.randomoresmod.modresource;

import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.util.HashMap;

public class NameGenerator {
	static List<String> P1 = Arrays.asList(
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
	static List<String> P2 = Arrays.asList(
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
	static List<String> P3 = Arrays.asList(
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
	static List<String> P4 = Arrays.asList(
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
	static List<String> P5 = Arrays.asList(
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
	static List<String> PIum = Arrays.asList(
		"ite",
		"ium"
	);

	static HashMap<String, Boolean> generatedNames = new HashMap<>();

	static String generate(int seed) {
		Random random = new Random(seed);
		String ae = random.nextBoolean() ? P2.get(random.nextInt(P2.size())) : "";
		String af = P1.get(random.nextInt(P1.size()));
		String ag = P2.get(random.nextInt(P2.size()));
		String ah = random.nextBoolean() ? P1.get(random.nextInt(P1.size())) : "";
		String ai = ah != "" && random.nextBoolean() ? P2.get(random.nextInt(P2.size())) : "";
		String aj = ai != "" && random.nextBoolean() ? PIum.get(random.nextInt(PIum.size())) : "";
		String finalName = ae + af + ag + ah + ai + aj;
		while (generatedNames.getOrDefault(finalName, new Boolean(false))) {
			finalName += "_";
		}
		generatedNames.put(finalName, new Boolean(true));
		return finalName;
	}
}
