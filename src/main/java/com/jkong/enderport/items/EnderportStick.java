package com.jkong.enderport.items;

import com.jkong.enderport.components.EPComponents;
import com.jkong.enderport.enchantments.EnchantmentsAcceptable;
import com.jkong.enderport.manager.StatsHolder;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

import static net.minecraft.sound.SoundEvents.ENTITY_ENDERMAN_TELEPORT;

public class EnderportStick extends SwordItem {

    public static final List<RegistryKey<Enchantment>> EnchantmentList = EnchantmentsAcceptable.getList();

    public EnderportStick() {
        super(ToolMaterials.DIAMOND,(new Settings()).fireproof().rarity(Rarity.EPIC).component(EPComponents.ENDERSOULS, 0).attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.DIAMOND,10,-2.4F)));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand){

        int blockCount = 9;
        Vec3d posD = user.getRotationVector().normalize();
        BlockPos OriginalPos = user.getBlockPos();
        int trytime = 0;

        for(int i = 0; i < 10; i++) {
            BlockPos posHead = OriginalPos.offset(Direction.Axis.X, (int)Math.round(i*posD.getX())).offset(Direction.Axis.Y, (int)Math.round(i*posD.getY())).offset(Direction.Axis.Z, (int)Math.round(i*posD.getZ())).offset(Direction.UP, 1);
            BlockState stateHead = world.getBlockState(posHead);

            if (stateHead.getBlock().collidable) {
                if (i <= 4) {
                    return TypedActionResult.pass(user.getStackInHand(hand));
                } else {
                    blockCount = i;
                }
            }
        }

        BlockPos tryPos = OriginalPos.offset(Direction.Axis.X, (int)Math.round(blockCount*posD.getX())).offset(Direction.Axis.Y, (int)Math.round(blockCount*posD.getY())).offset(Direction.Axis.Z, (int)Math.round(blockCount*posD.getZ()));

        while (true){
            if (trytime > 8) {
                return TypedActionResult.pass(user.getStackInHand(hand));
            }


            if (world.getBlockState(tryPos).getBlock().collidable) {
                tryPos = tryPos.offset(Direction.UP,1);
                trytime++;
            } else {break;}
        }


        user.setPosition(Vec3d.of(tryPos));
        TeleportParticle.addParticleBetween(world, user, OriginalPos, tryPos);
        TeleportParticle.addParticleTo(user);
        user.setVelocity(0,0,0);
        user.fallDistance = 0;
        user.playSoundToPlayer(ENTITY_ENDERMAN_TELEPORT, SoundCategory.VOICE,0.6f, 0.9f);
        user.getItemCooldownManager().set(this, 6);
        ((StatsHolder) user).setStillTicks(40);
        user.getStackInHand(hand).damage(1, user, hand.name().equals("main_hand") ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        return TypedActionResult.success(user.getStackInHand(hand));

    }

    @Override
    public boolean canBeEnchantedWith(ItemStack stack, RegistryEntry<Enchantment> enchantment, EnchantingContext context) {
        for (RegistryKey<Enchantment> enchantments : EnchantmentList) {
            if (enchantment.matchesKey(enchantments)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        LivingEntity attacker = (LivingEntity) damageSource.getAttacker();
        ItemStack attackerStack;
        int soul;

        if (attacker != null && attacker.isSneaking() && (attacker.getStackInHand(Hand.MAIN_HAND).getItem() == this || attacker.getStackInHand(Hand.OFF_HAND).getItem() == this)) {
            if (attacker.getStackInHand(Hand.MAIN_HAND).getItem() == this) {
                attackerStack = attacker.getStackInHand(Hand.MAIN_HAND);
            } else {
                attackerStack = attacker.getStackInHand(Hand.OFF_HAND);
            }

            soul = attackerStack.getOrDefault(EPComponents.ENDERSOULS, 0);
            if (soul >= 60) {
                if (soul >= 100) {
                    if (soul == EPComponents.getMaxEndersouls()) {
                        damageThis(attackerStack, attacker, 80);
                        return 300.0F;
                    }
                    damageThis(attackerStack, attacker, 50);
                    return 120.0F + (soul - 100);
                }
                damageThis(attackerStack, attacker, 30);
                return 70.0F;
            }
            damageThis(attackerStack, attacker, 20);
            return 10.0F;
        }

        return 0.0F;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        if (!target.isDead() || !(attacker instanceof PlayerEntity player)) return;

        if (target instanceof EndermanEntity){
            damageThis(stack, player, -40);
        } else if (target instanceof WardenEntity || target instanceof EnderDragonEntity || target instanceof WitherEntity){
            damageThis(stack, player, -1500);
            stack.set(EPComponents.SOULS_TO_BE_ADDED, 1);
        }
    }

    public void addPlayerEndersouls(PlayerEntity player, int num) {
        ((StatsHolder) player).setEndersouls(getPlayerEndersouls(player) + num);
    }

    public void setPlayerEndersouls(PlayerEntity player, int num) {
        ((StatsHolder) player).setEndersouls(num);
    }

    public int getPlayerEndersouls(PlayerEntity player) {
        return ((StatsHolder) player).getEndersouls();
    }

    private void damageThis(ItemStack stack, LivingEntity attacker, int damage) {
        stack.damage(damage, attacker, attacker.getStackInHand(Hand.MAIN_HAND).getItem() instanceof EnderportStick ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player && world instanceof ServerWorld) {
            if (stack.getOrDefault(EPComponents.SOULS_TO_BE_ADDED, 0) != 0) {
                for (PlayerEntity playero : world.getPlayers()) {
                    addPlayerEndersouls(playero, 1);
                }
                stack.set(EPComponents.SOULS_TO_BE_ADDED, 0);
            }

            if (getPlayerEndersouls(player) > EPComponents.getMaxEndersouls()) {
                setPlayerEndersouls(player, EPComponents.getMaxEndersouls());
            }

            stack.set(EPComponents.ENDERSOULS, getPlayerEndersouls(player));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.contains(EPComponents.ENDERSOULS)) {
            int soulcount = stack.getOrDefault(EPComponents.ENDERSOULS, 0);
            tooltip.add(Text.translatable("item.enderport.enderport_stick.soulcounter", soulcount).formatted(Formatting.DARK_PURPLE));
        }
    }
}
