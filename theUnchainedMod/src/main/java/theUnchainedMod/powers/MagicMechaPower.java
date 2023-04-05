package theUnchainedMod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.MagicMechaAction;
import theUnchainedMod.actions.MasterChainAction;
import theUnchainedMod.relics.Carabiner;

public class MagicMechaPower extends AbstractMasterChainPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("MagicMechaPower");
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerstrings.NAME;
    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static PowerType POWER_TYPE = PowerType.BUFF;
    private final int buffAmount;

    public MagicMechaPower(AbstractCreature owner, int amount, int buff, AbstractCard.CardType cardType) {
        super(POWER_ID, owner, amount, new MagicMechaAction(buff,owner), cardType);
        this.name = NAME;
        this.type = POWER_TYPE;
        buffAmount = buff;
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] +
                DESCRIPTIONS[1] + attacksRequired + DESCRIPTIONS[2] +
                DESCRIPTIONS[1] + skillsRequired + DESCRIPTIONS[3] +
                DESCRIPTIONS[1] + powersRequired + DESCRIPTIONS[4] +

                DESCRIPTIONS[5] + buffAmount + DESCRIPTIONS[6];
    }
}
