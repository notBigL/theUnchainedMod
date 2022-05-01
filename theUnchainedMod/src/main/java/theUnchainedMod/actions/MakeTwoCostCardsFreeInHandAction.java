package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class MakeTwoCostCardsFreeInHandAction extends AbstractGameAction {
    private final AbstractPlayer player;
    private final boolean upgrade;

    public MakeTwoCostCardsFreeInHandAction(boolean alsoUpgrade) {
        player = AbstractDungeon.player;
        this.upgrade = alsoUpgrade;
    }

    @Override
    public void update() {
        Iterator var1;
        AbstractCard c;
        var1 = this.player.hand.group.iterator();

        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c.cost == 2) {
                if (this.upgrade) {
                    c.upgrade();
                }
                c.setCostForTurn(0);
                c.superFlash();
                c.applyPowers();
            }
        }

        this.isDone = true;
    }
}
