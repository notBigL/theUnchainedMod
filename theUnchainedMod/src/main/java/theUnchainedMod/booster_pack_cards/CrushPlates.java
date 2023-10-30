package theUnchainedMod.booster_pack_cards;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.CrushPlatesAction;
import theUnchainedMod.cards.AbstractDynamicBoosterPackCard;
import theUnchainedMod.characters.TheUnchained;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class CrushPlates extends AbstractDynamicBoosterPackCard {

    public static final String ID = TheUnchainedMod.makeID(CrushPlates.class.getSimpleName());
    public static final String IMG = makeCardPath("CrushPlates.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 4;

    private static final CardStrings STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    public CrushPlates() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
    @Override
    public void applyPowers() { //your own powers
        super.applyPowers();
        this.initializeDescription();
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new CrushPlatesAction(new DamageInfo(p, damage, damageTypeForTurn), m));
    }
}
