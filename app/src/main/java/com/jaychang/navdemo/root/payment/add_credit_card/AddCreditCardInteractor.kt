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

package com.jaychang.navdemo.root.payment.add_credit_card

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import javax.inject.Inject

@RibInteractor
class AddCreditCardInteractor : Interactor<AddCreditCardInteractor.Presenter, AddCreditCardRouter>() {

  @Inject
  lateinit var presenter: Presenter

  @Inject
  lateinit var listener: Listener

  override fun didBecomeActive(savedInstanceState: Bundle?) {
    super.didBecomeActive(savedInstanceState)
    presenter.backButtonClicks()
      .subscribe {
        listener.onBackButtonClicked()
      }

    presenter.confirmButtonClicks()
      .subscribe {
        listener.onConfirmButtonClicked()
      }
  }

  override fun willResignActive() {
    super.willResignActive()
    println("${javaClass.simpleName} willResignActive")
  }

  interface Presenter {
    fun backButtonClicks(): Observable<Unit>

    fun confirmButtonClicks(): Observable<Unit>
  }

  interface Listener {
    fun onBackButtonClicked()

    fun onConfirmButtonClicked()
  }
}