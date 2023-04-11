package theUnchainedMod.booster_pack_cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.DamagePerChainFinishedAction;
import theUnchainedMod.cards.AbstractDynamicCard;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.powers.ChainsFinishedThisTurnPower;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class ChainFlourish extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(ChainFlourish.class.getSimpleName());
    public static final String IMG = makeCardPath("ChainFlourish.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_BOOSTER;

    private static final int COST = 0;
    private static final int DAMAGE = 5;
    private static final int MAGIC_NUMBER = 5;
    private static final int UPGRADE_PLUS_DMG = 3;
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
        int count = 0;
        if (AbstractDungeon.player.hasPower(ChainsFinishedThisTurnPower.POWER_ID)) {
            count = AbstractDungeon.player.getPower(ChainsFinishedThisTurnPower.POWER_ID).amount;
        }
        if (count == 1) {
            this.rawDescription = STRINGS.DESCRIPTION + STRINGS.EXTENDED_DESCRIPTION[0];
        } else {
            this.rawDescription = STRINGS.DESCRIPTION + STRINGS.EXTENDED_DESCRIPTION[1];
        }
        magicNumber = count;
        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = STRINGS.DESCRIPTION;
        this.initializeDescription();
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamagePerChainFinishedAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }
}
