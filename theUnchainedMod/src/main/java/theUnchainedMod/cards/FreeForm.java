package theUnchainedMod.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.powers.FreeFormPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.DefaultMod.makeCardPath;

@AutoAdd.Ignore
public class FreeForm extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(FreeForm.class.getSimpleName());
    public static final String IMG = makeCardPath("FreeForm.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_ORANGE;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;

    private static final int COST = 3;

    public FreeForm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.isEthereal = true;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            this.isEthereal = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FreeFormPower(p)));
    }
}
