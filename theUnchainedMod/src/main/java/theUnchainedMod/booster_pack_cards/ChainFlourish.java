package theUnchainedMod.booster_pack_cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.DamagePerChainFinishedAction;
import theUnchainedMod.cards.AbstractDynamicBoosterPackCard;
import theUnchainedMod.cards.AbstractDynamicCard;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.ChainsFinishedThisCombat;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class ChainFlourish extends AbstractDynamicBoosterPackCard {

    public static final String ID = TheUnchainedMod.makeID(ChainFlourish.class.getSimpleName());
    public static final String IMG = makeCardPath("ChainFlourish.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_BOOSTER;

    private static final int COST = 2;
    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int MAGIC_NUMBER = 0;
    private static final CardStrings STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    public ChainFlourish() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }

    public void applyPowers() {
        super.applyPowers();
        int count = ChainsFinishedThisCombat.chainsFinishedThisCombat.get(AbstractDungeon.actionManager);
        if (count == 1) {
            this.rawDescription = STRINGS.DESCRIPTION + STRINGS.EXTENDED_DESCRIPTION[0];
        } else {
            this.rawDescription = STRINGS.DESCRIPTION + STRINGS.EXTENDED_DESCRIPTION[1];
        }
        baseMagicNumber = magicNumber = count;
        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = STRINGS.DESCRIPTION;
        this.initializeDescription();
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamagePerChainFinishedAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), magicNumber, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }
}
