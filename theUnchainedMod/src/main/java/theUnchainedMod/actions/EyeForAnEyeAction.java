package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theUnchainedMod.patches.RelayHelpers;

public class EyeForAnEyeAction extends AbstractGameAction {

    private final AbstractCreature player;

    public EyeForAnEyeAction(int amount, AbstractCreature p) {
        this.amount = amount;
        this.player = p;
        this.target = AbstractDungeon.player;
    }

    @Override
    public void update() {
        int vigor = 0;
        int restAmount = this.amount;
        if (RelayHelpers.thisTurnRelayedDamage.get(player) > amount) {
            RelayHelpers.loseThisTurnRelayedDamage(amount, false, player);
            vigor = this.amount;
            restAmount = 0;
        } else {
            vigor = RelayHelpers.thisTurnRelayedDamage.get(player);
            restAmount -= RelayHelpers.thisTurnRelayedDamage.get(player);
            RelayHelpers.loseThisTurnRelayedDamage(false, player);
        }

        if (restAmount > 0) {
            if (RelayHelpers.nextTurnRelayedDamage.get(player) > restAmount) {
                RelayHelpers.loseNextTurnRelayedDamage(restAmount, false, player);
                vigor = this.amount;
            } else {
                vigor += RelayHelpers.nextTurnRelayedDamage.get(player);
                RelayHelpers.loseNextTurnRelayedDamage(false, player);
            }
        }
        if (vigor > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.target, new VigorPower(this.target, vigor)));
        }
        this.isDone = true;
    }
}
