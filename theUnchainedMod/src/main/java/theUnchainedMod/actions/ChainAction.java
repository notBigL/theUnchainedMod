package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ChainAction extends AbstractGameAction {
    private final AbstractCreature player;
    private final AbstractCard card;
    private final AbstractCard.CardType cardType;
    private final AbstractGameAction finishedChainAction;
    private final String ChainPowerID;
    private final boolean finisher;

    public ChainAction(AbstractCreature p, AbstractCard c, AbstractCard.CardType cT, AbstractGameAction fCA, String iD) {
        player = p;
        card = c;
        cardType = cT;
        finishedChainAction = fCA;
        ChainPowerID = iD;
        finisher = false;

    }

    public ChainAction(AbstractCreature p, AbstractCard c, AbstractCard.CardType cT, AbstractGameAction fCA, String iD, boolean liberation) {
        player = p;
        card = c;
        cardType = cT;
        finishedChainAction = fCA;
        ChainPowerID = iD;
        finisher = liberation;
    }

    @Override
    public void update() {
        AbstractPower chainPower = player.getPower(ChainPowerID);
        if (chainPower != null) {
            if (finisher) {
                this.addToBot(finishedChainAction);
                player.powers.remove(chainPower);
                this.isDone = true;
            } else {
                --chainPower.amount;
                if (cardType != card.type) {
                    player.powers.remove(chainPower);
                } else if (chainPower.amount == 0) {
                    this.addToBot(finishedChainAction);
                    player.powers.remove(chainPower);
                }
                chainPower.updateDescription();
                this.isDone = true;
            }
        }


    }
}
