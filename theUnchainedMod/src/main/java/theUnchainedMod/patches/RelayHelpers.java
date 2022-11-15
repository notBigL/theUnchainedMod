package theUnchainedMod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.powers.FractalShieldPower;
import theUnchainedMod.powers.NextTurnRelayedDamagePower;


@SpirePatch(
        clz = AbstractCreature.class,
        method = SpirePatch.CLASS
)
public class RelayHelpers {
    public static SpireField<Integer> currentRelay = new SpireField<>(() -> 0);
    public static Color relayTextColor;


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

/*        if (effect && RelayHelpers.currentRelay.get(player) > 0) {
            this.gainBlockAnimation();
        } else if (relayAmount > 0) {
            Color tmpCol = Settings.GOLD_COLOR.cpy();
            tmpCol.a = this.blockTextColor.a;
            this.blockTextColor = tmpCol;
            this.blockScale = 5.0F;
        }*/
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

        /*if (RelayHelpers.currentRelay.get(creature) == 0 && effect) {
            if (!noAnimation) {
                AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0F + BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0F + BLOCK_ICON_Y));
            }
        } else if (RelayHelpers.currentRelay.get(creature) > 0 && amount > 0) {
            Color tmp = Color.SCARLET.cpy();
            tmp.a = this.blockTextColor.a;
            this.blockTextColor = tmp;
            this.blockScale = 5.0F;
        }*/

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
