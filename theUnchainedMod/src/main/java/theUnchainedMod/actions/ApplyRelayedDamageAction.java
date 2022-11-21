package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.patches.RelayHelpers;
import theUnchainedMod.patches.RelayedDmgSum;

public class ApplyRelayedDamageAction extends AbstractGameAction {

    public ApplyRelayedDamageAction(AbstractCreature creature, int amount) {
        this.target = creature;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (target != null && amount > 0) {
            RelayedDmgSum.relayedDamageSum.set(AbstractDungeon.actionManager, RelayedDmgSum.relayedDamageSum.get(AbstractDungeon.actionManager) + amount);
            RelayHelpers.addNextTurnRelayedDamage(amount, target);
        }
        isDone = true;
    }
}
