package com.jkong.enderport.particles;

import com.jkong.enderport.blocks.LightedAirBlock;
import com.jkong.enderport.blocks.EPBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.tick.TickPriority;

public class TeleportParticle {
    private static final Random randomS = Random.create();

    public static void addParticleTo(PlayerEntity player) {
        if (player.getWorld().isClient()) {
            for(int i = 0; i < 100; ++i) {
                player.getWorld().addParticle(ParticleTypes.PORTAL, player.getParticleX(0.5), player.getRandomBodyY() - 0.25, player.getParticleZ(0.5), (randomS.nextDouble() - 0.5) * 2.0, -randomS.nextDouble(), (randomS.nextDouble() - 0.5) * 2.0);
            }
            for (int i = 0; i < 35; i++) {
                player.getWorld().addParticle(ParticleTypes.ENCHANT, player.getParticleX(0.5), player.getRandomBodyY() - 0.25, player.getParticleZ(0.5), (randomS.nextDouble() - 0.5) * 2.0, -randomS.nextDouble(), (randomS.nextDouble() - 0.5) * 2.0);
            }
        }
    }

    public static void addParticleBetween(World world, BlockPos origin, BlockPos destination) {
        Vec3d originPos = new Vec3d(origin.getX(), origin.getY(), origin.getZ());
        Vec3d trail = new Vec3d(destination.getX() - origin.getX(), destination.getY() - origin.getY(), destination.getZ() - origin.getZ());

        if (world.isClient()) {
            for (double i = 0; i <= 1; i = i + 0.01) {
                world.addParticle(ParticleTypes.PORTAL,originPos.getX()+i*trail.getX(),originPos.getY()+i*trail.getY(),originPos.getZ()+i*trail.getZ(),0,0,0);
                world.addParticle(ParticleTypes.ENCHANT,originPos.getX()+i*trail.getX(),originPos.getY()+i*trail.getY()+0.65,originPos.getZ()+i*trail.getZ(),0,0,0);
            }
        }

        if (!world.isClient()) {
            for (double i = 0; i <= 1; i = i + 0.1) {
                BlockPos ThisPos = BlockPos.ofFloored(originPos.getX()+i*trail.getX(),originPos.getY()+i*trail.getY(),originPos.getZ()+i*trail.getZ());
                if (world.getBlockState(ThisPos).isAir() && !(world.getBlockState(ThisPos).getBlock() instanceof LightedAirBlock)) {
                    world.setBlockState(ThisPos, EPBlocks.LIGHTED_AIR_BLOCK.getDefaultState());
                    world.scheduleBlockTick(ThisPos, EPBlocks.LIGHTED_AIR_BLOCK, 0, TickPriority.HIGH);
                }
            }
        }
    }

}
