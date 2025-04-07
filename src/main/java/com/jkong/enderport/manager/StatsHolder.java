package com.jkong.enderport.manager;

import org.spongepowered.asm.mixin.Unique;

public interface StatsHolder {
    @Unique
    int getEndersouls();

    @Unique
    void setEndersouls(int newEndersouls);

    @Unique
    int getStillTicks();

    @Unique
    void setStillTicks(int newTick);
}
