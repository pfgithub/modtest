package pw.pfg.randomoresmod.modresource;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class NamedBlockItem extends BlockItem {

	public NamedBlockItem(Block block_1, Settings item$Settings_1) {
		super(block_1, item$Settings_1);
	}

	@Override
	public Text getName() {
		return this.getBlock().getName();
	}

	@Override
	public Text getName(ItemStack stack) {
		return this.getBlock().getName();
	}
}
