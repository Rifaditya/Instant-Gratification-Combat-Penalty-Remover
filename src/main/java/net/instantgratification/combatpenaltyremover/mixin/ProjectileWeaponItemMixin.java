package net.instantgratification.combatpenaltyremover.mixin;

import net.instantgratification.combatpenaltyremover.registry.CombatPenaltyRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * ProjectileWeaponItem Mixin for Ranged Combat Protection
 * Verified against: Version 26.1 Snapshot 11+
 */
@Mixin(ProjectileWeaponItem.class)
public abstract class ProjectileWeaponItemMixin {

    /**
     * @author Antigravity (Zenith Studio)
     * @reason Redirect hurtAndBreak to conditionally avoid durability loss.
     */
    @Redirect(
        method = "shoot",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V")
    )
    private void cpr$conditionalHurtAndBreak(ItemStack instance, int amount, LivingEntity owner, EquipmentSlot slot) {
        if (owner.level() instanceof ServerLevel serverLevel && 
            serverLevel.getGameRules().get(CombatPenaltyRules.PREVENT_WEAPON_COMBAT_DAMAGE)) {
            // Skip damage
            return;
        }
        // Fallback to original behavior
        instance.hurtAndBreak(amount, owner, slot);
    }
}
