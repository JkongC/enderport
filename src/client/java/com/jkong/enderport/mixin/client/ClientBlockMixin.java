package com.jkong.enderport.mixin.client;

import com.jkong.enderport.items.DimensionPickaxe;
import com.jkong.enderport.particles.SpBreakParticle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class ClientBlockMixin {
    @Inject(method = "onBreak", at = @At(value = "HEAD"))
    private void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {
        if (world.isClient()) {
            PlayerInventory inventory = player.getInventory();
            if (state.getBlock().getHardness() == -1 && inventory.getStack(inventory.getSelectedSlot()).getItem() instanceof DimensionPickaxe) {
                SpBreakParticle.onBreakParticle(world, pos);
            }
        }
    }
}
