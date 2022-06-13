package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StareDownAction extends AbstractGameAction {

    private final boolean upgraded;

    public StareDownAction(int block, boolean upgraded) {
        this.amount = block;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        int energyGain = 0;
        boolean allEnemiesBlocking = true;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.currentBlock > 0) {
                energyGain++;
            } else if (!mo.isDead) {
                allEnemiesBlocking = false;
            }
            AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(mo, this.amount));
        }
        if (allEnemiesBlocking && this.upgraded) {
            energyGain++;
        }
        if (energyGain > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(energyGain));
        }
        this.isDone = true;
    }
}
