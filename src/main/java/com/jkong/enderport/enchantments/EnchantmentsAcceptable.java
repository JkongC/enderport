package com.jkong.enderport.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnchantmentsAcceptable {
    private static List<RegistryKey<Enchantment>> ENCHANTMENTS_OF_STICK = new ArrayList<>();
    private static List<RegistryKey<Enchantment>> ENCHANTMENTS_OF_PICKAXE = new ArrayList<>();

    static {
        ENCHANTMENTS_OF_STICK.add(Enchantments.SHARPNESS);
        ENCHANTMENTS_OF_STICK.add(Enchantments.SWEEPING_EDGE);
        ENCHANTMENTS_OF_STICK.add(Enchantments.SILK_TOUCH);
        ENCHANTMENTS_OF_STICK.add(Enchantments.SMITE);
        ENCHANTMENTS_OF_STICK.add(Enchantments.LOOTING);
        ENCHANTMENTS_OF_STICK.add(Enchantments.BANE_OF_ARTHROPODS);
        ENCHANTMENTS_OF_STICK.add(Enchantments.KNOCKBACK);

        ENCHANTMENTS_OF_STICK = Collections.unmodifiableList(ENCHANTMENTS_OF_STICK);
    }

    static {
        ENCHANTMENTS_OF_PICKAXE.add(Enchantments.EFFICIENCY);
        ENCHANTMENTS_OF_PICKAXE.add(Enchantments.MENDING);
        ENCHANTMENTS_OF_PICKAXE.add(Enchantments.FORTUNE);
        ENCHANTMENTS_OF_PICKAXE.add(Enchantments.SILK_TOUCH);
        ENCHANTMENTS_OF_PICKAXE.add(Enchantments.POWER);
        ENCHANTMENTS_OF_PICKAXE.add(Enchantments.UNBREAKING);

        ENCHANTMENTS_OF_PICKAXE = Collections.unmodifiableList(ENCHANTMENTS_OF_PICKAXE);
    }

    private EnchantmentsAcceptable() {}

    public static List<RegistryKey<Enchantment>> getListOfStick() {
        return ENCHANTMENTS_OF_STICK;
    }

    public static List<RegistryKey<Enchantment>> getListOfPickaxe() {
        return ENCHANTMENTS_OF_PICKAXE;
    }
}
