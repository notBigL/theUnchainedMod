package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.IncreaseCostAction;
import theUnchainedMod.characters.TheUnchained;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class RoyalGambit extends AbstractDynamicCard {
    public static final String ID = TheUnchainedMod.makeID(RoyalGambit.class.getSimpleName());
    public static final String IMG = makeCardPath("TradeOffer.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;

    private static final int COST = 0;
    private static final int MAGIC_NUMBER = 3;
    private static final int SECOND_MAGIC_NUMBER = 2;
    private static final int UPGRADE_PLUS_SECOND_MAGIC_NUMBER = -1;

    public RoyalGambit() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECOND_MAGIC_NUMBER);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
        AbstractDungeon.actionManager.addToBottom(new IncreaseCostAction(this.uuid, this.defaultSecondMagicNumber));
    }
}
