package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class UpgradeCardAfterPlayingAction extends AbstractGameAction {

    private final AbstractCard cardToUpgrade;
    private final int amount;

    public UpgradeCardAfterPlayingAction(AbstractCard c, int amount) {
        this.cardToUpgrade = c;
        this.amount = amount;
    }

    @Override
    public void update() {

        for (int i = amount; i > 0; i--) {
            if (cardToUpgrade.canUpgrade()) {
                cardToUpgrade.upgrade();
            } else {
                this.isDone = true;
                return;
            }
        }
        this.isDone = true;
    }
}
