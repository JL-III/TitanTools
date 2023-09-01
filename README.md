# **TitanEnchants**

## Titan enchants is a custom enchantment plugin for Theatria.

Players can toggle their enchantments on and off by holding shift and right-clicking while holding an imbued or charged titan tool.

Players can charge their tools with power crystals by taking a PowerCrystal and placing them on top of their titan tool inside their inventory UI.

### Custom enchantments include:

 - Titan Pick Fortune:
   - 3x3x3 block break
   - Auto Smelt

 - Titan Pick Silk:
   - 3x3x3 block break
   - Auto Collect

 - Titan Shovel:
   - Delete 5x5x1 blocks

 - Titan Rod:
   - No trash drops?
   - 2x tropical 2x puffer
 
 - Titan Sword:
   - Exp boost?
 
 - Titan Axe:
   - Tree feller
   - Auto-Replant

 - Titan Hoe:
   - Auto-replant

 ### Special Notes:  
 Shovel can delete bedrock above Y -63
 
Currently, all tools can be in the following states:

All tools have Red, Yellow, Blue variants.

Imbued Ω I, Ω II, Ω III
Charged ♆ I, ♆ II, ♆ III

Imbued dormant Ω
Charged dormant ♆
Inactive ♆

this is 9 different states

The goal is to limit this to a binary set ON/OFF states - leveraging the inactive state

Inactive ♆
Charged dormant ♆

Imbued Ω
Imbued dormant Ω

All tools start off as Inactive ♆ which is a little weird, it almost seems as if they are actually on at the time but they have no charge. 
Maybe we could indicate active in a different way like `[ON]` and `[OFF]`
Ancient Power ♆ `[ON]`

Alternatively, we could remove the Ancient Power line altogether since it is non-descriptive and vague.
For this we would need to remove the Ancient Power line from the new titan tools and have a process for removing it from the tools when someone charges their tools or toggles it on.

We could also just come out with a new edition of titan tools that dont have anything to do with this process.
Allow players trade in old ones for the new ones that work differently - or at all. We could disable the abilities on old tools when we make the switch.
