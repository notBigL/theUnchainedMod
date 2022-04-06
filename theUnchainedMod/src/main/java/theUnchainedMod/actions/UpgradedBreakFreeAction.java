package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class UpgradedBreakFreeAction extends AbstractGameAction {

    private final AbstractPlayer player;

    public UpgradedBreakFreeAction(AbstractPlayer player) {
        this.player = player;
    }

    @Override
    public void update() {
        int energyGain = 0;
        for(AbstractPower power : player.powers) {
            if(power.type == AbstractPower.PowerType.DEBUFF) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, power));
                energyGain++;
            }
        }
        if(energyGain > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(energyGain));
        }
        this.isDone = true;
    }
}