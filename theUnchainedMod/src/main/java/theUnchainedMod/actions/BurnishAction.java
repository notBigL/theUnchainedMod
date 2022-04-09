package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theUnchainedMod.powers.MomentumPower;

import java.util.Iterator;

public class BurnishAction extends AbstractGameAction {
    private final AbstractPlayer player;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RecycleAction");
    public static final String[] TEXT = uiStrings.TEXT;


    public BurnishAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.player = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.player.hand.isEmpty()) {
                this.isDone = true;
            } else if (this.player.hand.size() == 1) {
                if (this.player.hand.getBottomCard().costForTurn > 0) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new MomentumPower(player, this.player.hand.getBottomCard().costForTurn)));
                }
                this.player.hand.moveToExhaustPile(this.player.hand.getBottomCard());
                this.tickDuration();
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c;
                for (Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); this.player.hand.moveToExhaustPile(c)) {
                    c = (AbstractCard) var1.next();
                    if (c.costForTurn > 0) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new MomentumPower(player, c.costForTurn)));
                    }
                }
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }
            this.tickDuration();
        }
    }
}
