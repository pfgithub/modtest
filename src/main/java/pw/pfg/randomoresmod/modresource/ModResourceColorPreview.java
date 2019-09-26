package pw.pfg.randomoresmod.modresource;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import pw.pfg.randomoresmod.ColoredItem;
import pw.pfg.randomoresmod.RandomOresMod;
import pw.pfg.randomoresmod.ResourceObject;
import pw.pfg.randomoresmod.Style;

public class ModResourceColorPreview extends ColoredItem {
	ResourceDetails resource;
	String id;
	Item blockItem;

	public ModResourceColorPreview(ResourceDetails resource) {
		super(
			resource,
			new ResourceObject(
				resource.baseID + "_color_preview",
				new Style(
					new TextureLayer.SetBuilder()
						.add(new Identifier("randomoresmod:item/debug_color_preview"), true)
						.build(),
					"name.randomoresmod.debug.colorpreview"
				)
			),
			new Item.Settings()
				.group(RandomOresMod.RESOURCES)
				.rarity(resource.rarityMC)
		);
		this.resource = resource;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void appendTooltip(
		ItemStack itemStack,
		World world,
		List<Text> tooltip,
		TooltipContext tooltipContext
	) {
		if (tooltipContext.isAdvanced()) {
			String resourceToString = resource.toString();
			for (String v : resourceToString.split("\n")) {
				tooltip.add(new LiteralText(v).formatted(Formatting.DARK_GRAY));
			}
		}
	}
}
