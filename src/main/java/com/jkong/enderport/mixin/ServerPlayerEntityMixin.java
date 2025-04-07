package com.jkong.enderport.mixin;

import com.jkong.enderport.manager.EPStats;
import com.jkong.enderport.manager.StatsHolder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends LivingEntity implements StatsHolder {
    private ServerPlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    @Override
    public int getEndersouls(){
        if ((Object) this instanceof ServerPlayerEntity player) {
            return EPStats.getPlayerEndersoul(player).endersouls;
        }
        return 0;
    }

    @Unique
    @Override
    public void setEndersouls(int endersouls){
        if ((Object) this instanceof ServerPlayerEntity player) {
            EPStats.updatePlayerEndersoul(player, endersouls);
        }
    }

    @Unique
    protected int stillTicks;

    @Unique
    @Override
    public int getStillTicks(){
        return this.stillTicks;
    }

    @Unique
    @Override
    public void setStillTicks(int newTick){
        this.stillTicks = newTick;
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void tick(CallbackInfo ci) {
        if (this.stillTicks > 0) {
            this.stillTicks--;
        }
    }

}

