package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theUnchainedMod.powers.*;


public class ChainAction extends AbstractGameAction {
    private final AbstractCreature player;
    private final AbstractCard card;
    private final AbstractCard.CardType cardType;
    private final String ChainPowerID;
    private final boolean liberation;

    public ChainAction(AbstractCreature p, AbstractCard c, AbstractCard.CardType cT, String iD) {
        player = p;
        card = c;
        cardType = cT;
        ChainPowerID = iD;
        this.liberation = false;
    }

    public ChainAction(AbstractCreature p, AbstractCard c, AbstractCard.CardType cT, String iD, boolean liberation) {
        player = p;
        card = c;
        cardType = cT;
        ChainPowerID = iD;
        this.liberation = liberation;
    }

    @Override
    public void update() {
        AbstractChainPower chainPower = (AbstractChainPower) player.getPower(ChainPowerID);
        if (chainPower != null) {
            if (liberation) {
                chainPower.finishMe();
            } else {
                if (cardType == card.type) {
                    --chainPower.amount;
                }
                if (chainPower.amount == 0) {
                    chainPower.finishMe();
                }
                chainPower.updateDescription();
            }
            this.isDone = true;
        }
    }
}
