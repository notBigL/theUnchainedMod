package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.powers.AbstractChainPower;
import theUnchainedMod.powers.AbstractMasterChainPower;
import theUnchainedMod.powers.ArcaneMasteryPower;

public class ArcaneLinkAction extends AbstractGameAction {

    public ArcaneLinkAction (int masteryAmount)
    {
        amount = masteryAmount;
    }
    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;

        for (AbstractPower p : player.powers) {
            if (p instanceof AbstractChainPower || p instanceof AbstractMasterChainPower) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ArcaneMasteryPower(player, amount)));
                break;
            }
        }
        this.isDone = true;
    }
}
