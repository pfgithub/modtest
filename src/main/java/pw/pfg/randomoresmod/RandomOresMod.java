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
import pw.pfg.randomoresmod.modresource.ModResource;
import pw.pfg.randomoresmod.modresource.NameGenerator;
import pw.pfg.randomoresmod.modresource.ResourceDetails;

public class RandomOresMod implements ModInitializer {
	// an instance of our new item
	public static final List<IRegisterable> THINGS = new ArrayList<>();
	public static final Item QUESTION_MARK_INGOT = new Item(
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
		for (int i = 0; i < 255; i++) {
			RandomOresMod.THINGS.add(
				new ModResource(ResourceDetails.random(NameGenerator.generate(i)))
			);
		}
		RandomOresMod.THINGS.add(new ModResource(ResourceDetails.random("chest")));
		RandomOresMod.THINGS.add(new ModResource(ResourceDetails.random("cow")));
		RandomOresMod.THINGS.add(new ModResource(ResourceDetails.random("pig")));
		RandomOresMod.THINGS.add(
			new ModResource(ResourceDetails.random("creeper"))
		);
		RandomOresMod.THINGS.add(new ModResource(ResourceDetails.random("aw_man")));
		RandomOresMod.THINGS.add(
			new ModResource(ResourceDetails.random("enderman"))
		);
		RandomOresMod.THINGS.add(
			new ModResource(ResourceDetails.random("sub_one_and_a_half"))
		);
		RandomOresMod.THINGS.add(new ModResource(ResourceDetails.random("human")));
		RandomOresMod.THINGS.add(new ModResource(ResourceDetails.random("bot")));
		RandomOresMod.THINGS.add(
			new ModResource(ResourceDetails.random("derpidude"))
		);
		RandomOresMod.THINGS.add(new ModResource(ResourceDetails.random("pfg")));
		RandomOresMod.THINGS.add(
			new ModResource(ResourceDetails.random("howdyman"))
		);
	}

	@Override
	public void onInitialize() {
		RandomOresMod.THINGS.forEach(i -> i.register());

		Registry.register(
			Registry.ITEM,
			new Identifier("randomoresmod", "question_mark_ingot"),
			QUESTION_MARK_INGOT
		);

		Artifice.registerData(
			"randomoresmod:main",
			data -> {
				for (IRegisterable thing : RandomOresMod.THINGS) {
					thing.registerData(data);
				}
			}
		);

		//Loop over existing biomes
		Registry.BIOME.forEach(biome -> RandomOresMod.THINGS.forEach(i -> i.registerBiomeFeatures(biome)));

		//Listen for other biomes being registered
		RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> RandomOresMod.THINGS.forEach(t -> t.registerBiomeFeatures(biome)));
	}
}
