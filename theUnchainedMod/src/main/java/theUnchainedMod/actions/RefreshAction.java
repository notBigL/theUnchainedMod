package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.patches.RelayHelpers;
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
        if (RelayHelpers.thisTurnRelayedDamage.get(player) > amount) {
            RelayHelpers.loseThisTurnRelayedDamage(amount, false, player);
            block = this.amount;
            restAmount = 0;
        } else {
            block = RelayHelpers.thisTurnRelayedDamage.get(player);
            restAmount -= RelayHelpers.thisTurnRelayedDamage.get(player);
            RelayHelpers.loseThisTurnRelayedDamage(false, player);
        }

        if (restAmount > 0) {
            if (RelayHelpers.nextTurnRelayedDamage.get(player) > restAmount) {
                RelayHelpers.loseNextTurnRelayedDamage(restAmount, false, player);
                block = this.amount;
            } else {
                block += RelayHelpers.nextTurnRelayedDamage.get(player);
                RelayHelpers.loseNextTurnRelayedDamage(false, player);
            }
        }
        if (block > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, block));
        }
        this.isDone = true;
    }
}
