package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.powers.AbstractChainPower;
import theUnchainedMod.powers.AbstractMasterChainPower;

public class ReinforcedElementAction extends AbstractGameAction {


    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;

        for (AbstractPower p : player.powers) {
            if (p instanceof AbstractChainPower || p instanceof AbstractMasterChainPower) {
                this.addToTop(new DrawCardAction(player, 1));
                this.addToTop(new GainEnergyAction(1));
                break;
            }
        }
        this.isDone = true;
    }
}
