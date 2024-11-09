package com.jkong.enderport;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class StatsSaverAndLoader extends PersistentState {

    public static Type<StatsSaverAndLoader> type = new Type<>(
            StatsSaverAndLoader::new,
            StatsSaverAndLoader::createFromNbt,
            null
    );

    public HashMap<UUID, PlayerStats> playerStats = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound playersNbt = new NbtCompound();

        playerStats.forEach((uuid, playerStat) -> {
            NbtCompound playerNbt = new NbtCompound();
            playerNbt.putInt("endersouls", playerStat.endersouls);

            playersNbt.put(uuid.toString(), playerNbt);
        });

        nbt.put("players", playersNbt);
        return nbt;
    }

    public static StatsSaverAndLoader createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        StatsSaverAndLoader stats = new StatsSaverAndLoader();

        NbtCompound playersNbt = nbt.getCompound("players");
        playersNbt.getKeys().forEach(key -> {
            PlayerStats playerStats = new PlayerStats();
            playerStats.endersouls = playersNbt.getCompound(key).getInt("endersouls");
            UUID uuid = UUID.fromString(key);

            stats.playerStats.put(uuid, playerStats);
        });

        return stats;
    }

    public static StatsSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager manager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        return manager.getOrCreate(type, "enderport");
    }

    public static PlayerStats getPlayerStats(LivingEntity player) {
        StatsSaverAndLoader serverState = getServerState(player.getWorld().getServer());

        return serverState.playerStats.computeIfAbsent(player.getUuid(), uuid -> new PlayerStats());
    }
}
