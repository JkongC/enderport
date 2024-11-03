package com.jkong.enderport.mixin;

import com.jkong.enderport.blocks.LightedAirBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class LightRedirect {
    @Inject(method = "getLuminance()I", at = @At(value = "RETURN"), cancellable = true)
    public void getLuminance(CallbackInfoReturnable<Integer> cir){
        if ((Object)this instanceof BlockState state) {
            if (state.getBlock() instanceof LightedAirBlock) {
                int age = state.get(LightedAirBlock.AGE);
                cir.setReturnValue(15-Math.max(0,age-10));
            }
        }
    }

}
