package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MakeRoomAction extends AbstractGameAction {

    public MakeRoomAction(AbstractPlayer player, int relayAmount) {
        this.target = player;
        this.amount = relayAmount;
    }

    @Override
    public void update() {
        if (target.currentBlock == 0) {
            AbstractDungeon.actionManager.addToBottom(new GainRelayAction(target, amount));
        }
        this.isDone = true;
    }
}
