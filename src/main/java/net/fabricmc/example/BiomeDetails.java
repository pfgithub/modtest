package net.fabricmc.example;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BiomeDetails {
	static List<String> ORE_BASE = Arrays.asList("minecraft:block/stone");
	static List<String> ORE_OVERLAY = Arrays.asList(
		"modid:block/generic_overlay",
		"modid:block/lapis_overlay",
		"modid:block/emerald_overlay",
		"modid:block/vein_overlay"
	);
	static List<String> RESOURCE_BASE = Arrays.asList(
		"minecraft:item/iron_ingot",
		"minecraft:item/emerald",
		"minecraft:item/redstone_dust",
		"minecraft:item/lapis_lazuli",
		"minecraft:item/spawn_egg_overlay"
	);

	static BiomeDetails random(int seed) {
		Random random = new Random(seed);

		return new BiomeDetails(
		);
	}

	private BiomeDetails() {
	}
}
