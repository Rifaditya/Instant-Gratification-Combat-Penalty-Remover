package net.instantgratification.combatpenaltyremover.mixin;

import net.instantgratification.combatpenaltyremover.registry.CombatPenaltyRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.ChangeItemDamage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * ChangeItemDamage Mixin for Enchantment Protection (Thorns, Soul Speed, etc.)
 * Verified against: Version 26.1 Snapshot 11+
 */
@Mixin(ChangeItemDamage.class)
public abstract class ChangeItemDamageMixin {

    /**
     * @author Antigravity (Zenith Studio)
     * @reason Intercept enchantment-driven durability loss.
     *         Refined logic: Uses ItemTags for hand item weapon/tool detection.
     */
    @Inject(method = "apply", at = @At("HEAD"), cancellable = true)
    private void cpr$preventEnchantmentDurabilityLoss(ServerLevel level, int enchantmentLevel, EnchantedItemInUse itemInUse, net.minecraft.world.entity.Entity entity, net.minecraft.world.phys.Vec3 position, CallbackInfo ci) {
        if (!(entity instanceof Player player)) {
             return;
        }

        boolean preventWeaponDamage = level.getGameRules().get(CombatPenaltyRules.PREVENT_WEAPON_COMBAT_DAMAGE);
        boolean preventArmorDamage = level.getGameRules().get(CombatPenaltyRules.PREVENT_ARMOR_COMBAT_DAMAGE);
        boolean treatAxesAsWeapons = level.getGameRules().get(CombatPenaltyRules.TREAT_AXES_AS_WEAPONS);

        var slot = itemInUse.inSlot();
        if (slot == null) return;

        if (slot.isArmor() && preventArmorDamage) {
            ci.cancel();
            return;
        } 
        
        if (slot == net.minecraft.world.entity.EquipmentSlot.MAINHAND || slot == net.minecraft.world.entity.EquipmentSlot.OFFHAND) {
            if (preventWeaponDamage) {
                var stack = itemInUse.itemStack();
                boolean isPrimaryWeapon = stack.is(ItemTags.SWORDS) || stack.is(Items.TRIDENT) || stack.is(Items.MACE);
                boolean isAxe = stack.is(ItemTags.AXES);

                if (isPrimaryWeapon || (isAxe && treatAxesAsWeapons)) {
                    ci.cancel();
                }
            }
        }
    }
}
