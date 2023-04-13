package theUnchainedMod.booster_pack_cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.cards.AbstractDynamicCard;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.powers.DeliciousChurroPower;
import theUnchainedMod.powers.MagicMechaPower;
import theUnchainedMod.relics.Churros;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.DefaultMod.makeCardPath;

public class MagicMecha extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(MagicMecha.class.getSimpleName());
    public static final String IMG = makeCardPath("MagicMecha.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_BOOSTER;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;

    private static final int COST = 3;
    private static final int MAGIC_NUMBER = 1;
    private static final int SECOND_MAGIC_NUMBER = 3;
    private static final int UPGRADE_PLUS_BUFF = 2;

    public MagicMecha() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
    }
// TODO
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_BUFF);
        }
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new MagicMechaPower(p, magicNumber, defaultSecondMagicNumber, TYPE)));

        if(p.hasPower(DeliciousChurroPower.POWER_ID)) {
            p.getPower(DeliciousChurroPower.POWER_ID).onSpecificTrigger();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new MagicMechaPower(p, magicNumber, defaultSecondMagicNumber, TYPE)));
        }
    }
}
