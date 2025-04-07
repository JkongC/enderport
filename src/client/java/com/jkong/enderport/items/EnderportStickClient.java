package com.jkong.enderport.items;

import com.jkong.enderport.particles.TeleportParticle;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EnderportStickClient {
    public static void spawnParticles(ClientWorld world, ClientPlayerEntity player, BlockPos begin, BlockPos end, Vec3d userPos) {
        TeleportParticle.addParticleTo(player, userPos);
        TeleportParticle.addParticleBetween(world, begin, end);
    }
}
