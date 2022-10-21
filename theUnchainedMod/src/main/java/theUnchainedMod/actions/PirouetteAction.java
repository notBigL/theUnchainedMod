package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.cards.PerfectStep;
import theUnchainedMod.cards.Swirl;

import java.util.Iterator;

public class PirouetteAction extends AbstractGameAction {

    @Override
    public void update() {
        Iterator var1;
        AbstractCard c;

        var1 = AbstractDungeon.player.hand.group.iterator();

        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if ((c instanceof Swirl || c instanceof PerfectStep) && c.canUpgrade()) {
                c.upgrade();
                c.superFlash();
                c.applyPowers();
            }
        }
        this.isDone = true;
    }
}
