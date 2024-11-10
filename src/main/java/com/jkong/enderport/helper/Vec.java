package com.jkong.enderport.helper;

import net.minecraft.util.math.Vec3d;

public class Vec {

    private Vec() {}

    //Directly from Entity.class
    public static Vec3d getRotationVector(float pitch, float yaw) {
        float f = pitch * (float) (Math.PI / 180.0);
        float g = -yaw * (float) (Math.PI / 180.0);
        float h = net.minecraft.util.math.MathHelper.cos(g);
        float i = net.minecraft.util.math.MathHelper.sin(g);
        float j = net.minecraft.util.math.MathHelper.cos(f);
        float k = net.minecraft.util.math.MathHelper.sin(f);
        return new Vec3d(i * j, -k, h * j);
    }

    public static Vec3d getRotationFrom(Vec3d vec, float pitch, float yaw, double length) {
        Vec3d n = getRotationVector(pitch, yaw).normalize();
        return new Vec3d(vec.x + n.x * length, vec.y + n.y * length, vec.y + n.y * length);
    }
}
