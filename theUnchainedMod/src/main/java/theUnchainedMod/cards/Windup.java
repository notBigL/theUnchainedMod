package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.RandomCommonAttackChainPower;
import theUnchainedMod.powers.WhiplashPower;
import theUnchainedMod.relics.Churros;
import theUnchainedMod.vfx.WindupCrossHairEffect;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.DefaultMod.makeCardPath;

public class Windup extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(Windup.class.getSimpleName());
    public static final String IMG = makeCardPath("Windup.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_ORANGE;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;


    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 1;
    private static final int SECOND_MAGIC_NUMBER = 1;

    public Windup() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
        tags.add(CustomTags.CHAIN);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new WindupCrossHairEffect(m.hb.cX, m.hb.cY)));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RandomCommonAttackChainPower(p, defaultSecondMagicNumber, upgraded, TYPE)));
        if (p.hasRelic(Churros.ID)) {
            Churros churros = (Churros) p.getRelic(Churros.ID);
            if (!churros.isEaten()) {
                churros.eat();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RandomCommonAttackChainPower(p, defaultSecondMagicNumber, upgraded, TYPE)));
            }
        }
    }
}
