package com.jkong.enderport.mixin.client;

import com.jkong.enderport.components.EPComponents;
import com.jkong.enderport.items.DimensionPickaxe;
import com.jkong.enderport.particles.SpBreakParticle;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientPlayerInteractionManager.class)
public class CPIManagerMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "updateBlockBreakingProgress", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;calcBlockBreakingDelta(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void updateBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir, BlockState blockState) {
        PlayerInventory inventory = client.player.getInventory();
        ItemStack stack = inventory.getStack(inventory.selectedSlot);
        if (blockState.getBlock().getHardness() == -1 && stack.getItem() instanceof DimensionPickaxe && stack.get(EPComponents.ENDERSOULS) >= 20) {
            SpBreakParticle.breakingParticle(client.world, pos);
        }
    }
}
