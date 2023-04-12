package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class MakeHandCardCostLessAction extends AbstractGameAction {

    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private final AbstractPlayer p;
    private ArrayList<AbstractCard> cannotBeMadeCheaper = new ArrayList();

    public MakeHandCardCostLessAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;

    }

    @Override
    public void update() {
        Iterator var1;
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {

            var1 = this.p.hand.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                if (c.costForTurn == 0 || c.cost == -2 || c.cost == -1) {
                    this.cannotBeMadeCheaper.add(c);
                }
            }

            if (this.cannotBeMadeCheaper.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() - this.cannotBeMadeCheaper.size() == 1) {
                var1 = this.p.hand.group.iterator();

                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    if (c.costForTurn > 0) {
                        int oldCost = c.costForTurn;
                        c.setCostForTurn(oldCost - 1);
                        c.superFlash();
                        c.applyPowers();
                        this.isDone = true;
                        return;
                    }
                }
            }

            this.p.hand.group.removeAll(this.cannotBeMadeCheaper);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                this.tickDuration();
                return;
            }

            if (this.p.hand.group.size() == 1) {
                int oldCost = this.p.hand.getTopCard().costForTurn;
                this.p.hand.getTopCard().setCostForTurn(oldCost - 1);
                this.p.hand.getTopCard().superFlash();
                this.returnCards();
                this.isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                int oldCost = c.costForTurn;
                c.setCostForTurn(oldCost - 1);
                c.superFlash();
                c.applyPowers();
                this.p.hand.addToTop(c);
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }

    private void returnCards() {
        Iterator var1 = this.cannotBeMadeCheaper.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("theUnchainedMod:MakeHandCardCostLessAction");
        TEXT = uiStrings.TEXT;
    }
}
