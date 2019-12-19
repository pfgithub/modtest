package pw.pfg.randomoresmod.modresource;

import java.util.List;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import pw.pfg.randomoresmod.IRegisterable;
import pw.pfg.randomoresmod.ObjectDetails;
import pw.pfg.randomoresmod.RegistrationHelper;
import pw.pfg.randomoresmod.TextureInfo;

public interface RegisterableBlockDefaults extends IRegisterable {
	public ObjectDetails getResource();
	public TextureInfo getTexture();
	public Item getItem();
	public Block getBlock();

	@Override
	default public void registerTranslations(TranslationBuilder trans) {}

	@Override
	default public void registerData(ServerResourcePackBuilder data) {}

	@Override
	default public void registerAssets(ClientResourcePackBuilder pack) {
		RegistrationHelper.registerModels(
			pack,
			getTexture(),
			model -> {
				RegistrationHelper.registerDefaultBlockModel(model, getTexture());
			}
		);
	}

	@Override
	default public void register() {
		RegistrationHelper.register(getTexture().id, getBlock(), getItem());
	}

	@Override
	default public void registerClient() {
		RegistrationHelper.registerColorProvider(
			getBlock(),
			getItem(),
			getResource()
		);
	}

	@Override
	default public void registerBiomeFeatures(Biome biome) {}

	@Override
	default public void registerItemGroup(List<ItemStack> stacks) {
		stacks.add(new ItemStack(getBlock()));
	}
}
