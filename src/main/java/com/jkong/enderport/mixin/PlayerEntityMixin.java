package com.jkong.enderport.mixin;

import com.jkong.enderport.manager.PlayerStats;
import com.jkong.enderport.manager.StatsSaverAndLoader;
import com.jkong.enderport.manager.StatsHolder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements StatsHolder {
    private PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    protected PlayerStats stats;

    @Unique
    @Override
    public int getEndersouls(){
        if (this.stats == null){
            this.stats = StatsSaverAndLoader.getPlayerStats(this);
        }
        return this.stats.endersouls;
    }

    @Unique
    @Override
    public void setEndersouls(int newEndersouls){
        this.stats.endersouls = newEndersouls;
        StatsSaverAndLoader serverState = StatsSaverAndLoader.getServerState(this.getWorld().getServer());
        serverState.playerStats.put(this.getUuid(), this.stats);
        serverState.markDirty();
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

