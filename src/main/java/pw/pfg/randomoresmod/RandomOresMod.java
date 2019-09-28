package pw.pfg.randomoresmod;

import java.util.ArrayList;
import java.util.List;
import com.swordglowsblue.artifice.api.Artifice;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import pw.pfg.randomoresmod.modbiome.BiomeDetails;
import pw.pfg.randomoresmod.modbiome.ModBiome;
import pw.pfg.randomoresmod.modresource.ModResource;
import pw.pfg.randomoresmod.modresource.ResourceNameGenerator;
import pw.pfg.randomoresmod.modresource.ResourceDetails;

public class RandomOresMod implements ModInitializer {
	public static final List<IRegisterable> THINGS = new ArrayList<>();
	public static final Item QUESTION_MARK_INGOT = new Item(
		new Item.Settings().group(ItemGroup.MATERIALS)
	);
	public static final Item ENCHANTMENT_GLINT_TEST_TEXTURE = new EnchantedItem(
		new Item.Settings().group(ItemGroup.MATERIALS)
	);

	public static final ItemGroup RESOURCES = FabricItemGroupBuilder.create(
		new Identifier("randomoresmod", "resources")
	)
		.icon(() -> new ItemStack(QUESTION_MARK_INGOT))
		.appendItems(
			stacks -> {
				THINGS.forEach(t -> t.registerItemGroup(stacks));
			}
		)
		.build();

	static {
		RandomOresMod.THINGS.add(
			new ModBiome(BiomeDetails.random("acacia_log_forest"))
		);
		for (int i = 0; i < 255; i++) {
			RandomOresMod.THINGS.add(
				new ModResource(
					ResourceDetails.random(ResourceNameGenerator.generate(i))
				)
			);
		}
		String[] manualAdds = new String[] { "telhllium", "uuodiyium" };
		for (String manualAdd : manualAdds) {
			RandomOresMod.THINGS.add(
				new ModResource(ResourceDetails.random(manualAdd))
			);
		}
	}

	@Override
	public void onInitialize() {
		RandomOresMod.THINGS.forEach(i -> i.register());

		Registry.register(
			Registry.ITEM,
			new Identifier("randomoresmod", "question_mark_ingot"),
			QUESTION_MARK_INGOT
		);
		Registry.register(
			Registry.ITEM,
			new Identifier("randomoresmod", "enchantment_glint_test_texture"),
			ENCHANTMENT_GLINT_TEST_TEXTURE
		);

		Artifice.registerData(
			"randomoresmod:main",
			data -> {
				for (IRegisterable thing : RandomOresMod.THINGS) {
					thing.registerData(data);
				}
			}
		);

		Registry.BIOME.forEach(
			biome -> RandomOresMod.THINGS.forEach(i -> i.registerBiomeFeatures(biome))
		);
		RegistryEntryAddedCallback.event(Registry.BIOME)
			.register(
				(i, identifier, biome) -> RandomOresMod.THINGS.forEach(
					t -> t.registerBiomeFeatures(biome)
				)
			);
	}
}
