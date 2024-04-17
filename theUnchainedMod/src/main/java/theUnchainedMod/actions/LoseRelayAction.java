package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theUnchainedMod.patches.RelayHelpers;

public class LoseRelayAction extends AbstractGameAction {

    public LoseRelayAction(AbstractCreature target) {
        this.target = target;
    }

    @Override
    public void update() {
        if (!target.isDying && !target.isDead) {

            RelayHelpers.loseRelay(false, target);
        }
        isDone = true;
    }
}
