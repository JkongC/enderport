package com.jkong.enderport;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EnderportStick extends SwordItem {

    public EnderportStick() {
        super(ToolMaterials.DIAMOND,new Settings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand){

        int blockCount = 9;
        Vec3d posD = user.getRotationVector();
        int trytime = 0;

        for(int i = 0; i < 10; i++) {
            BlockState stateFoot = world.getBlockState(user.getBlockPos().offset(Direction.Axis.X, (int)Math.round(i*posD.getX())).offset(Direction.Axis.Y, (int)Math.round(i*posD.getY())).offset(Direction.Axis.Z, (int)Math.round(i*posD.getZ())));
            BlockState stateHead = world.getBlockState(user.getBlockPos().offset(Direction.Axis.X, (int)Math.round(i*posD.getX())).offset(Direction.Axis.Y, (int)Math.round(i*posD.getY())).offset(Direction.Axis.Z, (int)Math.round(i*posD.getZ())).offset(Direction.UP, 1));

            if ((stateHead.isOpaque() || stateHead.getBlock() instanceof TransparentBlock || stateHead.getBlock() instanceof PaneBlock || stateHead.getBlock() instanceof LeavesBlock) || (stateFoot.isOpaque() || stateFoot.getBlock() instanceof TransparentBlock || stateFoot.getBlock() instanceof PaneBlock || stateFoot.getBlock() instanceof LeavesBlock)) {
                if (i <= 4) {
                    return TypedActionResult.pass(user.getStackInHand(hand));
                } else {
                    blockCount = i;
                }
            }
        }

        BlockPos tryPos = user.getBlockPos().offset(Direction.Axis.X, (int)Math.round(blockCount*posD.getX())).offset(Direction.Axis.Y, (int)Math.round(blockCount*posD.getY())).offset(Direction.Axis.Z, (int)Math.round(blockCount*posD.getZ()));

        while (true){
            if (trytime > 8) {
                return TypedActionResult.pass(user.getStackInHand(hand));
            }

            if (!(world.getBlockState(tryPos).getBlock() instanceof AirBlock || world.getBlockState(tryPos).getBlock() instanceof FluidBlock)) {
                tryPos = tryPos.offset(Direction.UP,1);
                trytime++;
            } else {break;}
        }

        user.setPosition(Vec3d.of(tryPos));
        user.setVelocity(0,0,0);
        user.fallDistance = 0;
        return TypedActionResult.success(user.getStackInHand(hand));

    }
}
