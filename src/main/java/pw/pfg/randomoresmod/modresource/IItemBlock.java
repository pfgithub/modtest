package pw.pfg.randomoresmod.modresource;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface IItemBlock {
	public Block getBlock();
	public boolean hasGlint(ItemStack itemStack_1);
}
