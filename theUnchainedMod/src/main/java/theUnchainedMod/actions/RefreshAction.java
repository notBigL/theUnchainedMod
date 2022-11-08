package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.powers.NextTurnRelayedDamagePower;
import theUnchainedMod.powers.RelayedDamagePower;

public class RefreshAction extends AbstractGameAction {

    private final AbstractPlayer player;

    public RefreshAction(int amountToLoseAndDamage, AbstractPlayer player) {
        this.amount = amountToLoseAndDamage;
        this.player = player;
    }


    @Override
    public void update() {
        int block = 0;
        int restAmount = this.amount;
        if (player.hasPower(RelayedDamagePower.POWER_ID)) {
            AbstractPower relayedDamage = player.getPower(RelayedDamagePower.POWER_ID);
            if (this.amount < relayedDamage.amount) {
                relayedDamage.reducePower(this.amount);
                block = this.amount;
                restAmount = 0;
            } else {
                block = relayedDamage.amount;
                restAmount -= relayedDamage.amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, relayedDamage));
            }
        }
        if (restAmount > 0 && player.hasPower(NextTurnRelayedDamagePower.POWER_ID)) {
            AbstractPower nextTurnRelayedDamage = player.getPower(NextTurnRelayedDamagePower.POWER_ID);
            if (restAmount < nextTurnRelayedDamage.amount) {
                nextTurnRelayedDamage.reducePower(restAmount);
                block = this.amount;
            } else {
                block += nextTurnRelayedDamage.amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, nextTurnRelayedDamage));
            }
        }
        if (block > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, block));
        }
        this.isDone = true;
    }
}
