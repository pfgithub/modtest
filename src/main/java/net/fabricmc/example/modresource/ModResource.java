package net.fabricmc.example.modresource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.example.IRegisterable;

public class ModResource implements IRegisterable {
	ResourceDetails details;

	final List<IRegisterable> THINGS = new ArrayList<>();

	public ModResource(ResourceDetails details) {
		this.details = details;

		this.THINGS.add(new ModResourceOre(this.details));
	}

	@Override
	public void registerTranslations(TranslationBuilder trans) {
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
}
