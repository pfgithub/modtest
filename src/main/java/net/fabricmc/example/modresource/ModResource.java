package net.fabricmc.example.modresource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.example.IRegisterable;
import net.minecraft.world.biome.Biome;

public class ModResource implements IRegisterable {
	ResourceDetails resource;

	final List<IRegisterable> THINGS = new ArrayList<>();

	public ModResource(ResourceDetails details) {
		this.resource = details;

		this.THINGS.add(new ModResourceOre(this.resource));
		this.THINGS.add(new ModResourceGem(this.resource));
	}

	@Override
	public void registerTranslations(TranslationBuilder trans) {
		trans.entry(resource.name, resource.englishName);

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
}
