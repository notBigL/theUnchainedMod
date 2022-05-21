package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.powers.RelayPower;

public class PeaceOfferingAction extends AbstractGameAction {

    public PeaceOfferingAction(int relayAmount) {
        this.amount = relayAmount;
    }


    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        if (this.amount > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new RelayPower(player, player, this.amount)));
        }
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.currentBlock > 0) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(mo, mo.currentBlock));
            }
        }
        this.isDone = true;
    }
}