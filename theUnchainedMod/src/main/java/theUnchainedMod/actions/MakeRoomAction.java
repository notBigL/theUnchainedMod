package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.powers.RelayPower;

public class MakeRoomAction extends AbstractGameAction {

    public MakeRoomAction(AbstractPlayer player, int drawAmount) {
        this.target = player;
        this.amount = drawAmount;
    }

    @Override
    public void update() {
        if(target.hasPower(RelayPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(amount));
        }
        this.isDone = true;
    }
}
