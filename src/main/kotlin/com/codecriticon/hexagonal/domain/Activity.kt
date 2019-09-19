package com.codecriticon.hexagonal.domain

import java.time.LocalDateTime

class Activity(
    val id: ActivityId = ActivityId(-1L),
    val money: Money,
    val ownerAccountId: Account.AccountId,
    val sourceAccountId: Account.AccountId,
    val targetAccountId: Account.AccountId,
    val timestamp: LocalDateTime
) {
    class ActivityId(val value: Long)
}
