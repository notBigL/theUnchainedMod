package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.cards.DemonicBoon;

import java.util.Iterator;

public class IncreaseCostOnDemonicBoonAction extends AbstractGameAction {

    public IncreaseCostOnDemonicBoonAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        Iterator var1 = AbstractDungeon.player.discardPile.group.iterator();

        AbstractCard c;
        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof DemonicBoon) {
                c.modifyCostForCombat(this.amount);
                if (c.cost != c.costForTurn) {
                    c.setCostForTurn(c.cost);
                }
            }
        }

        var1 = AbstractDungeon.player.drawPile.group.iterator();

        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof DemonicBoon) {
                c.modifyCostForCombat(this.amount);
                if (c.cost != c.costForTurn) {
                    c.setCostForTurn(c.cost);
                }
            }
        }

        var1 = AbstractDungeon.player.hand.group.iterator();

        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof DemonicBoon) {
                c.modifyCostForCombat(this.amount);
                if (c.cost != c.costForTurn) {
                    c.setCostForTurn(c.cost);
                }
            }
        }

        var1 = AbstractDungeon.player.exhaustPile.group.iterator();

        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof DemonicBoon) {
                c.modifyCostForCombat(this.amount);
                if (c.cost != c.costForTurn) {
                    c.setCostForTurn(c.cost);
                }
            }
        }

        this.isDone = true;
    }
}
