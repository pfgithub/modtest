package net.fabricmc.example;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SoundAction extends Action{
    SoundEvent effect;

    public SoundAction(SoundEvent effect){
        this.effect = effect;
    }

	@Override
	public void onUse(World world, PlayerEntity playerEntity, Hand hand) {
		playerEntity.playSound(effect, 1.0F, 1.0F);
	}

}
