package theUnchainedMod.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import theUnchainedMod.actions.ApplyRelayedDamageAction;
import theUnchainedMod.powers.FractalShieldPower;
import theUnchainedMod.vfx.HBRelayBrokenEffect;


@SpirePatch(
        clz = AbstractCreature.class,
        method = SpirePatch.CLASS
)
public class RelayHelpers {
    public static SpireField<Integer> currentRelay = new SpireField<>(() -> 0);
    public static SpireField<Integer> nextTurnRelayedDamage = new SpireField<>(() -> 0);
    public static SpireField<Integer> thisTurnRelayedDamage = new SpireField<>(() -> 0);

    public static SpireField<Color> relayColor = new SpireField<>(() -> new Color(1.0F, 1.0F, 1.0F, 0.0F));
    public static SpireField<Color> relayTextColor = new SpireField<>(() -> new Color(0.9F, 0.9F, 0.9F, 0.0F));
    public static SpireField<Color> relayOutlineColor = new SpireField<>(() -> new Color(0.6F, 0.93F, 0.98F, 0.0F));
    public static SpireField<Float> relayAnimTimer = new SpireField<>(() -> 0.0F);
    public static SpireField<Float> relayScale = new SpireField<>(() -> 1.0F);
    public static SpireField<Float> relayRotation = new SpireField<>(() -> 0.0F);

    public static SpireField<Color> nextTurnRelayedDamageColor = new SpireField<>(() -> new Color(1.0F, 1.0F, 1.0F, 0.0F));
    public static SpireField<Color> nextTurnRelayedActualDamageTextColor = new SpireField<>(() -> new Color(1.0F, 0.0F, 0.0F, 1.0F));
    public static SpireField<Color> nextTurnRelayedDamageTextColor = new SpireField<>(() -> new Color(0.9F, 0.9F, 0.9F, 0.0F));
    public static SpireField<Color> nextTurnRelayedDamageOutlineColor = new SpireField<>(() -> new Color(0.6F, 0.93F, 0.98F, 0.0F));
    public static SpireField<Float> nextTurnRelayedDamageAnimTimer = new SpireField<>(() -> 0.0F);
    public static SpireField<Float> nextTurnRelayedDamageScale = new SpireField<>(() -> 1.0F);

    public static SpireField<Color> thisTurnRelayedDamageColor = new SpireField<>(() -> new Color(1.0F, 1.0F, 1.0F, 0.0F));
    public static SpireField<Color> thisTurnRelayedActualDamageTextColor = new SpireField<>(() -> new Color(1.0F, 0.0F, 0.0F, 1.0F));
    public static SpireField<Color> thisTurnRelayedDamageTextColor = new SpireField<>(() -> new Color(0.9F, 0.9F, 0.9F, 0.0F));
    public static SpireField<Color> thisTurnRelayedDamageOutlineColor = new SpireField<>(() -> new Color(0.6F, 0.93F, 0.98F, 0.0F));
    public static SpireField<Float> thisTurnRelayedDamageAnimTimer = new SpireField<>(() -> 0.0F);
    public static SpireField<Float> thisTurnRelayedDamageScale = new SpireField<>(() -> 1.0F);

    public static final float RELAY_ICON_X = -14.0F * Settings.scale;
    public static final float RELAY_ICON_Y = +32.0F * Settings.scale;
    public static final float NEXT_TURN_RELAYED_DAMAGE_ICON_Y = 160.0F * Settings.scale;
    public static final float THIS_TURN_RELAYED_DAMAGE_ICON_Y = 96.0F * Settings.scale;

    public static void addRelay(int relayAmount, AbstractCreature creature) {
        float tmp = (float) relayAmount;

        boolean effect = RelayHelpers.currentRelay.get(creature) == 0;

        RelayHelpers.currentRelay.set(creature, RelayHelpers.currentRelay.get(creature) + MathUtils.floor(tmp));

        if (RelayHelpers.currentRelay.get(creature) > 999) {
            RelayHelpers.currentRelay.set(creature, 999);
        }
        if (effect && RelayHelpers.currentRelay.get(creature) > 0) {
            RelayHelpers.gainRelayAnimation(creature);
        } else if (relayAmount > 0) {
            Color tmpCol = Settings.GOLD_COLOR.cpy();
            tmpCol.a = RelayHelpers.relayTextColor.get(creature).a;
            RelayHelpers.relayTextColor.set(creature, tmpCol);
            RelayHelpers.relayScale.set(creature, 5.0F);
        }
    }

    public static void addNextTurnRelayedDamage(int relayedDamageAmount, AbstractCreature creature) {
        float tmp = (float) relayedDamageAmount;

        boolean effect = RelayHelpers.nextTurnRelayedDamage.get(creature) == 0;

        RelayHelpers.nextTurnRelayedDamage.set(creature, RelayHelpers.nextTurnRelayedDamage.get(creature) + MathUtils.floor(tmp));

        if (RelayHelpers.nextTurnRelayedDamage.get(creature) > 999) {
            RelayHelpers.nextTurnRelayedDamage.set(creature, 999);
        }

        if (effect && RelayHelpers.nextTurnRelayedDamage.get(creature) > 0) {
            RelayHelpers.gainNextTurnRelayedDamageAnimation(creature);
        } else if (relayedDamageAmount > 0) {
            Color tmpCol = Color.SCARLET.cpy();
            tmpCol.a = RelayHelpers.nextTurnRelayedDamageTextColor.get(creature).a;
            RelayHelpers.nextTurnRelayedDamageTextColor.set(creature, tmpCol);
            RelayHelpers.nextTurnRelayedDamageScale.set(creature, 5.0F);
        }
    }

    public static void addThisTurnRelayedDamage(int relayedDamageAmount, AbstractCreature creature) {
        float tmp = (float) relayedDamageAmount;

        boolean effect = RelayHelpers.thisTurnRelayedDamage.get(creature) == 0;

        RelayHelpers.thisTurnRelayedDamage.set(creature, RelayHelpers.thisTurnRelayedDamage.get(creature) + MathUtils.floor(tmp));

        if (RelayHelpers.thisTurnRelayedDamage.get(creature) > 999) {
            RelayHelpers.thisTurnRelayedDamage.set(creature, 999);
        }

        if (effect && RelayHelpers.thisTurnRelayedDamage.get(creature) > 0) {
            RelayHelpers.gainThisTurnRelayedDamageAnimation(creature);
        } else if (relayedDamageAmount > 0) {
            Color tmpCol = Color.SCARLET.cpy();
            tmpCol.a = RelayHelpers.thisTurnRelayedDamageTextColor.get(creature).a;
            RelayHelpers.thisTurnRelayedDamageTextColor.set(creature, tmpCol);
            RelayHelpers.thisTurnRelayedDamageScale.set(creature, 5.0F);
        }
    }

    public static void loseRelay(boolean noAnimation, AbstractCreature creature) {
        loseRelay(RelayHelpers.currentRelay.get(creature), noAnimation, creature);
    }

    public static void loseRelay(int amount, boolean noAnimation, AbstractCreature creature) {
        boolean effect = RelayHelpers.currentRelay.get(creature) != 0;

        RelayHelpers.currentRelay.set(creature, RelayHelpers.currentRelay.get(creature) - amount);
        if (RelayHelpers.currentRelay.get(creature) < 0) {
            RelayHelpers.currentRelay.set(creature, 0);
        }

        if (RelayHelpers.currentRelay.get(creature) == 0 && effect) {
            if (!noAnimation) {
                CardCrawlGame.sound.play("relayBreak");
                AbstractDungeon.effectList.add(new HBRelayBrokenEffect(creature.hb.cX - creature.hb.width / 2.0F + RELAY_ICON_X, creature.hb.cY - creature.hb.height / 2.0F + RELAY_ICON_Y));
            }
        } else if (RelayHelpers.currentRelay.get(creature) > 0 && amount > 0) {
            Color tmp = Color.SCARLET.cpy();
            tmp.a = RelayHelpers.relayTextColor.get(creature).a;
            RelayHelpers.relayTextColor.set(creature, tmp);
            RelayHelpers.relayScale.set(creature, 5.0F);
        }
    }

    public static void loseNextTurnRelayedDamage(boolean noAnimation, AbstractCreature creature) {
        loseNextTurnRelayedDamage(RelayHelpers.nextTurnRelayedDamage.get(creature), noAnimation, creature);
    }

    public static void loseNextTurnRelayedDamage(int amount, boolean noAnimation, AbstractCreature creature) {
        boolean effect = RelayHelpers.nextTurnRelayedDamage.get(creature) != 0;

        RelayHelpers.nextTurnRelayedDamage.set(creature, RelayHelpers.nextTurnRelayedDamage.get(creature) - amount);
        if (RelayHelpers.nextTurnRelayedDamage.get(creature) < 0) {
            RelayHelpers.nextTurnRelayedDamage.set(creature, 0);
        }

        if (RelayHelpers.nextTurnRelayedDamage.get(creature) == 0 && effect) {
            if (!noAnimation) {
                //CardCrawlGame.sound.play("relayBreak");
                //AbstractDungeon.effectList.add(new HbBlockBrokenEffect(creature.hb.cX - creature.hb.width / 2.0F + RELAY_ICON_X, creature.hb.cY - creature.hb.height / 2.0F + RELAY_ICON_Y));
            }
        } else if (RelayHelpers.nextTurnRelayedDamage.get(creature) > 0 && amount > 0) {
            Color tmp = Settings.GOLD_COLOR.cpy();
            tmp.a = RelayHelpers.nextTurnRelayedDamageTextColor.get(creature).a;
            RelayHelpers.nextTurnRelayedDamageTextColor.set(creature, tmp);
            RelayHelpers.nextTurnRelayedDamageScale.set(creature, 5.0F);
        }
    }

    public static void loseThisTurnRelayedDamage(boolean noAnimation, AbstractCreature creature) {
        loseThisTurnRelayedDamage(RelayHelpers.thisTurnRelayedDamage.get(creature), noAnimation, creature);
    }

    public static void loseThisTurnRelayedDamage(int amount, boolean noAnimation, AbstractCreature creature) {
        boolean effect = RelayHelpers.thisTurnRelayedDamage.get(creature) != 0;

        RelayHelpers.thisTurnRelayedDamage.set(creature, RelayHelpers.thisTurnRelayedDamage.get(creature) - amount);
        if (RelayHelpers.thisTurnRelayedDamage.get(creature) < 0) {
            RelayHelpers.thisTurnRelayedDamage.set(creature, 0);
        }

        if (RelayHelpers.thisTurnRelayedDamage.get(creature) == 0 && effect) {
            if (!noAnimation) {
                //CardCrawlGame.sound.play("relayBreak");
                //AbstractDungeon.effectList.add(new HbBlockBrokenEffect(creature.hb.cX - creature.hb.width / 2.0F + RELAY_ICON_X, creature.hb.cY - creature.hb.height / 2.0F + RELAY_ICON_Y));
            }
        } else if (RelayHelpers.thisTurnRelayedDamage.get(creature) > 0 && amount > 0) {
            Color tmp = Settings.GOLD_COLOR.cpy();
            tmp.a = RelayHelpers.thisTurnRelayedDamageTextColor.get(creature).a;
            RelayHelpers.thisTurnRelayedDamageTextColor.set(creature, tmp);
            RelayHelpers.thisTurnRelayedDamageScale.set(creature, 5.0F);
        }
    }

    private static void gainRelayAnimation(AbstractCreature creature) {
        RelayHelpers.relayAnimTimer.set(creature, 0.7F);
        RelayHelpers.relayTextColor.get(creature).a = 0.0F;
        RelayHelpers.relayColor.get(creature).a = 0.0F;
    }

    public static void updateRelayAnimations(AbstractCreature creature) {
        if (RelayHelpers.currentRelay.get(creature) > 0) {
            if (RelayHelpers.relayAnimTimer.get(creature) > 0.0F) {
                RelayHelpers.relayAnimTimer.set(creature, RelayHelpers.relayAnimTimer.get(creature) - Gdx.graphics.getDeltaTime());
                if (RelayHelpers.relayAnimTimer.get(creature) < 0.0F) {
                    RelayHelpers.relayAnimTimer.set(creature, 0.0F);
                }

                RelayHelpers.relayRotation.set(creature, Interpolation.pow3Out.apply(360.0F, 0.0F, 1.0F - RelayHelpers.relayAnimTimer.get(creature) / 0.7F));
                RelayHelpers.relayScale.set(creature, Interpolation.pow3In.apply(3.0F, 1.0F, 1.0F - RelayHelpers.relayAnimTimer.get(creature) / 0.7F));
                RelayHelpers.relayColor.get(creature).a = Interpolation.pow2Out.apply(0.0F, 1.0F, 1.0F - RelayHelpers.relayAnimTimer.get(creature) / 0.7F);
                RelayHelpers.relayTextColor.get(creature).a = Interpolation.pow5In.apply(0.0F, 1.0F, 1.0F - RelayHelpers.relayAnimTimer.get(creature) / 0.7F);
            } else if (RelayHelpers.relayScale.get(creature) != 1.0F) {
                RelayHelpers.relayScale.set(creature, MathHelper.scaleLerpSnap(RelayHelpers.relayScale.get(creature), 1.0F));
            }

            if (RelayHelpers.relayTextColor.get(creature).r != 1.0F) {
                RelayHelpers.relayTextColor.get(creature).r = MathHelper.slowColorLerpSnap(RelayHelpers.relayTextColor.get(creature).r, 1.0F);
            }

            if (RelayHelpers.relayTextColor.get(creature).g != 1.0F) {
                RelayHelpers.relayTextColor.get(creature).g = MathHelper.slowColorLerpSnap(RelayHelpers.relayTextColor.get(creature).g, 1.0F);
            }

            if (RelayHelpers.relayTextColor.get(creature).b != 1.0F) {
                RelayHelpers.relayTextColor.get(creature).b = MathHelper.slowColorLerpSnap(RelayHelpers.relayTextColor.get(creature).b, 1.0F);
            }
        }

    }

    private static void gainNextTurnRelayedDamageAnimation(AbstractCreature creature) {
        RelayHelpers.nextTurnRelayedDamageAnimTimer.set(creature, 0.7F);
        RelayHelpers.nextTurnRelayedDamageTextColor.get(creature).a = 0.0F;
        RelayHelpers.nextTurnRelayedActualDamageTextColor.get(creature).a = 0.0F;
        RelayHelpers.nextTurnRelayedDamageColor.get(creature).a = 0.0F;
    }

    public static void updateNextTurnRelayedDamageAnimations(AbstractCreature creature) {
        if (RelayHelpers.nextTurnRelayedDamage.get(creature) > 0) {
            if (RelayHelpers.nextTurnRelayedDamageAnimTimer.get(creature) > 0.0F) {
                RelayHelpers.nextTurnRelayedDamageAnimTimer.set(creature, RelayHelpers.nextTurnRelayedDamageAnimTimer.get(creature) - Gdx.graphics.getDeltaTime());
                if (RelayHelpers.nextTurnRelayedDamageAnimTimer.get(creature) < 0.0F) {
                    RelayHelpers.nextTurnRelayedDamageAnimTimer.set(creature, 0.0F);
                }

                //this.blockOffset = Interpolation.swingOut.apply(BLOCK_OFFSET_DIST * 3.0F, 0.0F, 1.0F - this.blockAnimTimer / 0.7F);
                RelayHelpers.nextTurnRelayedDamageScale.set(creature, Interpolation.pow3In.apply(3.0F, 1.0F, 1.0F - RelayHelpers.nextTurnRelayedDamageAnimTimer.get(creature) / 0.7F));
                RelayHelpers.nextTurnRelayedDamageColor.get(creature).a = Interpolation.pow2Out.apply(0.0F, 1.0F, 1.0F - RelayHelpers.nextTurnRelayedDamageAnimTimer.get(creature) / 0.7F);
                RelayHelpers.nextTurnRelayedDamageTextColor.get(creature).a = Interpolation.pow5In.apply(0.0F, 1.0F, 1.0F - RelayHelpers.nextTurnRelayedDamageAnimTimer.get(creature) / 0.7F);
                RelayHelpers.nextTurnRelayedActualDamageTextColor.get(creature).a = Interpolation.pow5In.apply(0.0F, 1.0F, 1.0F - RelayHelpers.nextTurnRelayedDamageAnimTimer.get(creature) / 0.7F);
            } else if (RelayHelpers.nextTurnRelayedDamageScale.get(creature) != 1.0F) {
                RelayHelpers.nextTurnRelayedDamageScale.set(creature, MathHelper.scaleLerpSnap(RelayHelpers.nextTurnRelayedDamageScale.get(creature), 1.0F));
            }

            if (RelayHelpers.nextTurnRelayedDamageTextColor.get(creature).r != 1.0F) {
                RelayHelpers.nextTurnRelayedDamageTextColor.get(creature).r = MathHelper.slowColorLerpSnap(RelayHelpers.nextTurnRelayedDamageTextColor.get(creature).r, 1.0F);
            }

            if (RelayHelpers.nextTurnRelayedDamageTextColor.get(creature).g != 1.0F) {
                RelayHelpers.nextTurnRelayedDamageTextColor.get(creature).g = MathHelper.slowColorLerpSnap(RelayHelpers.nextTurnRelayedDamageTextColor.get(creature).g, 1.0F);
            }

            if (RelayHelpers.nextTurnRelayedDamageTextColor.get(creature).b != 1.0F) {
                RelayHelpers.nextTurnRelayedDamageTextColor.get(creature).b = MathHelper.slowColorLerpSnap(RelayHelpers.nextTurnRelayedDamageTextColor.get(creature).b, 1.0F);
            }


            if (RelayHelpers.nextTurnRelayedActualDamageTextColor.get(creature).r != Color.SCARLET.r) {
                RelayHelpers.nextTurnRelayedActualDamageTextColor.get(creature).r = MathHelper.slowColorLerpSnap(RelayHelpers.nextTurnRelayedActualDamageTextColor.get(creature).r, 1.0F);
            }

            if (RelayHelpers.nextTurnRelayedActualDamageTextColor.get(creature).g != Color.SCARLET.g) {
                RelayHelpers.nextTurnRelayedActualDamageTextColor.get(creature).g = MathHelper.slowColorLerpSnap(RelayHelpers.nextTurnRelayedActualDamageTextColor.get(creature).g, 0.0F);
            }

            if (RelayHelpers.nextTurnRelayedActualDamageTextColor.get(creature).b != Color.SCARLET.b) {
                RelayHelpers.nextTurnRelayedActualDamageTextColor.get(creature).b = MathHelper.slowColorLerpSnap(RelayHelpers.nextTurnRelayedActualDamageTextColor.get(creature).b, 0.0F);
            }
        }

    }

    private static void gainThisTurnRelayedDamageAnimation(AbstractCreature creature) {
        RelayHelpers.thisTurnRelayedDamageAnimTimer.set(creature, 0.7F);
        RelayHelpers.thisTurnRelayedDamageTextColor.get(creature).a = 0.0F;
        RelayHelpers.thisTurnRelayedActualDamageTextColor.get(creature).a = 0.0F;
        RelayHelpers.thisTurnRelayedDamageColor.get(creature).a = 0.0F;
    }

    public static void updateThisTurnRelayedDamageAnimations(AbstractCreature creature) {
        if (RelayHelpers.thisTurnRelayedDamage.get(creature) > 0) {
            if (RelayHelpers.thisTurnRelayedDamageAnimTimer.get(creature) > 0.0F) {
                RelayHelpers.thisTurnRelayedDamageAnimTimer.set(creature, RelayHelpers.thisTurnRelayedDamageAnimTimer.get(creature) - Gdx.graphics.getDeltaTime());
                if (RelayHelpers.thisTurnRelayedDamageAnimTimer.get(creature) < 0.0F) {
                    RelayHelpers.thisTurnRelayedDamageAnimTimer.set(creature, 0.0F);
                }

                //this.blockOffset = Interpolation.swingOut.apply(BLOCK_OFFSET_DIST * 3.0F, 0.0F, 1.0F - this.blockAnimTimer / 0.7F);
                RelayHelpers.thisTurnRelayedDamageScale.set(creature, Interpolation.pow3In.apply(3.0F, 1.0F, 1.0F - RelayHelpers.thisTurnRelayedDamageAnimTimer.get(creature) / 0.7F));
                RelayHelpers.thisTurnRelayedDamageColor.get(creature).a = Interpolation.pow2Out.apply(0.0F, 1.0F, 1.0F - RelayHelpers.thisTurnRelayedDamageAnimTimer.get(creature) / 0.7F);
                RelayHelpers.thisTurnRelayedDamageTextColor.get(creature).a = Interpolation.pow5In.apply(0.0F, 1.0F, 1.0F - RelayHelpers.thisTurnRelayedDamageAnimTimer.get(creature) / 0.7F);
                RelayHelpers.thisTurnRelayedActualDamageTextColor.get(creature).a = Interpolation.pow5In.apply(0.0F, 1.0F, 1.0F - RelayHelpers.thisTurnRelayedDamageAnimTimer.get(creature) / 0.7F);
            } else if (RelayHelpers.thisTurnRelayedDamageScale.get(creature) != 1.0F) {
                RelayHelpers.thisTurnRelayedDamageScale.set(creature, MathHelper.scaleLerpSnap(RelayHelpers.thisTurnRelayedDamageScale.get(creature), 1.0F));
            }

            if (RelayHelpers.thisTurnRelayedDamageTextColor.get(creature).r != 1.0F) {
                RelayHelpers.thisTurnRelayedDamageTextColor.get(creature).r = MathHelper.slowColorLerpSnap(RelayHelpers.thisTurnRelayedDamageTextColor.get(creature).r, 1.0F);
            }

            if (RelayHelpers.thisTurnRelayedDamageTextColor.get(creature).g != 1.0F) {
                RelayHelpers.thisTurnRelayedDamageTextColor.get(creature).g = MathHelper.slowColorLerpSnap(RelayHelpers.thisTurnRelayedDamageTextColor.get(creature).g, 1.0F);
            }

            if (RelayHelpers.thisTurnRelayedDamageTextColor.get(creature).b != 1.0F) {
                RelayHelpers.thisTurnRelayedDamageTextColor.get(creature).b = MathHelper.slowColorLerpSnap(RelayHelpers.thisTurnRelayedDamageTextColor.get(creature).b, 1.0F);
            }


            if (RelayHelpers.thisTurnRelayedActualDamageTextColor.get(creature).r != Color.SCARLET.r) {
                RelayHelpers.thisTurnRelayedActualDamageTextColor.get(creature).r = MathHelper.slowColorLerpSnap(RelayHelpers.thisTurnRelayedActualDamageTextColor.get(creature).r, 1.0F);
            }

            if (RelayHelpers.thisTurnRelayedActualDamageTextColor.get(creature).g != Color.SCARLET.g) {
                RelayHelpers.thisTurnRelayedActualDamageTextColor.get(creature).g = MathHelper.slowColorLerpSnap(RelayHelpers.thisTurnRelayedActualDamageTextColor.get(creature).g, 0.0F);
            }

            if (RelayHelpers.thisTurnRelayedActualDamageTextColor.get(creature).b != Color.SCARLET.b) {
                RelayHelpers.thisTurnRelayedActualDamageTextColor.get(creature).b = MathHelper.slowColorLerpSnap(RelayHelpers.thisTurnRelayedActualDamageTextColor.get(creature).b, 0.0F);
            }
        }

    }

    public static void loseAllRelayedDamage(AbstractCreature creature) {
        if (creature != null && !creature.isDeadOrEscaped() && creature.hasPower(FractalShieldPower.POWER_ID) && (RelayHelpers.thisTurnRelayedDamage.get(creature) > 0 || RelayHelpers.nextTurnRelayedDamage.get(creature) > 0)) {
            creature.getPower(FractalShieldPower.POWER_ID).onSpecificTrigger();
        }

        loseNextTurnRelayedDamage(false, creature);
        loseThisTurnRelayedDamage(false, creature);
    }


    public static int relayDamageWhenAttacked(DamageInfo info, int damageAmount, AbstractCreature creature) {
        if (damageAmount > 0 && (!RelayedDamageField.relayed.get(info) || creature.hasPower(FractalShieldPower.POWER_ID))) {
            int relayedDamage;
            if (RelayHelpers.currentRelay.get(creature) >= damageAmount) {
                loseRelay(damageAmount, false, creature);
                relayedDamage = damageAmount;
                damageAmount = 0;
            } else {
                damageAmount -= RelayHelpers.currentRelay.get(creature);
                relayedDamage = RelayHelpers.currentRelay.get(creature);
                loseRelay(false, creature);
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyRelayedDamageAction(creature, relayedDamage));
        }
        //if (RelayHelpers.currentRelay.get(creature) == 0) {
        //    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        //}
        return damageAmount;
    }
}
