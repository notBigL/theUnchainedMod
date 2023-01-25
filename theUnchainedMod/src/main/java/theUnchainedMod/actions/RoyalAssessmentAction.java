package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RoyalAssessmentAction extends AbstractGameAction {

    private final boolean upgraded;

    public RoyalAssessmentAction(boolean upgraded) {
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        int energyGain = 0;
        boolean allEnemiesBlocking = true;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped() && mo.currentBlock > 0) {
                energyGain++;
            } else if (!mo.isDeadOrEscaped()) {
                allEnemiesBlocking = false;
            }
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
