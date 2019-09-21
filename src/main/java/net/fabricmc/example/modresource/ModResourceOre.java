package net.fabricmc.example.modresource;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.example.IRegisterable;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class ModResourceOre extends OreBlock implements IRegisterable {
	ResourceDetails resource;
	String id;
	Item blockItem;

	public ModResourceOre(ResourceDetails resource) {
		// new OreBlock(Block.Settings.of(Material.STONE).strength(3.0F, 3.0F)))
		super(
			Block.Settings.of(Material.STONE)
				.strength(resource.materialHardness, resource.materialResistance)
		);
		this.resource = resource;
		this.id = resource.id + "_ore";
		this.blockItem =
			new BlockItem(this, new Item.Settings().group(ItemGroup.MATERIALS));
	}

	// public void registerData(ServerResourcePackBuilder pack) {}
	@Override
	public void registerTranslations(TranslationBuilder trans) {
		trans.entry(
			"block.modid." + this.id,
			this.resource.name +
				" Ore (" +
				this.resource.materialHardness +
				" - " +
				this.resource.materialResistance +
				")"
		);
	}

	// public void registerAssets(ClientResourcePackBuilder pack) {
	// 	// pack.addItemModel(id, f);
	// // pack.addTranslations(new Identifier("modid", "en_US"), trans -> {});
	// // todo: do a thing that adds a slab for every block as an easy example test
	// }
	@Override
	public void register() {
		Registry.register(Registry.BLOCK, new Identifier("modid", this.id), this);
		Registry.register(
			Registry.ITEM,
			new Identifier("modid", this.id),
			this.blockItem
		);
	}

	// view (find . -name "iron_ore.json")
	@Override
	public void registerAssets(ClientResourcePackBuilder pack) {
		// 		{   "parent": "block/block",
		//     "textures": {
		//         "particle": "block/dirt",
		//         "bottom": "block/dirt",
		//         "top": "block/grass_block_top",
		//         "side": "block/grass_block_side",
		//         "overlay": "block/grass_block_side_overlay"
		//     },
		//     "elements": [
		//         {   "from": [ 0, 0, 0 ],
		//             "to": [ 16, 16, 16 ],
		//             "faces": {
		//                 "down":  { "uv": [ 0, 0, 16, 16 ], "texture": "#bottom", "cullface": "down" },
		//                 "up":    { "uv": [ 0, 0, 16, 16 ], "texture": "#top",    "cullface": "up", "tintindex": 0 },
		//                 "north": { "uv": [ 0, 0, 16, 16 ], "texture": "#side",   "cullface": "north" },
		//                 "south": { "uv": [ 0, 0, 16, 16 ], "texture": "#side",   "cullface": "south" },
		//                 "west":  { "uv": [ 0, 0, 16, 16 ], "texture": "#side",   "cullface": "west" },
		//                 "east":  { "uv": [ 0, 0, 16, 16 ], "texture": "#side",   "cullface": "east" }
		//             }
		//         },
		//         {   "from": [ 0, 0, 0 ],
		//             "to": [ 16, 16, 16 ],
		//             "faces": {
		//                 "north": { "uv": [ 0, 0, 16, 16 ], "texture": "#overlay", "tintindex": 0, "cullface": "north" },
		//                 "south": { "uv": [ 0, 0, 16, 16 ], "texture": "#overlay", "tintindex": 0, "cullface": "south" },
		//                 "west":  { "uv": [ 0, 0, 16, 16 ], "texture": "#overlay", "tintindex": 0, "cullface": "west" },
		//                 "east":  { "uv": [ 0, 0, 16, 16 ], "texture": "#overlay", "tintindex": 0, "cullface": "east" }
		//             }
		//         }
		//     ]
		// }
		pack.addBlockModel(
			new Identifier("modid", this.id),
			model -> {
				model.parent(new Identifier("minecraft", "block/block"))
					.texture("particle", new Identifier("minecraft", "block/stone"))
					.texture(
						"base",
						new Identifier("modid", "block/ore_base_" + resource.oreStyle)
					)
					.texture(
						"overlay",
						new Identifier("modid", "block/ore_overlay_" + resource.oreStyle)
					)
					.element(
						elem -> {
							elem.from(0, 0, 0).to(16, 16, 16);
							for (Direction dir : Direction.values()) {
								elem.face(
									dir,
									s -> s.uv(0, 0, 16, 16).texture("base").cullface(dir)
								);
							}
						}
					)
					.element(
						elem -> {
							elem.from(0, 0, 0).to(16, 16, 16);
							for (Direction dir : Direction.values()) {
								elem.face(
									dir,
									s -> s.uv(0, 0, 16, 16)
										.texture("overlay")
										.cullface(dir)
										.tintindex(0)
								);
							}
						}
					);
			}
		);
		pack.addBlockState(
			new Identifier("modid", this.id),
			f -> {
				f.variant(
					"",
					v -> {
						v.model(new Identifier("modid", "block/" + this.id));
					}
				);
			}
		);
		pack.addItemModel(
			new Identifier("modid", this.id),
			model -> model.parent(new Identifier("modid", "block/" + this.id))
		);
	}

	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public void registerData(ServerResourcePackBuilder data) {
		data.addLootTable(
			new Identifier("modid", "blocks/" + this.id),
			ltp -> {
				ltp.type(new Identifier("modid", "block/" + this.id))
					.pool(
						pool -> pool.rolls(1)
							.entry(
								e -> e.name(new Identifier("modid", this.id))
									.type(new Identifier("minecraft:item"))
							)
							.condition(
								new Identifier("minecraft:survives_explosion"),
								cond -> {}
							)
					);
			}
		);
	}

	@Override
	public void registerClient() {
		// it should be possible for one ore to be the same color as grass
		ColorProviderRegistry.BLOCK.register((block, world, pos, layer) -> layer == 0 ? resource.color : 0, this);
		ColorProviderRegistry.ITEM.register(
			(stack, layer) -> layer == 0 ? resource.color : 0,
			this.blockItem
		);
	}
}
