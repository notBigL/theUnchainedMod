package theUnchainedMod.booster_pack_cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.LoseRelayAction;
import theUnchainedMod.cards.AbstractDynamicCard;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.RelayHelpers;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class ArcaneArtillery extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(ArcaneArtillery.class.getSimpleName());
    public static final String IMG = makeCardPath("ArcaneArtillery.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_BOOSTER;

    private static final int COST = 2;
    private static final int DAMAGE = 0;

    public ArcaneArtillery() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
    public void applyPowers() {
        this.baseDamage = RelayHelpers.currentRelay.get(AbstractDungeon.player);
        super.applyPowers();
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseDamage = RelayHelpers.currentRelay.get(p);
        calculateCardDamage(m);
        AbstractDungeon.actionManager.addToBottom(new LoseRelayAction(p));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
}
