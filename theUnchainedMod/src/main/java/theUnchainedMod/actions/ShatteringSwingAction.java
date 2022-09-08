package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class ShatteringSwingAction extends AbstractGameAction {

    private final AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public ShatteringSwingAction(int amount) {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
        } else {
            if (this.duration == Settings.ACTION_DUR_FASTER) {
                if (this.p.drawPile.isEmpty() || this.p.drawPile.size() == 1) {
                    this.isDone = true;
                    return;
                }

                if (this.p.drawPile.group.size() > this.amount) {
                    CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    Iterator var6 = this.p.drawPile.group.iterator();

                    while(var6.hasNext()) {
                        AbstractCard c = (AbstractCard)var6.next();
                        temp.addToTop(c);
                    }

                    temp.sortAlphabetically(true);
                    temp.sortByRarityPlusStatusCardType(false);
                    AbstractDungeon.gridSelectScreen.open(temp, 1, TEXT[0], false, false, false, false);
                    this.tickDuration();
                    return;
                }
            }

            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                Iterator var3 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var3.hasNext()) {
                    AbstractCard c = (AbstractCard)var3.next();
                    this.p.drawPile.removeCard(c);
                    this.p.hand.moveToDeck(c, false);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardPileToTopOfDeckAction");
        TEXT = uiStrings.TEXT;
    }
}
