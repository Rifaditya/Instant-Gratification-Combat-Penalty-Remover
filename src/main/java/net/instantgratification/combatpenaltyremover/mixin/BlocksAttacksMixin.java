package net.instantgratification.combatpenaltyremover.mixin;

import net.instantgratification.combatpenaltyremover.registry.CombatPenaltyRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * BlocksAttacks Mixin for Shield Protection
 * Verified against: Version 26.1 Snapshot 11+
 */
@Mixin(BlocksAttacks.class)
public abstract class BlocksAttacksMixin {

    /**
     * @author Antigravity (Zenith Studio)
     * @reason Intercept shield durability loss during blocking.
     */
    @Inject(method = "hurtBlockingItem", at = @At("HEAD"), cancellable = true)
    private void cpr$preventShieldDamage(net.minecraft.world.level.Level level, ItemStack stack, LivingEntity entity, net.minecraft.world.InteractionHand hand, float amount, CallbackInfo ci) {
        if (entity instanceof Player player && level instanceof ServerLevel serverLevel) {
            if (serverLevel.getGameRules().get(CombatPenaltyRules.PREVENT_SHIELD_BLOCK_DAMAGE)) {
                ci.cancel();
            }
        }
    }
}
