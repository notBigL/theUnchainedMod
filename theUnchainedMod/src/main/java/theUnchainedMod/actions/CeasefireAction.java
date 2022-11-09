package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.powers.RelayPower;

public class CeasefireAction extends AbstractGameAction {

    public CeasefireAction() {
        this.amount = 0;
        if (AbstractDungeon.player.hasPower(RelayPower.POWER_ID)) {
            this.amount = AbstractDungeon.player.getPower(RelayPower.POWER_ID).amount;
        }
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        if (this.amount > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainRelayAction(player, this.amount));        }
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.currentBlock > 0 && !mo.isDeadOrEscaped()) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(mo, mo.currentBlock));
            }
        }
        this.isDone = true;
    }
}
