package com.jkong.enderport.blockentities;

import com.jkong.enderport.blocks.EPClientBlocks;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.impl.client.rendering.BlockEntityRendererRegistryImpl;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EPClientBlockEntities {

    public static final BlockEntityType<LightedAirBlockEntity> LIGHTED_AIR_BLOCK_ENTITY = register(
            "lighted_air_block",
            FabricBlockEntityTypeBuilder.create(LightedAirBlockEntity::new, EPClientBlocks.LIGHTED_AIR_BLOCK).build()
    );

    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of("enderport", path), blockEntityType);
    }

    public static void Initialize() {
        BlockEntityRendererFactories.register(
                EPClientBlockEntities.LIGHTED_AIR_BLOCK_ENTITY,
                LightedAirBlockEntityRenderer::new
        );
    }
}
