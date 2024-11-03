package com.jkong.enderport.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnchantmentsAcceptable {
    private static List<RegistryKey<Enchantment>> EnchantmentList = new ArrayList<>();

    static {
        EnchantmentList.add(Enchantments.SHARPNESS);
        EnchantmentList.add(Enchantments.SWEEPING_EDGE);
        EnchantmentList.add(Enchantments.SILK_TOUCH);
        EnchantmentList.add(Enchantments.SMITE);
        EnchantmentList.add(Enchantments.LOOTING);
        EnchantmentList.add(Enchantments.BANE_OF_ARTHROPODS);
        EnchantmentList.add(Enchantments.KNOCKBACK);

        EnchantmentList = Collections.unmodifiableList(EnchantmentList);
    }

    private EnchantmentsAcceptable() {}

    public static List<RegistryKey<Enchantment>> getList() {
        return EnchantmentList;
    }
}
