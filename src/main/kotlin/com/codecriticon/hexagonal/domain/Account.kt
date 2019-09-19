package com.codecriticon.hexagonal.domain

import java.time.LocalDateTime

class Account(
    private val id: AccountId = AccountId(-1L),
    private val baselineBalance: Money,
    private val activityWindow: ActivityWindow
) {
    class AccountId(val value: Long)

    fun withoutId(baselineBalance: Money, activityWindow: ActivityWindow): Account =
        Account(baselineBalance = baselineBalance, activityWindow = activityWindow)

    fun withId(accountId: AccountId, baselineBalance: Money, activityWindow: ActivityWindow): Account =
        Account(accountId, baselineBalance, activityWindow)

    private fun calculateBalance(): Money =
        this.baselineBalance + this.activityWindow.calculateBalance(this.id)

    fun withdraw(money: Money, targetAccountId: AccountId): Boolean = when {
        !mayWithdraw(money) -> false
        else -> {
            val withdrawal = Activity(
                ownerAccountId = this.id,
                sourceAccountId = this.id,
                targetAccountId = targetAccountId,
                timestamp = LocalDateTime.now(),
                money = money
            )
            this.activityWindow.addActivity(withdrawal)
            true
        }
    }

    private fun mayWithdraw(money: Money): Boolean = (this.calculateBalance() + !money).isPositiveOrZero()

    fun deposit(money: Money, sourceAccountId: AccountId) {
        val deposit = Activity(
            ownerAccountId = this.id,
            sourceAccountId = sourceAccountId,
            targetAccountId = this.id,
            timestamp = LocalDateTime.now(),
            money = money
        )
        this.activityWindow.addActivity(deposit)
    }
}
