package pw.pfg.randomoresmod.modbiome;

import java.util.List;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldClimate;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.BushFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.MineshaftFeature;
import net.minecraft.world.gen.feature.MineshaftFeatureConfig;
import net.minecraft.world.gen.feature.PlantedFeatureConfig;
import net.minecraft.world.gen.feature.RandomFeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import pw.pfg.randomoresmod.IRegisterable;

public final class ModBiome extends Biome implements IRegisterable {
	BiomeDetails biome;

	public ModBiome(BiomeDetails biome) {
		super(
			(new Biome.Settings())
				.configureSurfaceBuilder(
					SurfaceBuilder.DEFAULT,
					SurfaceBuilder.GRASS_CONFIG
				)
				.precipitation(Biome.Precipitation.RAIN)
				.category(Biome.Category.FOREST)
				.depth(0.1F)
				.scale(0.2F)
				.temperature(0.7F)
				.downfall(0.8F)
				.waterColor(4159204)
				.waterFogColor(329011)
				.parent((String) null)
		);
		this.biome = biome;
		this.addStructureFeature(Feature.WOODLAND_MANSION, FeatureConfig.DEFAULT);
		this.addStructureFeature(
				Feature.MINESHAFT,
				new MineshaftFeatureConfig(0.004D, MineshaftFeature.Type.NORMAL)
			);
		this.addStructureFeature(Feature.STRONGHOLD, FeatureConfig.DEFAULT);
		DefaultBiomeFeatures.addLandCarvers(this);
		DefaultBiomeFeatures.addDefaultStructures(this);
		DefaultBiomeFeatures.addDefaultLakes(this);
		DefaultBiomeFeatures.addDungeons(this);
		this.addFeature(
				GenerationStep.Feature.VEGETAL_DECORATION,
				configureFeature(
					Feature.RANDOM_SELECTOR,
					new RandomFeatureConfig(
						new Feature[] {
							Feature.HUGE_BROWN_MUSHROOM,
							Feature.HUGE_RED_MUSHROOM,
							Feature.DARK_OAK_TREE,
							Feature.FANCY_TREE
						},
						new FeatureConfig[] {
							new PlantedFeatureConfig(false),
							new PlantedFeatureConfig(false),
							FeatureConfig.DEFAULT,
							FeatureConfig.DEFAULT
						},
						new float[] { 0.025F, 0.05F, 0.6666667F, 0.1F },
						Feature.NORMAL_TREE,
						FeatureConfig.DEFAULT
					),
					Decorator.DARK_OAK_TREE,
					DecoratorConfig.DEFAULT
				)
			);
		DefaultBiomeFeatures.addForestFlowers(this);

		// DefaultBiomeFeatures.addMineables(this);
		DefaultBiomeFeatures.addDefaultOres(this);

		// DefaultBiomeFeatures.addDefaultDisks(this);
		// DefaultBiomeFeatures.addDefaultFlowers(this);
		DefaultBiomeFeatures.addForestGrass(this);

		// DefaultBiomeFeatures.addDefaultMushrooms(this);
		// DefaultBiomeFeatures.addDefaultVegetation(this);
		// DefaultBiomeFeatures.addSprings(this);
		DefaultBiomeFeatures.addFrozenTopLayer(this);
		DefaultBiomeFeatures.method_20826(this); // end cities
		this.addSpawn(
				EntityCategory.CREATURE,
				new Biome.SpawnEntry(EntityType.SHEEP, 12, 4, 4)
			);
		this.addSpawn(
				EntityCategory.CREATURE,
				new Biome.SpawnEntry(EntityType.PIG, 10, 4, 4)
			);
		this.addSpawn(
				EntityCategory.CREATURE,
				new Biome.SpawnEntry(EntityType.CHICKEN, 10, 4, 4)
			);
		this.addSpawn(
				EntityCategory.CREATURE,
				new Biome.SpawnEntry(EntityType.COW, 8, 4, 4)
			);
		this.addSpawn(
				EntityCategory.AMBIENT,
				new Biome.SpawnEntry(EntityType.BAT, 10, 8, 8)
			);
		this.addSpawn(
				EntityCategory.MONSTER,
				new Biome.SpawnEntry(EntityType.SPIDER, 100, 4, 4)
			);
		this.addSpawn(
				EntityCategory.MONSTER,
				new Biome.SpawnEntry(EntityType.ZOMBIE, 95, 4, 4)
			);
		this.addSpawn(
				EntityCategory.MONSTER,
				new Biome.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1)
			);
		this.addSpawn(
				EntityCategory.MONSTER,
				new Biome.SpawnEntry(EntityType.SKELETON, 100, 4, 4)
			);
		this.addSpawn(
				EntityCategory.MONSTER,
				new Biome.SpawnEntry(EntityType.CREEPER, 100, 4, 4)
			);
		this.addSpawn(
				EntityCategory.MONSTER,
				new Biome.SpawnEntry(EntityType.SLIME, 100, 4, 4)
			);
		this.addSpawn(
				EntityCategory.MONSTER,
				new Biome.SpawnEntry(EntityType.ENDERMAN, 10, 1, 4)
			);
		this.addSpawn(
				EntityCategory.MONSTER,
				new Biome.SpawnEntry(EntityType.WITCH, 5, 1, 1)
			);

		this.addFeature(
				GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(
					Feature.BUSH,
					new BushFeatureConfig(Blocks.OAK_LEAVES.getDefaultState()),
					Decorator.CHANCE_HEIGHTMAP_DOUBLE,
					new ChanceDecoratorConfig(4)
				)
			);
		this.addFeature(
				GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(
					Feature.BUSH,
					new BushFeatureConfig(Blocks.OAK_LEAVES.getDefaultState()),
					Decorator.CHANCE_HEIGHTMAP_DOUBLE,
					new ChanceDecoratorConfig(8)
				)
			);
		this.addFeature(
				GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(
					Feature.BUSH,
					new BushFeatureConfig(Blocks.OAK_LEAVES.getDefaultState()),
					Decorator.CHANCE_HEIGHTMAP_DOUBLE,
					new ChanceDecoratorConfig(8)
				)
			);
		this.addFeature(
				GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(
					Feature.BUSH,
					new BushFeatureConfig(Blocks.OAK_LEAVES.getDefaultState()),
					Decorator.CHANCE_HEIGHTMAP_DOUBLE,
					new ChanceDecoratorConfig(8)
				)
			);
		this.addFeature(
				GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(
					Feature.BUSH,
					new BushFeatureConfig(Blocks.OAK_LEAVES.getDefaultState()),
					Decorator.CHANCE_HEIGHTMAP_DOUBLE,
					new ChanceDecoratorConfig(8)
				)
			);
	}

	@Environment(EnvType.CLIENT)
	public int getGrassColorAt(BlockPos blockPos_1) {
		int int_1 = super.getGrassColorAt(blockPos_1);
		return (int_1 & 16711422) + 2634762 >> 1;
	}

	@Override
	public void registerTranslations(TranslationBuilder trans) {}

	@Override
	public void registerData(ServerResourcePackBuilder data) {}

	@Override
	public void registerAssets(ClientResourcePackBuilder pack) {}

	@Override
	public void register() {
		Registry.register(
			Registry.BIOME,
			new Identifier("randomoresmod", this.biome.id),
			this
		);
		OverworldBiomes.addContinentalBiome(this, OverworldClimate.TEMPERATE, 2D);
		OverworldBiomes.addContinentalBiome(this, OverworldClimate.COOL, 2D);
	}

	@Override
	public void registerClient() {}

	@Override
	public void registerBiomeFeatures(Biome biome) {}

	@Override
	public void registerItemGroup(List<ItemStack> stacks) {}
}
