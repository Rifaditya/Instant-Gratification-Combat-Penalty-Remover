# 26.3 Скорость атаки и кинематика перезарядки

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Вики отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или разрабатываемые функции, опережающие публичные сборки на CurseForge и Modrinth.

---

## 1. Official Infobox Table

| Parameter | Technical Details |
| :--- | :--- |
| **Feature Category** | Combat Kinematics & Attack Recharge Scaling |
| **Target Minecraft Version** | `26.3-snapshot-6` |
| **Target Classes** | `net.minecraft.world.entity.player.Player` |
| **Target Method** | `getCurrentItemAttackStrengthDelay()` |
| **ThreadLocal State** | `CombatPenaltyRules.CAPTURED_ATTACK_STRENGTH` |
| **Controlling GameRule** | `ig:attack_speed_efficiency` (Integer, Default: `100`) |
| **Mathematical Property** | Inversely Proportional Delay Scaling ($T_{\text{delay}} \propto 1/M$) |
| **Player Agency Guard** | Unbounded Positive Range ($1 \le E \le 2,147,483,647$) |

---

## 2. Step-by-Step Player Workflow

```
[ Attack Speed Kinematics Workflow ]
1. Player equips weapon (Sword attack speed: 1.6 attacks/sec; Axe: 0.8-1.0 attacks/sec).
2. Server evaluates GameRule 'ig:attack_speed_efficiency':
   - Default (100%): Vanilla recharge delay (e.g. 12.5 ticks for swords).
   - Double (200%): Delay cut in half (6.25 ticks). Weapon recharges 2x faster.
   - 1.8 Mode (1000%+): Delay drops below 1 tick. Immediate recharge allows spam clicking.
3. Player initiates attack:
   - PlayerMixin intercepts Player.attack() at HEAD.
   - Attack strength scale (0.5f offset) captured into ThreadLocal float.
   - ItemStackMixin reads ThreadLocal to assess spam status.
4. Player.getCurrentItemAttackStrengthDelay() modified at RETURN to scale recovery time.
```

---

## 3. Mathematical Formulas & Recharge Curves

### 1. Base Vanilla Attack Delay
In vanilla Minecraft, attack delay $T_{\text{base}}$ (in ticks) is inversely proportional to the player's generic attack speed attribute:
$$T_{\text{base}} = \frac{20}{\text{generic.attack\_speed}}$$
*Example (Diamond Sword, speed = 1.6)*:
$$T_{\text{base}} = \frac{20}{1.6} = 12.5 \text{ ticks} = 0.625 \text{ seconds}$$

### 2. Efficiency Multiplier & Inverse Delay
With Combat Penalty Remover efficiency scaling $E_{\text{efficiency}}$:
$$M = \frac{E_{\text{efficiency}}}{100.0}$$
$$T_{\text{delay}} = \frac{T_{\text{base}}}{\max(M, 0.01)}$$
Substituting $T_{\text{base}}$:
$$T_{\text{delay}} = \frac{20}{\text{generic.attack\_speed} \cdot \left(\frac{E_{\text{efficiency}}}{100.0}\right)}$$

### 3. Key Kinematic Thresholds
| Efficiency $E$ | Multiplier $M$ | Sword Delay ($T_{\text{delay}}$) | Effective Attacks / Sec | Combat Style Equivalent |
| :--- | :--- | :--- | :--- | :--- |
| **50** | $0.5\times$ | $25.0\text{ ticks}$ ($1.25\text{s}$) | $0.8\text{ atk/s}$ | Slow / Heavy Sluggish Mode |
| **100** | $1.0\times$ | $12.5\text{ ticks}$ ($0.625\text{s}$) | $1.6\text{ atk/s}$ | Vanilla Default |
| **200** | $2.0\times$ | $6.25\text{ ticks}$ ($0.312\text{s}$) | $3.2\text{ atk/s}$ | Fast Paced Action |
| **500** | $5.0\times$ | $2.50\text{ ticks}$ ($0.125\text{s}$) | $8.0\text{ atk/s}$ | High-Speed Hack & Slash |
| **1000** | $10.0\times$ | $1.25\text{ ticks}$ ($0.062\text{s}$) | $16.0\text{ atk/s}$ | 1.8 Classic Spam Combat |
| **10000** | $100.0\times$ | $0.125\text{ ticks}$ (Instant) | Uncapped | True Sandbox Freedom |

---

## 4. Visual ASCII State Cycle

```
[ Player Clicks Attack ]
         |
         v
+-------------------------------------------------------------+
| Player.attack() [HEAD Inject]                               |
| -> CAPTURED_ATTACK_STRENGTH.set(getAttackStrengthScale(0.5F))|
+-------------------------------------------------------------+
         |
         v
+-------------------------------------------------------------+
| Damage Dealt to Target                                      |
| -> ItemStack.postHurtEnemy() [HEAD Inject]                  |
| -> Reads ThreadLocal float scale                            |
| -> Cancels durability if scale < 0.90F                     |
+-------------------------------------------------------------+
         |
         v
+-------------------------------------------------------------+
| Player.getCurrentItemAttackStrengthDelay() [RETURN Inject]  |
| -> return originalDelay / (efficiency / 100.0F)             |
+-------------------------------------------------------------+
         |
         v
[ Attack Meter Recharges at Accelerated Kinematic Rate ]
```

---

## 5. SNBT & Codec Attribute Schemas

The attack recharge calculation operates on the vanilla entity attribute:
```json
{
  "id": "minecraft:generic.attack_speed",
  "base": 4.0,
  "modifiers": [
    {
      "id": "minecraft:weapon_attack_speed",
      "amount": -2.4,
      "operation": "add_value"
    }
  ]
}
```

---

## 6. Exhaustive Reference Tables

| Parameter | Type | Default | Valid Range | Function |
| :--- | :--- | :--- | :--- | :--- |
| `ig:attack_speed_efficiency` | `Integer` | `100` | $1 \le E \le 2,147,483,647$ | Percentage speed multiplier |
| `CAPTURED_ATTACK_STRENGTH` | `ThreadLocal<Float>` | `1.0F` | $0.0F \le S \le 1.0F$ | Snapshot of recharge meter at attack instant |
| `SPAM_THRESHOLD` | `float` | `0.90F` | Constant | Durability protection boundary |

---

## 7. Developer & Mixin Hooks

```java
// PlayerMixin.java
@Inject(method = "getCurrentItemAttackStrengthDelay", at = @At("RETURN"), cancellable = true)
private void cpr$applyAttackSpeedEfficiency(CallbackInfoReturnable<Float> cir) {
    Player player = (Player) (Object) this;
    if (player.level() instanceof ServerLevel serverLevel) {
        int efficiency = serverLevel.getGameRules().get(CombatPenaltyRules.ATTACK_SPEED_EFFICIENCY);
        if (efficiency != 100 && efficiency > 0) {
            float multiplier = efficiency / 100.0F;
            cir.setReturnValue(cir.getReturnValue() / multiplier);
        }
    }
}
```

---

## 🔗 Related Documentation Links
* [[Вернуться к порталу Minecraft 26.3|ru_ru-26.3-Home]]
* [[26.3 Устранение расхода прочности и штрафов|ru_ru-26.3-Durability-and-Penalty-Removals]]
* [[26.3 Конфигурация и справочник игровых правил|ru_ru-26.3-Configuration-and-GameRules]]
* [[26.3 Анализ архитектуры и миксинов|ru_ru-26.3-Architecture-and-Mixins]]
