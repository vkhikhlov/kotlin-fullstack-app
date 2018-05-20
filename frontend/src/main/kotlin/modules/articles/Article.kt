package modules.articles

import modules.base.BaseModule
import modules.base.BaseModuleComponent
import modules.base.BaseModuleProps
import vue.*

interface ArticleComponent : BaseModuleComponent<VData, BaseModuleProps, VRefs>

object ArticleOptions : BaseModule<VData, BaseModuleProps, VRefs, VComputed, ArticleComponent>(ArticleComponent::class)