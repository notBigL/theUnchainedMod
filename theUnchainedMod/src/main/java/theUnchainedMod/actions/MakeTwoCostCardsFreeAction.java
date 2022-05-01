package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class MakeTwoCostCardsFreeAction extends AbstractGameAction {
    private final AbstractPlayer player;
    private final boolean upgrade;

    public MakeTwoCostCardsFreeAction(boolean alsoUpgrade) {
        player = AbstractDungeon.player;
        this.upgrade = alsoUpgrade;
    }

    @Override
    public void update() {
        Iterator var1;
        AbstractCard c;
        var1 = this.player.hand.group.iterator();
        loopThrough(var1);
        var1 = this.player.drawPile.group.iterator();
        loopThrough(var1);
        var1 = this.player.discardPile.group.iterator();
        loopThrough(var1);
        var1 = this.player.exhaustPile.group.iterator();
        loopThrough(var1);
        this.isDone = true;
    }

    private void loopThrough(Iterator iterator) {
        AbstractCard c;
        while (iterator.hasNext()) {
            c = (AbstractCard) iterator.next();
            if (c.cost == 2) {
                if (this.upgrade) {
                    c.upgrade();
                }
                c.setCostForTurn(0);
                c.superFlash();
                c.applyPowers();
            }
        }

    }
}
