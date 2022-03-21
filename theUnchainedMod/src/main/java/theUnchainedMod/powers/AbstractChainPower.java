package theUnchainedMod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.actions.ChainAction;

public class AbstractChainPower extends AbstractPower {
    private static int chainIdOffset;
    public final AbstractGameAction finishedChainAction;
    public final AbstractCard.CardType cardType;


    public AbstractChainPower(String powerID, AbstractCreature player, int chainLength, AbstractGameAction finishedChainAction, AbstractCard.CardType cardType) {
        this.ID = powerID + chainIdOffset;
        chainIdOffset++;
        this.owner = player;
        this.amount = chainLength;
        this.finishedChainAction = finishedChainAction;
        this.cardType = cardType;
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }


    public void onUseCard(AbstractCard c, UseCardAction action) {
        if (c.cardID.equals("TheUnchainedMod:Liberation")) {
            this.addToBot(new ChainAction(this.owner, c, this.cardType, finishedChainAction, this.ID, true));
        } else {
            this.addToBot(new ChainAction(this.owner, c, this.cardType, finishedChainAction, this.ID));
            this.flash();
        }
    }
}
