package net.fabricmc.example.modresource;

import java.util.List;
import java.util.Random;
import java.util.Arrays;

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

	static String generate(int seed) {
		Random random = new Random(seed);
		String p1 = P1.get(random.nextInt(P1.size()));
		String p2 = P2.get(random.nextInt(P2.size()));
		String p3 = P3.get(random.nextInt(P3.size()));
		String p4 = P4.get(random.nextInt(P4.size()));
		String p5 = P5.get(random.nextInt(P5.size()));
		return p1 + p2 + p3 + p4 + p5;
	}
}
