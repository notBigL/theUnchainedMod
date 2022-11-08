package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theUnchainedMod.powers.NextTurnRelayedDamagePower;
import theUnchainedMod.powers.RelayedDamagePower;

public class PoeticJusticeAction extends AbstractGameAction {

    public PoeticJusticeAction(int amount) {
        this.amount = amount;
        this.target = AbstractDungeon.player;
    }

    @Override
    public void update() {
        int vigor = 0;
        int restAmount = this.amount;
        if (this.target.hasPower(RelayedDamagePower.POWER_ID)) {
            AbstractPower relayedDamage = this.target.getPower(RelayedDamagePower.POWER_ID);
            if (this.amount < relayedDamage.amount) {
                relayedDamage.reducePower(this.amount);
                vigor = this.amount;
                restAmount = 0;
            } else {
                vigor = relayedDamage.amount;
                restAmount -= relayedDamage.amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.target, this.target, relayedDamage));
            }
        }
        if (restAmount > 0 && this.target.hasPower(NextTurnRelayedDamagePower.POWER_ID)) {
            AbstractPower nextTurnRelayedDamage = this.target.getPower(NextTurnRelayedDamagePower.POWER_ID);
            if (restAmount < nextTurnRelayedDamage.amount) {
                nextTurnRelayedDamage.reducePower(restAmount);
                vigor = this.amount;
            } else {
                vigor += nextTurnRelayedDamage.amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.target, this.target, nextTurnRelayedDamage));
            }
        }
        if (vigor > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.target, new VigorPower(this.target, vigor)));
        }
        this.isDone = true;
    }
}
