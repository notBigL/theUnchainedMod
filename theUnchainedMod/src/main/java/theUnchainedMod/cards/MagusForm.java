package theUnchainedMod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.powers.MagusFormPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class MagusForm extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(MagusForm.class.getSimpleName());
    public static final String IMG = makeCardPath("MagusForm.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_ORANGE;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;

    private static final int COST = 3;
    private static final int MAGIC_NUMBER = 1;

    public MagusForm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        this.isEthereal = true;
        tags.add(BaseModCardTags.FORM);
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            isEthereal = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MagusFormPower(p, magicNumber)));
    }
}
