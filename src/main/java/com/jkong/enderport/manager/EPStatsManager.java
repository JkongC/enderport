package com.jkong.enderport.manager;

import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentStateType;

public class EPStatsManager {
    private static final String ID = "enderport_stats";

    private static final PersistentStateType<EPStats> TYPE = new PersistentStateType<>(ID, EPStats::new, EPStats.CODEC, DataFixTypes.LEVEL);

    public static EPStats getState(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(TYPE);
    }
}
