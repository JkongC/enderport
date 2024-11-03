package com.jkong.enderport.blocks;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.tick.TickPriority;

public class LightedAirBlock extends AirBlock {
    public static final int MAX_AGE = 25;
    public static final IntProperty AGE = Properties.AGE_25;

    public LightedAirBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(this.getAgeProperty(), Integer.valueOf(0)));
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (getAge(state) < MAX_AGE) {
            world.setBlockState(pos, withAge(getAge(state)+1), Block.NOTIFY_LISTENERS);
            world.scheduleBlockTick(pos, EPBlocks.LIGHTED_AIR_BLOCK, 0, TickPriority.HIGH);
        } else {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
        }
    }

    public BlockState withAge(int age) {
        return this.getDefaultState().with(this.getAgeProperty(), Integer.valueOf(age));
    }

    public int getAge(BlockState state) {
        return state.get(this.getAgeProperty());
    }

    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
