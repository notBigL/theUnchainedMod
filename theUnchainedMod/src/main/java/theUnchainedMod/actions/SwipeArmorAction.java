package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SwipeArmorAction extends AbstractGameAction {

    boolean doubleAmount;

    public SwipeArmorAction(boolean doubleAmount) {
    this.doubleAmount = doubleAmount;
    }

    @Override
    public void update() {
        int blockAmount = 0;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.currentBlock > 0) {
                blockAmount += mo.currentBlock;
            }
        }
        if (blockAmount > 0) {
            blockAmount = blockAmount * 2;
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, blockAmount));
        }
    }
}
