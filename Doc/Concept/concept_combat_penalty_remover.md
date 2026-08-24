# Combat Penalty Remover

## Philosophy Fit
**Instant Gratification (IG)**: "Respect the Player's Time, Not the Game's Rules." This mod aggressively strips away vanilla Minecraft's durability penalties incurred during combat, giving players granular control over exactly which penalties they want to remove without having to micromanage item repairs constantly or rely purely on Mending/Unbreaking.

## Mechanics
1. **Weapon Durability on Hit Penalty Removal**
   - **Description**: Reverts or prevents the specific extra baseline durability loss weapons receive when striking mobs directly (e.g. Swords losing 1 durability, Axes 2 durability per entity hit). 
   - **Implementation**: Mixin into `Item` and the `hurtEnemy` method for all specific weapon tools (Swords, Axes, Tridents).

2. **Armor Durability on Hurt Penalty Removal**
   - **Description**: Prevents armor pieces from taking damage/losing durability when the player takes incoming damage from specific combat sources (mobs, players).
   - **Implementation**: Intercept the `damageArmor` / `hurtArmor` methods within the Player/LivingEntity class and redirect the durability reduction if the GameRule passes.

3. **Shield Block Durability Penalty Removal**
   - **Description**: When blocking incoming damage that's 3.0+ points (like creepers or vindicators), shields normally take immense durability damage relative to the blocked amount. This neutralizes that, making the shield immortal during blocks while active.
   - **Implementation**: Mixin into the specific `ShieldItem` or `LivingEntity` damage calculation block event logic.

4. **Environmental/Non-Combat Armor Penalty Setting**
   - **Description**: Separate setting. Removes the durability damage armor takes from specific non-combat mechanics (falling, burning, drowning, cactus).
   - **Implementation**: Track damage sources via `DamageSource` checks in the armor-damaging mixin.

## Configuration (GameRules)
Native GameRules only via DasikLibrary.
- `ig:prevent_weapon_combat_damage` (Boolean, default `true` - disables durability loss when attacking enemies)
- `ig:prevent_armor_combat_damage` (Boolean, default `true` - disables armor taking damage from attacks)
- `ig:prevent_shield_block_damage` (Boolean, default `true` - disables shields taking durability hit when blocking)
- `ig:prevent_armor_environmental_damage` (Boolean, default `false` - disables armor taking damage from fall/fire)

## Project Metadata
- **Version Format**: `1.0.0+build.1`
- **Internal Dependency**: `"dasik-library": "*"` (Standalone)
- **Archive Strategy**: Store old build `.jar`s in `/Archive/builds/`

## Assets Needed
- **Particles**: None.
- **Sounds**: None.

## Quality Assurance [PRO PROTOCOL]
- **Debugging Commands**: 
  - `/gamerule preventArmorCombatDamage true` (Fight a horde of zombies, check armor durability after)
  - `/gamerule preventWeaponCombatDamage false` (Verify weapon breaks like vanilla if disabled)

## Implementation Checklist
- [x] Feature 1: Weapon Hit Logic interception
- [x] Feature 2: Armor Damage interception 
- [x] Feature 3: Shield Damage interception
- [x] Feature 4: External/Environmental Damage source checking
- [x] GameRule registration for each specific boolean setting
- [x] All Mixins fully verified against Vanilla's `damageItem` logic
- [x] Platform Docs updated (CurseForge/Modrinth)
