package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.RelayedDmgSum;
import theUnchainedMod.vfx.SharePainEffect;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class SharePain extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(SharePain.class.getSimpleName());
    public static final String IMG = makeCardPath("SharePain.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;


    private static final int COST = 2;
    private static final int DAMAGE = 0;
    private static final int SECOND_MAGIC_NUMBER = 2;

    public SharePain() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.selfRetain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseMagicNumber = RelayedDmgSum.relayedDamageSum.get(AbstractDungeon.actionManager);
        this.baseDamage += this.baseMagicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseMagicNumber = RelayedDmgSum.relayedDamageSum.get(AbstractDungeon.actionManager);
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.baseMagicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.damage += this.magicNumber;
        this.calculateCardDamage(m);
        for (int i = 0; i < Math.min(this.damage, 200.0F); i += 5) {
            int randomGlyphAmount = (int)(Math.random() * 7) + 1;
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SharePainEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY, randomGlyphAmount)));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE, true));
    }
}