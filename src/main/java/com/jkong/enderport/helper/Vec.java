package com.jkong.enderport.helper;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Vec {

    private Vec() {}

    //From Entity.class
    public static Vec3d getRotationVector(float pitch, float yaw) {
        float f = pitch * (float) (Math.PI / 180.0);
        float g = -yaw * (float) (Math.PI / 180.0);
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d(i * j, -k, h * j);
    }

    public static Vec3d getRotationVector(float pitch, float yaw, double length) {
        Vec3d temp = getRotationVector(pitch, yaw);
        return new Vec3d(length * temp.x, length * temp.y, length * temp.z);
    }

    public static Vec3d getVecFrom(Vec3d vec, Vec3d pointer) {
        return new Vec3d(vec.x + pointer.x, vec.y + pointer.y, vec.z + pointer.z);
    }
}
