/*
 * Copyright (C) 2019. Jay Chang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.jaychang.navdemo.root

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jaychang.navdemo.R
import com.jaychang.navdemo.root.delivery_order.DeliveryOrderBuilder
import com.jaychang.navigation.HorizontalTransition
import com.jaychang.navigation.ScreenStack
import com.jaychang.navigation.VerticalTransition
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.BindsInstance
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.BINARY

class RootBuilder(
    dependency: ParentComponent
) : ViewBuilder<RootView, RootRouter, RootBuilder.ParentComponent>(dependency) {

    fun build(parentViewGroup: ViewGroup): RootRouter {
        val view = createView(parentViewGroup)
        val interactor = RootInteractor()
        val component = DaggerRootBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.rootRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): RootView? {
        return inflater.inflate(R.layout.rib_root, parentViewGroup, false) as RootView
    }

    interface ParentComponent

    @dagger.Module
    abstract class Module {

        @dagger.Module
        companion object {

            @RootScope
            @Provides
            @JvmStatic
            fun presenter(view: RootView): RootInteractor.Presenter = view

            @RootScope
            @Provides
            @JvmStatic
            fun screenStack(view: RootView) = ScreenStack(
                parentViewGroup = view,
                defaultPushTransitionProvider = { HorizontalTransition() },
                defaultPresentTransitionProvider = { VerticalTransition() }
            )

            @RootScope
            @Provides
            @JvmStatic
            fun router(
                rootView: RootView,
                component: Component,
                interactor: RootInteractor,
                screenStack: ScreenStack
            ): RootRouter {
                val deliveryOrderBuilder = DeliveryOrderBuilder(component)
                return RootRouter(
                    rootView,
                    interactor,
                    component,
                    deliveryOrderBuilder,
                    screenStack
                )
            }
        }
    }

    @RootScope
    @dagger.Component(modules = [Module::class], dependencies = [ParentComponent::class])
    interface Component : InteractorBaseComponent<RootInteractor>,
        DeliveryOrderBuilder.ParentComponent,
        BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: RootInteractor): Builder

            @BindsInstance
            fun view(view: RootView): Builder

            fun parentComponent(component: ParentComponent): Builder

            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun rootRouter(): RootRouter
    }
}

@Scope
@kotlin.annotation.Retention(BINARY)
annotation class RootScope

@Qualifier
@kotlin.annotation.Retention(BINARY)
annotation class RootInternal
