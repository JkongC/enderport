package com.jkong.enderport.mixin;

import com.jkong.enderport.items.DimensionPickaxe;
import com.jkong.enderport.particles.SpBreakParticle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Block.class)
public abstract class BlockMixin {
    @SuppressWarnings("InjectedReferences")
    @Inject(method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;", at = @At(value = "HEAD"), cancellable = true)
    private static void getDroppedStacks(
            BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack stack, CallbackInfoReturnable<List<ItemStack>> cir
    ) {
        if (state.getBlock().getHardness() == -1 && stack.getItem() instanceof DimensionPickaxe) {
            cir.setReturnValue(List.of(state.getBlock().asItem().getDefaultStack()));
        }
    }

    @Inject(method = "onBreak", at = @At(value = "HEAD"))
    private void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {
        if (world.isClient()) {
            PlayerInventory inventory = player.getInventory();
            if (state.getBlock().getHardness() == -1 && inventory.getStack(inventory.selectedSlot).getItem() instanceof DimensionPickaxe) {
                SpBreakParticle.onBreakParticle(world, pos);
            }
        }
    }
}
