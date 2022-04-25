package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.powers.AbstractChainPower;

public class OverHeadSwingEndTurnAction extends AbstractGameAction {

    private final AbstractPlayer player;

    public OverHeadSwingEndTurnAction(AbstractPlayer p) {
        player = p;
    }

    @Override
    public void update() {

        for (AbstractPower p : player.powers) {
            if (p instanceof AbstractChainPower) {
                AbstractDungeon.actionManager.addToBottom(new PressEndTurnButtonAction());
                break;
            }
        }
        this.isDone = true;
    }
}
