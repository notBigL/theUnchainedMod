package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.cards.Swirl;

import java.util.Iterator;

public class RevertSwirlToBaseValuesAction extends AbstractGameAction {

    public void update() {
        Iterator var1 = AbstractDungeon.player.discardPile.group.iterator();

        AbstractCard c;
        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof Swirl) {
                Swirl currentCard = (Swirl) c;
                currentCard.revertToBaseValues();
                currentCard.applyPowers();
            }
        }

        var1 = AbstractDungeon.player.drawPile.group.iterator();

        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof Swirl) {
                Swirl currentCard = (Swirl) c;
                currentCard.revertToBaseValues();
                currentCard.applyPowers();
            }
        }

        var1 = AbstractDungeon.player.hand.group.iterator();

        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof Swirl) {
                Swirl currentCard = (Swirl) c;
                currentCard.revertToBaseValues();
                currentCard.applyPowers();
            }
        }

        var1 = AbstractDungeon.player.exhaustPile.group.iterator();

        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof Swirl) {
                Swirl currentCard = (Swirl) c;
                currentCard.revertToBaseValues();
                currentCard.applyPowers();
            }
        }

        this.isDone = true;
    }
}
