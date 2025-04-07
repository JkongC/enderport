package com.jkong.enderport.manager;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EPStats extends PersistentState {

    public HashMap<UUID, EnderSoulStats> endersoulStats = new HashMap<>();

    public static final Codec<EPStats> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.unboundedMap(Codec.STRING, EnderSoulStats.CODEC)
                            .fieldOf("enderport_playerStats")
                            .forGetter(state -> {
                                Map<String, EnderSoulStats> map = new HashMap<>();
                                state.endersoulStats.forEach((uuid, stats) ->
                                        map.put(uuid.toString(), stats));
                                return map;
                            })
            ).apply(instance, (playersMap) -> {
                EPStats state = new EPStats();
                playersMap.forEach((key, stats) ->
                        state.endersoulStats.put(UUID.fromString(key), stats));
                return state;
            }));

    public EPStats() {}

    public static EnderSoulStats getPlayerEndersoul(ServerPlayerEntity player) {
        EPStats state = EPStatsManager.getState(player.getServerWorld());
        return state.endersoulStats.computeIfAbsent(
                player.getUuid(),
                uuid -> new EnderSoulStats(0)
        );
    }

    public static void updatePlayerEndersoul(ServerPlayerEntity player, int endersouls) {
        EPStats state = EPStatsManager.getState(player.getServerWorld());
        state.endersoulStats.put(player.getUuid(), new EnderSoulStats(endersouls));
        state.markDirty();
    }
}
