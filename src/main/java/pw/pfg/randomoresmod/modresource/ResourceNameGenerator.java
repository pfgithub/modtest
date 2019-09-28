package pw.pfg.randomoresmod.modresource;

import java.util.Random;
import pw.pfg.randomoresmod.NameGenerator;

public class ResourceNameGenerator extends NameGenerator {
	static final NameGenerator P1 = list(
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
	static final NameGenerator P2 = list(
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
	static final NameGenerator P3 = list(
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
	static final NameGenerator P4 = list(
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
	static final NameGenerator P5 = list(
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
	static final NameGenerator PIum = list("ite", "ium");

	static final NameGenerator MAIN = order(
		optional(P2),
		P1,
		P2,
		optional(order(P2, optional(PIum)))
	);

	public static final ResourceNameGenerator INSTANCE = new ResourceNameGenerator();

	@Override
	public String generate(Random random) {
		return MAIN.generate(random);
	}
}
