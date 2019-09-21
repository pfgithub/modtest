package pw.pfg.randomoresmod;

import com.swordglowsblue.artifice.api.Artifice;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class ExampleClientModInitializer implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		System.out.println("Client Mod Initializer Loadede.");

		// Redstone is now the same color as grass
		ColorProviderRegistry.BLOCK.register(
			(block, world, pos, layer) -> {
				BlockColorProvider provider = ColorProviderRegistry.BLOCK.get(
					Blocks.GRASS
				);
				return provider == null ? -1
					: provider.getColor(block, world, pos, layer);
			},
			Blocks.REDSTONE_WIRE
		);

		// Make white dye glow red.
		ColorProviderRegistry.ITEM.register((item, layer) -> (int) (64 * (Math.sin(System.currentTimeMillis() / 5e2) + 3)) << 16, Items.WHITE_DYE);

		for (IRegisterable thing : ExampleMod.THINGS) {
			thing.registerClient();
		}

		Artifice.registerAssets(
			"randomoresmod:main",
			pack -> {
				for (IRegisterable thing : ExampleMod.THINGS) {
					thing.registerAssets(pack);
				}
				pack.addTranslations(
					new Identifier("randomoresmod", "en_us"),
					trans -> {
						for (IRegisterable thing : ExampleMod.THINGS) {
							thing.registerTranslations(trans);
						}
					}
				);
			}
		);
	}
}
