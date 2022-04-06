package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BreakFreeAction extends AbstractGameAction {

    private final AbstractPlayer player;

    public BreakFreeAction(AbstractPlayer player) {
        this.player = player;
    }

    @Override
    public void update() {
        int energyGain = 0;
        if(player.hasPower("Weakened")) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, "Weakened"));
            energyGain++;
        }
        if(player.hasPower("Frail")) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, "Frail"));
            energyGain++;
        }
        if(player.hasPower("Vulnerable")) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, "Vulnerable"));
            energyGain++;
        }
        if(energyGain > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(energyGain));
        }
        this.isDone = true;
    }
}
