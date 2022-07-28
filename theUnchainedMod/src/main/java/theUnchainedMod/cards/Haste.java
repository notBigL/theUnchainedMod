package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.HasteAction;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.MomentumPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.DefaultMod.makeCardPath;

public class Haste extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(Haste.class.getSimpleName());
    public static final String IMG = makeCardPath("Haste.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;


    private static final int COST = 0;
    private static final int MAGIC_NUMBER = 1;
    private static final int SECOND_MAGIC_NUMBER = 1;


    public Haste() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
        tags.add(CustomTags.MOMENTUM);
        this.cardsToPreview = new Swirl();
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(magicNumber, new HasteAction(this.upgraded)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MomentumPower(p, defaultSecondMagicNumber)));
    }
}
