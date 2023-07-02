package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.powers.NextTurnRelayedDamagePower;
import theUnchainedMod.powers.RelayedDamagePower;

public class LoseRelayedDamageAction extends AbstractGameAction {

    public LoseRelayedDamageAction(int relayedDamageToLose) {
        this.amount = relayedDamageToLose;
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        int restAmount = this.amount;
        if (player.hasPower(RelayedDamagePower.POWER_ID)) {
            AbstractPower relayedDamagePower = player.getPower(RelayedDamagePower.POWER_ID);
            if (this.amount < relayedDamagePower.amount) {
                relayedDamagePower.reducePower(this.amount);
                restAmount = 0;
            } else {
                restAmount -= relayedDamagePower.amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, relayedDamagePower));
            }
        }
        if (restAmount > 0 && player.hasPower(NextTurnRelayedDamagePower.POWER_ID)) {
            AbstractPower nextTurnRelayedDamagePower = player.getPower(NextTurnRelayedDamagePower.POWER_ID);
            if (restAmount < nextTurnRelayedDamagePower.amount) {
                nextTurnRelayedDamagePower.reducePower(restAmount);
            } else {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, nextTurnRelayedDamagePower));
            }
        }
        this.isDone = true;
    }
}
