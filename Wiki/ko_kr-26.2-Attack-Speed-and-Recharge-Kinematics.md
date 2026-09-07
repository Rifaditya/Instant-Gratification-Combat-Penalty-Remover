# 26.2 공격 속도 및 충전 키네마틱스

> 📌 **저장소 소스 코드 면책 조항**: 이 위키의 문서는 **저장소의 현재 소스 코드 상태**를 반영하며, CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근 릴리스되지 않은 커밋이나 개발 중인 기능이 포함될 수 있습니다.

---

## 1. Official Infobox Table

| Parameter | Technical Details |
| :--- | :--- |
| **Feature Category** | Combat Kinematics & Attack Recharge Scaling |
| **Target Minecraft Version** | `26.2` |
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
* [[Minecraft 26.2 포털로 돌아가기|ko_kr-26.2-Home]]
* [[26.2 내구도 감소 및 페널티 제거|ko_kr-26.2-Durability-and-Penalty-Removals]]
* [[26.2 구성 및 게임 규칙(GameRules) 매트릭스|ko_kr-26.2-Configuration-and-GameRules]]
* [[26.2 아키텍처 및 믹스인(Mixins) 분석|ko_kr-26.2-Architecture-and-Mixins]]
