package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.powers.ArcaneCharmPower;
import theUnchainedMod.vfx.ArcaneCharmEffect;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class ArcaneCharm extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(ArcaneCharm.class.getSimpleName());
    public static final String IMG = makeCardPath("ArcaneCharm.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;

    private static final int COST = 2;
    private static final int MAGIC_NUMBER = 7;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 3;

    public ArcaneCharm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ArcaneCharmEffect(p.hb.cX, p.hb.cY)));
        CardCrawlGame.sound.play("arcaneCharmApplication", 0.2F);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArcaneCharmPower(p, p, this.magicNumber), this.magicNumber, AbstractGameAction.AttackEffect.NONE));
    }
}
