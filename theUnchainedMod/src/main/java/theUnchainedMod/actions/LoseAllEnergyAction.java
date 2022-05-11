package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LoseAllEnergyAction extends AbstractGameAction {


    @Override
    public void update() {
        AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(99));
        this.isDone = true;
    }
}
