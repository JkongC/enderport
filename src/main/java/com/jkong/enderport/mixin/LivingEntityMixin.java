package com.jkong.enderport.mixin;

import com.jkong.enderport.manager.StatsHolder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "takeKnockback", at = @At(value = "HEAD"), cancellable = true)
    private void takeKnockback(CallbackInfo ci) {
        if ((Object)this instanceof ServerPlayerEntity player) {
            if (((StatsHolder) player).getStillTicks() > 0) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "computeFallDamage", at = @At(value = "HEAD"), cancellable = true)
    private void computeFallDamage(double fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> cir) {
        if ((Object)this instanceof ServerPlayerEntity player) {
            if (((StatsHolder) player).getStillTicks() > 0) {
                cir.setReturnValue(0);
            }
        }
    }
}
