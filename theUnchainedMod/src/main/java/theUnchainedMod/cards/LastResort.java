package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.powers.LastResortPower;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class LastResort extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(LastResort.class.getSimpleName());
    public static final String IMG = makeCardPath("LastResort.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int MAGIC_NUMBER = 3;
    private static final int SECOND_MAGIC_NUMBER = 2;
    private static final int UPGRADE_PLUS_SECOND_MAGIC_NUMBER = 1;
    private static final int ENERGY_GAIN = 2;

    public LastResort() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECOND_MAGIC_NUMBER);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(magicNumber));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY_GAIN));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LastResortPower(p, defaultSecondMagicNumber, TYPE)));
    }
}
