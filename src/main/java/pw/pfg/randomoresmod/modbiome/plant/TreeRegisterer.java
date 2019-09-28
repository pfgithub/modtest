package pw.pfg.randomoresmod.modbiome.plant;

import java.util.ArrayList;
import java.util.List;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.biome.Biome;
import pw.pfg.randomoresmod.IRegisterable;

public class TreeRegisterer implements IRegisterable {
	TreeDetails resource;

	final List<IRegisterable> THINGS = new ArrayList<>();

	public TreeRegisterer(TreeDetails details) {
		this.resource = details;

		this.THINGS.add(new TreeLogBlock(this.resource));
		this.THINGS.add(new TreeLeavesBlock(this.resource));
		this.THINGS.add(new TreePlankBlock(this.resource));
	}

	@Override
	public void registerTranslations(TranslationBuilder trans) {
		trans.entry(resource.translationKey, resource.englishName);

		for (IRegisterable registerable : this.THINGS) {
			registerable.registerTranslations(trans);
		}
	}

	@Override
	public void register() {
		for (IRegisterable registerable : this.THINGS) {
			registerable.register();
		}
	}

	@Override
	public void registerData(ServerResourcePackBuilder data) {
		for (IRegisterable registerable : this.THINGS) {
			registerable.registerData(data);
		}
	}

	@Override
	public void registerAssets(ClientResourcePackBuilder pack) {
		for (IRegisterable registerable : this.THINGS) {
			registerable.registerAssets(pack);
		}
	}

	@Override
	public void registerClient() {
		for (IRegisterable registerable : this.THINGS) {
			registerable.registerClient();
		}
	}

	@Override
	public void registerBiomeFeatures(Biome biome) {
		for (IRegisterable registerable : this.THINGS) {
			registerable.registerBiomeFeatures(biome);
		}
	}

	@Override
	public void registerItemGroup(List<ItemStack> stacks) {
		this.THINGS.forEach(t -> t.registerItemGroup(stacks));
		while (stacks.size() % 9 != 0) {
			stacks.add(new ItemStack(Items.AIR));
		}
	}
}
