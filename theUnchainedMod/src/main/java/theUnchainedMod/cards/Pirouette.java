package theUnchainedMod.cards;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.PirouetteAction;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.powers.MomentumPower;
import theUnchainedMod.powers.NewTwoAmountPower;
import theUnchainedMod.relics.BalletShoes;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class Pirouette extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(Pirouette.class.getSimpleName());
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
        if (p.hasPower(MomentumPower.POWER_ID)) {
            NewTwoAmountPower momentumPower = (NewTwoAmountPower) p.getPower(MomentumPower.POWER_ID);
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new MomentumPower(p, momentumPower.amount2)));
        } else {
            if (p.hasRelic(BalletShoes.ID))
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new MomentumPower(p, 1)));
            else AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new MomentumPower(p, 2)));
        }
        AbstractDungeon.actionManager.addToBottom(new PirouetteAction());
    }
}
