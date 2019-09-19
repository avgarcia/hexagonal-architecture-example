package com.codecriticon.hexagonal.domain

import java.time.LocalDateTime

class ActivityWindow(activities: List<Activity>) {
    private val activities: List<Activity> = activities
        get() = field.toList()

    fun getStartTimestamp(): LocalDateTime = activities.minBy { activity ->
        activity.timestamp
    }!!.timestamp

    fun getEndTimestamp(): LocalDateTime = activities.maxBy { activity ->
        activity.timestamp
    }!!.timestamp

    fun calculateBalance(accountId: Account.AccountId): Money {
        val depositBalance = activities.filter { activity ->
            activity.targetAccountId == accountId
        }.map { activity ->
            activity.money
        }.reduce { sum: Money, money ->
            sum + money
        }

        val withdrawalBalance = activities.filter { activity ->
            activity.sourceAccountId == accountId
        }.map { activity ->
            activity.money
        }.reduce { sum, money ->
            sum + money
        }

        return depositBalance + !withdrawalBalance
    }

    fun addActivity(activity: Activity) = this.activities + activity
}
