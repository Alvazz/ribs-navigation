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

package com.jaychang.navdemo.root.payment.select_payment

import android.view.View
import android.view.ViewGroup
import com.jaychang.navdemo.root.payment.PaymentInteractor
import com.uber.rib.core.screenstack.ViewProvider

class SelectPaymentScreen(
    private val view: SelectPaymentView,
    private val listener: PaymentInteractor.Listener
) : ViewProvider() {
    override fun buildView(parentView: ViewGroup): View = view

    override fun onBackPress(): Boolean {
        listener.detachPayment()
        return true
    }
}