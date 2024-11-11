package com.jkong.enderport.particles;

import com.jkong.enderport.helper.Vec;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class SpBreakParticle {

    private static final HashSet<Vec3d> pointList = new HashSet<>();
    private static final ArrayList<Vec3d> listCopy;

    static {

        for (int i = 0; i < 360; i += 30) {
            for (int j = -90; j <= 90; j += 16) {
                for (double length = 0.5; length < 0.8; length += length/4) {
                    Vec3d single = Vec.getRotationVector((float) j, (float) i, length);
                    pointList.add(single);
                }
            }
        }

        listCopy = new ArrayList<>(pointList);

    }

    public static void onBreakParticle(World world, BlockPos pos) {
        Vec3d center = pos.toCenterPos().offset(Direction.DOWN, 0.3);

        for (Vec3d point : pointList) {
            Vec3d actualPoint = Vec.getVecFrom(center, point);
            world.addParticle(ParticleTypes.PORTAL, actualPoint.x, actualPoint.y, actualPoint.z, -2.2 * point.x, -2.2 * point.y, -2.2 * point.z);
        }
    }

    public static void breakingParticle(World world, BlockPos pos) {
        Random rand = new Random();
        int index;
        Vec3d center = pos.toCenterPos();

        for (int i = 0; i < 4; i++) {
            index = rand.nextInt(listCopy.size());
            Vec3d point = Vec.lengthenVec(listCopy.get(index), 2.0);
            Vec3d actualPoint = Vec.getVecFrom(center, point);
            world.addParticle(ParticleTypes.ENCHANT, actualPoint.x, actualPoint.y, actualPoint.z, -1.8 * point.x, -1.8 * point.y, -1.8 * point.z);
        }
    }
}
