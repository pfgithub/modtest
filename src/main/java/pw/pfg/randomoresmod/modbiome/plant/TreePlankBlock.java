package pw.pfg.randomoresmod.modbiome.plant;

import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import pw.pfg.randomoresmod.ColoredBlock;
import pw.pfg.randomoresmod.RandomOresMod;

public class TreePlankBlock extends ColoredBlock {
	TreeDetails resource;
	Item blockItem;

	public TreePlankBlock(TreeDetails resource) {
		super(
			resource,
            resource.planks,
			FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).build(),
			new Item.Settings()
				.group(RandomOresMod.RESOURCES)
		);
		this.resource = resource;
	}

	@Override
	public void registerData(ServerResourcePackBuilder data) {
		super.registerData(data);
		data.addLootTable(
			new Identifier("randomoresmod", "blocks/" + this.id),
			ltp -> {
				ltp.type(new Identifier("randomoresmod", "block/" + this.id))
					.pool(
						pool -> {
							pool.rolls(1);
							pool.entry(
								e -> {
									e.name(new Identifier("randomoresmod", this.id))
										.type(new Identifier("minecraft:item"));
								}
							);
							pool.condition(
								new Identifier("minecraft:survives_explosion"),
								cond -> {}
							);
						}
					);
			}
		);
	}
}
