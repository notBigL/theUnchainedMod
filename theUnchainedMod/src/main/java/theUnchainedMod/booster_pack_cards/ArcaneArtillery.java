package theUnchainedMod.booster_pack_cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.cards.AbstractDynamicCard;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.RelayHelpers;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class ArcaneArtillery extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(ArcaneArtillery.class.getSimpleName());
    public static final String IMG = makeCardPath("ArcaneArtillery.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_BOOSTER;

    private static final int COST = 1;
    private static final int DAMAGE = 0;
    private static final CardStrings STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    public ArcaneArtillery() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            this.isMultiDamage = true;
            target = CardTarget.ALL_ENEMY;
            this.rawDescription = STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    public void applyPowers() {
        this.baseDamage = RelayHelpers.currentRelay.get(AbstractDungeon.player);
        super.applyPowers();
        if(upgraded) this.rawDescription = STRINGS.UPGRADE_DESCRIPTION;
        else this.rawDescription = STRINGS.DESCRIPTION;
        this.rawDescription += STRINGS.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if(upgraded) this.rawDescription = STRINGS.UPGRADE_DESCRIPTION;
        else this.rawDescription = STRINGS.DESCRIPTION;
        this.rawDescription += STRINGS.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseDamage = RelayHelpers.currentRelay.get(p);
        calculateCardDamage(m);
        if(upgraded) AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        else AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
}
