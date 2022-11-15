package theUnchainedMod.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import theUnchainedMod.powers.FractalShieldPower;
import theUnchainedMod.powers.NextTurnRelayedDamagePower;


@SpirePatch(
        clz = AbstractCreature.class,
        method = SpirePatch.CLASS
)
public class RelayHelpers {
    public static SpireField<Integer> currentRelay = new SpireField<>(() -> 0);

    public static SpireField<Color> relayColor = new SpireField<>(() -> new Color(0.6F, 0.93F, 0.98F, 0.0F));
    public static SpireField<Color> relayTextColor = new SpireField<>(() -> new Color(0.9F, 0.9F, 0.9F, 0.0F));
    public static SpireField<Color> relayOutlineColor = new SpireField<>(() -> new Color(0.6F, 0.93F, 0.98F, 0.0F));
    public static SpireField<Float> relayAnimTimer = new SpireField<>(() -> 0.0F);
    public static SpireField<Float> relayScale = new SpireField<>(() -> 1.0F);


    public static void addRelay(int relayAmount, AbstractCreature creature) {
        float tmp = (float) relayAmount;

        boolean effect = false;
        if (RelayHelpers.currentRelay.get(creature) == 0) {
            effect = true;
        }

        RelayHelpers.currentRelay.set(creature, RelayHelpers.currentRelay.get(creature) + MathUtils.floor(tmp));

        if (RelayHelpers.currentRelay.get(creature) > 999) {
            RelayHelpers.currentRelay.set(creature, 999);
        }

        if (effect && RelayHelpers.currentRelay.get(creature) > 0) {
            RelayHelpers.gainRelayAnimation(creature);
        } else if (relayAmount > 0) {
            Color tmpCol = Settings.GOLD_COLOR.cpy();
            tmpCol.a = RelayHelpers.relayTextColor.get(creature).a;
            RelayHelpers.relayTextColor.set(creature, tmpCol);
            RelayHelpers.relayScale.set(creature, 5.0F);
        }
    }

    public static void loseRelay(boolean noAnimation, AbstractCreature creature) {
        loseRelay(RelayHelpers.currentRelay.get(creature), noAnimation, creature);
    }

    public static void loseRelay(int amount, boolean noAnimation, AbstractCreature creature) {
        boolean effect = false;

        if (RelayHelpers.currentRelay.get(creature) != 0) {
            effect = true;
        }

        RelayHelpers.currentRelay.set(creature, RelayHelpers.currentRelay.get(creature) - amount);
        if (RelayHelpers.currentRelay.get(creature) < 0) {
            RelayHelpers.currentRelay.set(creature, 0);
        }

        if (RelayHelpers.currentRelay.get(creature) == 0 && effect) {
            /*if (!noAnimation) {
                AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0F + BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0F + BLOCK_ICON_Y));
            }*/
        } else if (RelayHelpers.currentRelay.get(creature) > 0 && amount > 0) {
            Color tmp = Color.SCARLET.cpy();
            tmp.a = RelayHelpers.relayTextColor.get(creature).a;
            RelayHelpers.relayTextColor.set(creature, tmp);
            RelayHelpers.relayScale.set(creature, 5.0F);
        }

    }

    private static void gainRelayAnimation(AbstractCreature creature) {
        RelayHelpers.relayAnimTimer.set(creature, 0.7F);
        RelayHelpers.relayTextColor.get(creature).a = 0.0F;
        RelayHelpers.relayColor.get(creature).a = 0.0F;
    }

    public static void updateRelayAnimations(AbstractCreature creature) {
        if (RelayHelpers.currentRelay.get(creature) > 0) {
            if (RelayHelpers.relayAnimTimer.get(creature) > 0.0F) {
                RelayHelpers.relayAnimTimer.set(creature, RelayHelpers.relayAnimTimer.get(creature) - Gdx.graphics.getDeltaTime());
                if (RelayHelpers.relayAnimTimer.get(creature) < 0.0F) {
                    RelayHelpers.relayAnimTimer.set(creature, 0.0F);
                }

                //this.blockOffset = Interpolation.swingOut.apply(BLOCK_OFFSET_DIST * 3.0F, 0.0F, 1.0F - this.blockAnimTimer / 0.7F);
                RelayHelpers.relayScale.set(creature, Interpolation.pow3In.apply(3.0F, 1.0F, 1.0F - RelayHelpers.relayAnimTimer.get(creature) / 0.7F));
                RelayHelpers.relayColor.get(creature).a = Interpolation.pow2Out.apply(0.0F, 1.0F, 1.0F - RelayHelpers.relayAnimTimer.get(creature) / 0.7F);
                RelayHelpers.relayTextColor.get(creature).a = Interpolation.pow5In.apply(0.0F, 1.0F, 1.0F - RelayHelpers.relayAnimTimer.get(creature) / 0.7F);
            } else if (RelayHelpers.relayScale.get(creature) != 1.0F) {
                RelayHelpers.relayScale.set(creature, MathHelper.scaleLerpSnap(RelayHelpers.relayScale.get(creature), 1.0F));
            }

            if (RelayHelpers.relayTextColor.get(creature).r != 1.0F) {
                RelayHelpers.relayTextColor.get(creature).r = MathHelper.slowColorLerpSnap(RelayHelpers.relayTextColor.get(creature).r, 1.0F);
            }

            if (RelayHelpers.relayTextColor.get(creature).g != 1.0F) {
                RelayHelpers.relayTextColor.get(creature).g = MathHelper.slowColorLerpSnap(RelayHelpers.relayTextColor.get(creature).g, 1.0F);
            }

            if (RelayHelpers.relayTextColor.get(creature).b != 1.0F) {
                RelayHelpers.relayTextColor.get(creature).b = MathHelper.slowColorLerpSnap(RelayHelpers.relayTextColor.get(creature).b, 1.0F);
            }
        }

    }



    public static int relayDamageWhenAttacked(DamageInfo info, int damageAmount, AbstractCreature creature) {
        if (damageAmount > 0 && (!RelayedDamageField.relayed.get(info) || creature.hasPower(FractalShieldPower.POWER_ID))) {
            int relayedDamage;
            if (RelayHelpers.currentRelay.get(creature) >= damageAmount) {
                loseRelay(damageAmount, false, creature);
                relayedDamage = damageAmount;
                damageAmount = 0;
            } else {
                damageAmount -= RelayHelpers.currentRelay.get(creature);
                relayedDamage = RelayHelpers.currentRelay.get(creature);
                loseRelay(false, creature);
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(creature, creature, new NextTurnRelayedDamagePower(creature, creature, relayedDamage)));
        }
        //if (RelayHelpers.currentRelay.get(creature) == 0) {
        //    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        //}
        return damageAmount;
    }
}
