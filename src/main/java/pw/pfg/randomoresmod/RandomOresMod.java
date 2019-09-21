package pw.pfg.randomoresmod;

import java.util.ArrayList;
import java.util.List;
import com.swordglowsblue.artifice.api.Artifice;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import pw.pfg.randomoresmod.modresource.ModResource;
import pw.pfg.randomoresmod.modresource.ResourceDetails;

public class RandomOresMod implements ModInitializer {
	// an instance of our new item
	public static final List<IRegisterable> THINGS = new ArrayList<>();

	static {
		for (int i = 0; i < 100; i++) {
			RandomOresMod.THINGS.add(new ModResource(ResourceDetails.random(i)));
		}
	}

	public static final ItemGroup OTHER_GROUP = FabricItemGroupBuilder.create(
		new Identifier("tutorial", "other")
	)
		.icon(() -> new ItemStack(Items.APPLE))
		.appendItems(
			stacks -> {
				stacks.add(new ItemStack(Items.APPLE));
			}
		)
		.build();

	@Override
	public void onInitialize() {
		for (IRegisterable thing : RandomOresMod.THINGS) {
			thing.register();
		// Registry.register(Registry.ITEM, new Identifier("randomoresmod", item.id), item);
		// ColorProviderRegistry.ITEM.register(item);
		}

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
