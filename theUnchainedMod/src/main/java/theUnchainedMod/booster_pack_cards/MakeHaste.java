package theUnchainedMod.booster_pack_cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.MakeHasteAction;
import theUnchainedMod.cards.AbstractDynamicCard;
import theUnchainedMod.cards.Swirl;
import theUnchainedMod.characters.TheUnchained;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class MakeHaste extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(MakeHaste.class.getSimpleName());
    public static final String IMG = makeCardPath("MakeHaste.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_BOOSTER;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 3;
    private static final int SECOND_MAGIC_NUMBER = 2;


    public MakeHaste() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber= defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
        this.cardsToPreview = new Swirl();
        //this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MakeHasteAction());
    }
}
