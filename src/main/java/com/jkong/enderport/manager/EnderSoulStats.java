package com.jkong.enderport.manager;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class EnderSoulStats {
    public static final Codec<EnderSoulStats> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("endersouls").forGetter(s -> s.endersouls)
            ).apply(instance, EnderSoulStats::new));

    public EnderSoulStats(int endersouls) {this.endersouls = endersouls;}

    public int endersouls;
}
