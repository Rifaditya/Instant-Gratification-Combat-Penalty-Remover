package net.instantgratification.combatpenaltyremover.registry;

import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.resources.Identifier;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;

/**
 * GameRule Registration for Combat Penalty Remover
 * Verified against: Version 26.1 Snapshot 11+
 * Pattern: DasikLibrary standard
 */
public class CombatPenaltyRules {

    public static GameRuleCategory CATEGORY;
    public static GameRule<Boolean> PREVENT_WEAPON_COMBAT_DAMAGE;
    public static GameRule<Boolean> PREVENT_ARMOR_COMBAT_DAMAGE;
    public static GameRule<Boolean> PREVENT_SHIELD_BLOCK_DAMAGE;
    public static GameRule<Boolean> PREVENT_ARMOR_ENVIRONMENTAL_DAMAGE;
    public static GameRule<Boolean> TREAT_AXES_AS_WEAPONS;
    public static GameRule<Boolean> PREVENT_SPAM_ATTACK_DURABILITY_LOSS;
    public static GameRule<Integer> ATTACK_SPEED_EFFICIENCY;
    public static GameRule<Boolean> PREVENT_WEAPON_PVP_DAMAGE;
    public static GameRule<Boolean> PREVENT_ARMOR_PVP_DAMAGE;

    /**
     * Captures the attack strength scale during an attack to be used for durability calculation.
     */
    public static final ThreadLocal<Float> CAPTURED_ATTACK_STRENGTH = ThreadLocal.withInitial(() -> 1.0F);

    public static void register() {
        CATEGORY = DynamicGameRuleManager.registerCategory(
                Identifier.fromNamespaceAndPath("combat-penalty-remover", "combat_penalties")
        );

        PREVENT_WEAPON_COMBAT_DAMAGE = DynamicGameRuleManager.booleanRule("ig:prevent_weapon_combat_damage", CATEGORY, true)
                .name("Prevent Weapon Combat Damage")
                .description("If active, melee and ranged weapons won't lose durability during combat attacks.")
                .register();

        PREVENT_ARMOR_COMBAT_DAMAGE = DynamicGameRuleManager.booleanRule("ig:prevent_armor_combat_damage", CATEGORY, true)
                .name("Prevent Armor Combat Damage")
                .description("If active, armor won't lose durability when damaged by entities (mobs/players).")
                .register();

        PREVENT_SHIELD_BLOCK_DAMAGE = DynamicGameRuleManager.booleanRule("ig:prevent_shield_block_damage", CATEGORY, true)
                .name("Prevent Shield Block Damage")
                .description("If active, shields won't lose durability when blocking attacks.")
                .register();

        PREVENT_ARMOR_ENVIRONMENTAL_DAMAGE = DynamicGameRuleManager.booleanRule("ig:prevent_armor_environmental_damage", CATEGORY, false)
                .name("Prevent Armor Environmental Damage")
                .description("If active, armor won't lose durability from environmental sources like fire, lava, or falls.")
                .register();

        TREAT_AXES_AS_WEAPONS = DynamicGameRuleManager.booleanRule("ig:treat_axes_as_weapons", CATEGORY, true)
                .name("Treat Axes as Weapons")
                .description("If active, axes will only lose 1 durability when used as a weapon, instead of the vanilla 2.")
                .register();

        PREVENT_SPAM_ATTACK_DURABILITY_LOSS = DynamicGameRuleManager.booleanRule("ig:prevent_spam_attack_durability_loss", CATEGORY, true)
                .name("Prevent Spam Attack Durability Loss")
                .description("If active, attacks with low charge (below 90%) won't consume durability. Fixes tools breaking fast when spamming.")
                .register();

        ATTACK_SPEED_EFFICIENCY = DynamicGameRuleManager.integerRule("ig:attack_speed_efficiency", CATEGORY, 100)
                .name("Attack Speed Efficiency")
                .description("Percentage multiplier for attack recharge speed. 100 is vanilla, 200 is twice as fast, 50 is half speed.")
                .register();

        PREVENT_WEAPON_PVP_DAMAGE = DynamicGameRuleManager.booleanRule("ig:prevent_weapon_pvp_damage", CATEGORY, false)
                .name("Prevent Weapon PvP Damage")
                .description("If active, weapons won't lose durability when attacking other players.")
                .register();

        PREVENT_ARMOR_PVP_DAMAGE = DynamicGameRuleManager.booleanRule("ig:prevent_armor_pvp_damage", CATEGORY, false)
                .name("Prevent Armor PvP Damage")
                .description("If active, armor won't lose durability when damaged by other players.")
                .register();
    }
}
