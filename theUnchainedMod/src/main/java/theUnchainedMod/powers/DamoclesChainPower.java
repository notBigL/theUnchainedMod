package theUnchainedMod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.DamoclesFinishedChainAction;

public class DamoclesChainPower extends AbstractMasterChainPower {
    public AbstractCreature source;

    public static final String POWER_ID = TheUnchainedMod.makeID("DamoclesChainPower");
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerstrings.NAME;
    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static PowerType POWER_TYPE = PowerType.BUFF;

    private final int damageTakenAmount;
    private final int strengthLossAmount;

    public DamoclesChainPower(AbstractCreature owner, int chainAmount, int dmgTakenAmount, int loseStrengthAmount, AbstractCard.CardType cardType) {
        super(POWER_ID, owner, chainAmount, new DamoclesFinishedChainAction(dmgTakenAmount, loseStrengthAmount), cardType);
        this.name = NAME;
        this.amount = chainAmount;
        this.type = POWER_TYPE;
        this.damageTakenAmount = dmgTakenAmount;
        this.strengthLossAmount = loseStrengthAmount;
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] +
            DESCRIPTIONS[1] + attacksRequired + DESCRIPTIONS[2] +
            DESCRIPTIONS[1] + skillsRequired + DESCRIPTIONS[3] +
            DESCRIPTIONS[1] + powersRequired + DESCRIPTIONS[4] +

            DESCRIPTIONS[5] + damageTakenAmount + DESCRIPTIONS[6] + strengthLossAmount + DESCRIPTIONS[7];
    }
}
