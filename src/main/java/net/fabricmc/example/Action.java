package net.fabricmc.example;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class Action {

	public abstract void onUse(World world, PlayerEntity playerEntity, Hand hand);
}
