package pw.pfg.randomoresmod;

import java.util.ArrayList;
import java.util.List;
import com.swordglowsblue.artifice.api.Artifice;
import net.fabricmc.api.ModInitializer;
import pw.pfg.randomoresmod.modresource.ModResource;
import pw.pfg.randomoresmod.modresource.ModResourceOre;
import pw.pfg.randomoresmod.modresource.ResourceDetails;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.impl.client.render.ColorProviderRegistryImpl;
import net.fabricmc.loader.game.MinecraftGameProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.util.registry.Registry;

public class ExampleMod implements ModInitializer {
	// an instance of our new item
	public static final List<IRegisterable> THINGS = new ArrayList<>();

	static {
		for (int i = 0; i < 100; i++) {
			ExampleMod.THINGS.add(new ModResource(ResourceDetails.random(i)));
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
		for (IRegisterable thing : ExampleMod.THINGS) {
			thing.register();
		// Registry.register(Registry.ITEM, new Identifier("randomoresmod", item.id), item);
		// ColorProviderRegistry.ITEM.register(item);
		}

		Artifice.registerData(
			"randomoresmod:main",
			data -> {
				for (IRegisterable thing : ExampleMod.THINGS) {
					thing.registerData(data);
				}
			}
		);

		//Loop over existing biomes
		Registry.BIOME.forEach(biome -> ExampleMod.THINGS.forEach(i -> i.registerBiomeFeatures(biome)));

		//Listen for other biomes being registered
		RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> ExampleMod.THINGS.forEach(t -> t.registerBiomeFeatures(biome)));
	}
}
