package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import theUnchainedMod.powers.RelayPower;

public class LongGameAction extends AbstractGameAction {

    public LongGameAction (AbstractPlayer player, int relayAmount) {
        this.amount = relayAmount;
        this.target = player;
    }


    @Override
    public void update() {
        if(target.hasPower("theUnchainedMod:MaladyPower")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new RelayPower(target, target, amount)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new DrawCardNextTurnPower(target, 1), 1));
        }
        this.isDone = true;
    }
}
