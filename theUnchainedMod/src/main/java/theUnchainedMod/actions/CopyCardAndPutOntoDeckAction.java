package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class CopyCardAndPutOntoDeckAction extends AbstractGameAction {

    private final AbstractPlayer p;
    private final boolean upgraded;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CopyAction");
    public static final String[] TEXT = uiStrings.TEXT;

    public CopyCardAndPutOntoDeckAction(boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (p.hand.size() == 0) {
                this.isDone = true;
            } else if (p.hand.size() == 1) {
                c = this.p.hand.getTopCard();
                this.p.hand.moveToDeck(c.makeStatEquivalentCopy(), false);
                if (upgraded) {
                    this.p.hand.moveToDeck(c.makeStatEquivalentCopy(), false);
                }
                this.isDone = true;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                c = AbstractDungeon.handCardSelectScreen.selectedCards.getTopCard();
                this.p.hand.moveToDeck(c.makeStatEquivalentCopy(), false);
                if (upgraded) {
                    this.p.hand.moveToDeck(c.makeStatEquivalentCopy(), false);
                }
                AbstractDungeon.player.hand.addToHand(c);
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }
        }
        this.tickDuration();
    }
}
