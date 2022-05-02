package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.powers.LongerChainsPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.DefaultMod.makeCardPath;

public class LongerChains extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(LongerChains.class.getSimpleName());
    public static final String IMG = makeCardPath("LongerChains.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;

    private static final int COST = 2;
    private static final int MAGIC_NUMBER = 2;
    private static final int SECOND_MAGIC_NUMBER = 8;
    private static final int UPGRADE_PLUS_SECOND_MAGIC_NUMBER = -2;

    public LongerChains() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawPower(p, this.magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LongerChainsPower(p, p, defaultSecondMagicNumber)));
    }
}
