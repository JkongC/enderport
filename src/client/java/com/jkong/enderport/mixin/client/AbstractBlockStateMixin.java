package com.jkong.enderport.mixin.client;

import com.jkong.enderport.blocks.LightedAirBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
    @Shadow public abstract Block getBlock();

    @Inject(method = "getLuminance", at = @At(value = "HEAD"), cancellable = true)
    private void getLuminance(CallbackInfoReturnable<Integer> cir) {
        if (this.getBlock() instanceof LightedAirBlock) {
            cir.setReturnValue(((AbstractBlock.AbstractBlockState)(Object)this).get(LightedAirBlock.BRIGHTNESS, 0));
        }
    }
}
