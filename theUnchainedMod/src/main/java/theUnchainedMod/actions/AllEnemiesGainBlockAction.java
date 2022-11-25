package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AllEnemiesGainBlockAction extends AbstractGameAction {


    public AllEnemiesGainBlockAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo != null && !mo.isDeadOrEscaped()) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(mo, this.amount));
                }
            }
        }
        this.isDone = true;
    }
}

