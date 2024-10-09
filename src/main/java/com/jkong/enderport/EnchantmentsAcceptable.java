package com.jkong.enderport;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryKey;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentsAcceptable {
    public static final RegistryKey<Enchantment> a = Enchantments.SHARPNESS;
    public static final RegistryKey<Enchantment> b = Enchantments.SWEEPING_EDGE;
    public static final RegistryKey<Enchantment> c = Enchantments.SILK_TOUCH;
    public static final RegistryKey<Enchantment> d = Enchantments.SMITE;
    public static final RegistryKey<Enchantment> e = Enchantments.LOOTING;
    public static final RegistryKey<Enchantment> f = Enchantments.BANE_OF_ARTHROPODS;
    public static final RegistryKey<Enchantment> g = Enchantments.KNOCKBACK;

    private EnchantmentsAcceptable() {}

    public static List<RegistryKey<Enchantment>> getList() {
        ArrayList<RegistryKey<Enchantment>> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        list.add(f);
        list.add(g);
        return list;
    }
}
