package theUnchainedMod.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.GainRelayAction;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.powers.FractalShieldBlocksAllDamagePower;
import theUnchainedMod.powers.FractalShieldPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.TheUnchainedMod.makeCardPath;

@AutoAdd.Ignore
public class FractalShield extends AbstractDynamicRelayCard {

    public static final String ID = TheUnchainedMod.makeID(FractalShield.class.getSimpleName());
    public static final String IMG = makeCardPath("FractalShield.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 5;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 2;

    public FractalShield() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FractalShieldPower(p, p, magicNumber)));
    }
}
