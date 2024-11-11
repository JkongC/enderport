package com.jkong.enderport.mixin;

import com.jkong.enderport.components.EPComponents;
import com.jkong.enderport.items.DimensionPickaxe;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
    @Inject(method = "calcBlockBreakingDelta", at = @At(value = "HEAD"), cancellable = true)
    protected void calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir){
        PlayerInventory playerInventory = player.getInventory();
        ItemStack stack = playerInventory.main.get(playerInventory.selectedSlot);
        if (state.getHardness(world, pos) == -1.0F && stack.getItem() instanceof DimensionPickaxe && stack.get(EPComponents.ENDERSOULS) >= 20) {
            cir.setReturnValue(player.getBlockBreakingSpeed(Blocks.OBSIDIAN.getDefaultState()) / 50.0F / 30.0F);
        }
    }
}
