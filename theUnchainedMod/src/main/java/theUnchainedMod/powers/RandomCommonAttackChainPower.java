package theUnchainedMod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.RandomCommonAttackAction;

public class RandomCommonAttackChainPower extends AbstractChainPower {
    public AbstractCreature source;

    public static final String POWER_ID = TheUnchainedMod.makeID("RandomCommonAttackChainPower");
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerstrings.NAME;
    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static AbstractPower.PowerType POWER_TYPE = PowerType.BUFF;


    public RandomCommonAttackChainPower(AbstractCreature owner, int amount, boolean upgraded, AbstractCard.CardType cardType) {
        super(POWER_ID, owner, amount, new RandomCommonAttackAction(upgraded), cardType);
        this.name = NAME;
        this.type = POWER_TYPE;
        this.updateDescription();
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }
}
