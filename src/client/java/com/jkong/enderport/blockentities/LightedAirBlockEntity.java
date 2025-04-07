package com.jkong.enderport.blockentities;

import com.jkong.enderport.blocks.LightedAirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LightedAirBlockEntity extends BlockEntity {
    private int age = 0;
    private static final int MAX_AGE = 25;

    public LightedAirBlockEntity(BlockPos pos, BlockState state) {
        super(EPClientBlockEntities.LIGHTED_AIR_BLOCK_ENTITY, pos, state);
    }

    public static void tickClient(World world, BlockPos pos, BlockState state, LightedAirBlockEntity blockEntity) {
        blockEntity.age++;
        if (blockEntity.age >= MAX_AGE) {
            world.removeBlock(pos, false);
            return;
        }

        int newBrightness = blockEntity.getBrightness();
        if (state.get(LightedAirBlock.BRIGHTNESS) != newBrightness) {
            world.setBlockState(pos, state.with(LightedAirBlock.BRIGHTNESS, newBrightness));
        }
    }

    public int getBrightness() {
        if (age < 10) {
            return 15;
        } else {
            return 25 - age;
        }
    }
}
