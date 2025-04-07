package com.jkong.enderport.blockentities;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

public class LightedAirBlockEntityRenderer implements BlockEntityRenderer<LightedAirBlockEntity> {

    public LightedAirBlockEntityRenderer(BlockEntityRendererFactory.Context context) {}

    @Override
    public void render(LightedAirBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {

    }
}
