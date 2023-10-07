package theUnchainedMod.actions;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.util.UtilityClass;

public class RandomTwoCostCardAction extends AbstractGameAction {

    public RandomTwoCostCardAction(int cost) {
        this.amount = cost;
    }

    @Override
    public void update() {
        AbstractCard c = UtilityClass.returnTrulyRandomTwoCostCardInCombat().makeCopy();
        c.setCostForTurn(amount);
        /*if (c.type != AbstractCard.CardType.POWER) {
            CardModifierManager.addModifier(c, new ExhaustMod());
        }*/
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
        this.isDone = true;
    }
}
