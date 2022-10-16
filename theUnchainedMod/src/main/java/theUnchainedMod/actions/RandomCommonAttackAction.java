package theUnchainedMod.actions;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.util.UtilityClass;

public class RandomCommonAttackAction extends AbstractGameAction {

    private final boolean upgraded;

    public RandomCommonAttackAction(boolean upgraded) {
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        AbstractCard c = UtilityClass.returnTrulyRandomCommonAttackCardInCombat();
        if(upgraded) {
            c.upgrade();
        }
        c.setCostForTurn(0);
        CardModifierManager.addModifier(c, new ExhaustMod());

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
        this.isDone = true;
    }
}
