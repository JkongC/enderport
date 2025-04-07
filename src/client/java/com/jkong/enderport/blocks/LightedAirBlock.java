package com.jkong.enderport.blocks;

import com.jkong.enderport.blockentities.EPClientBlockEntities;
import com.jkong.enderport.blockentities.LightedAirBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LightedAirBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final IntProperty BRIGHTNESS = IntProperty.of("brightness", 0, 15);

    public LightedAirBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(BRIGHTNESS, 15));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(LightedAirBlock::new);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!world.isClient()) {
            world.removeBlock(pos, false);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BRIGHTNESS);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LightedAirBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, EPClientBlockEntities.LIGHTED_AIR_BLOCK_ENTITY, world.isClient() ? LightedAirBlockEntity::tickClient : null);
    }
}
