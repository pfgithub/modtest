package pw.pfg.randomoresmod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.swordglowsblue.artifice.api.Artifice;
import org.apache.commons.lang3.text.WordUtils;
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
import pw.pfg.randomoresmod.modbiome.plant.TreeDetails;
import pw.pfg.randomoresmod.modbiome.plant.TreeNameGenerator;
import pw.pfg.randomoresmod.modbiome.plant.TreeRegisterer;
import pw.pfg.randomoresmod.modresource.ModResource;
import pw.pfg.randomoresmod.modresource.ResourceNameGenerator;
import pw.pfg.randomoresmod.modresource.ResourceDetails;

public class RandomOresMod implements ModInitializer {
	public static final List<IRegisterable> THINGS = new ArrayList<>();
	public static final List<IRegisterable> LIST_RESOURCES = new ArrayList<>();
	public static final List<IRegisterable> LIST_TREES = new ArrayList<>();
	public static final Item QUESTION_MARK_INGOT = new Item(new Item.Settings());
	public static final Item QUESTION_MARK_SAPLING = new Item(
		new Item.Settings()
	);
	public static final Item ENCHANTMENT_GLINT_TEST_TEXTURE = new EnchantedItem(
		new Item.Settings()
	);

	public static final ItemGroup RESOURCES = FabricItemGroupBuilder.create(
		new Identifier("randomoresmod", "resources")
	)
		.icon(() -> new ItemStack(QUESTION_MARK_INGOT))
		.appendItems(
			stacks -> {
				LIST_RESOURCES.forEach(t -> t.registerItemGroup(stacks));
			}
		)
		.build();

	public static final ItemGroup TREES = FabricItemGroupBuilder.create(
		new Identifier("randomoresmod", "trees")
	)
		.icon(() -> new ItemStack(QUESTION_MARK_SAPLING))
		.appendItems(
			stacks -> {
				LIST_TREES.forEach(t -> t.registerItemGroup(stacks));
			}
		)
		.build();

	static {
		RandomOresMod.THINGS.add(
			new ModBiome(BiomeDetails.random("acacia_log_forest"))
		);
		for (int i = 0; i < 255; i++) {
			IRegisterable thing = new ModResource(
				ResourceDetails.random(
					ResourceNameGenerator.INSTANCE.generateUnique(new Random(i))
				)
			);
			RandomOresMod.THINGS.add(thing);
			RandomOresMod.LIST_RESOURCES.add(thing);
		}
		String[] manualAdds = new String[] { "telhllium", "uuodiyium" };
		for (String manualAdd : manualAdds) {
			IRegisterable thing = new ModResource(ResourceDetails.random(manualAdd));
			RandomOresMod.THINGS.add(thing);
			RandomOresMod.LIST_RESOURCES.add(thing);
		}
		for (int i = 0; i < 255; i++) {
			String id = TreeNameGenerator.INSTANCE.generateUnique(new Random(i));
			System.out.println("Registering plant with id " + id);
			String englishName = WordUtils.capitalize(id.replace("_", " "));
			IRegisterable thing = new TreeRegisterer(
				TreeDetails.random(id, englishName)
			);
			RandomOresMod.THINGS.add(thing);
			RandomOresMod.LIST_TREES.add(thing);
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
			new Identifier("randomoresmod", "question_mark_sapling"),
			QUESTION_MARK_SAPLING
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
