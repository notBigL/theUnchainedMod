package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WinkAction extends AbstractGameAction {

    public WinkAction(AbstractMonster monster, int blockAmount) {
        this.target = monster;
        this.amount = blockAmount;
    }

    @Override
    public void update() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.target, this.amount));
        this.isDone = true;
    }
}
