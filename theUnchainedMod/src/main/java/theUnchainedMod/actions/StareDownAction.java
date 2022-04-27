package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StareDownAction extends AbstractGameAction {

    public StareDownAction(int block) {
        this.amount = block;
    }

    @Override
    public void update() {
        int energyGain = 0;
        boolean allEnemiesBlocking = true;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.currentBlock > 0) {
                energyGain++;
            } else {
                allEnemiesBlocking = false;
            }
            AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(mo, this.amount));
        }
        if(allEnemiesBlocking) {
            energyGain++;
        }
        if (energyGain > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(energyGain));
        }
        this.isDone = true;
    }
}
