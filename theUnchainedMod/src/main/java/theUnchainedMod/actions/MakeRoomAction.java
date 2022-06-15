package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MakeRoomAction extends AbstractGameAction {

    public MakeRoomAction(AbstractPlayer player, int energyAmount) {
        this.target = player;
        this.amount = energyAmount;
    }

    @Override
    public void update() {
        if (target.currentBlock > 0) {
            this.isDone = true;
        } else {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(amount));
        }
        this.isDone = true;
    }
}
