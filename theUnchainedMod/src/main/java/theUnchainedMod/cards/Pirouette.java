package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.PirouetteAction;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.powers.FullSpinPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.DefaultMod.makeCardPath;

public class Pirouette extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(Pirouette.class.getSimpleName());
    public static final String IMG = makeCardPath("Pirouette.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_ORANGE;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;


    private static final int COST = 1;

    public Pirouette() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        AbstractCard previewCard = new Swirl();
        previewCard.upgrade();
        this.cardsToPreview = previewCard;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Swirl card = new Swirl();
        if (p.hasPower(FullSpinPower.POWER_ID)) {
            card.fullSpinApply(p.getPower(FullSpinPower.POWER_ID).amount);
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1, false));
        AbstractDungeon.actionManager.addToBottom(new PirouetteAction());
    }
}
