package com.codecriticon.hexagonal.domain

import java.math.BigInteger

class Money(private val amount: BigInteger) {

    companion object {
        val ZERO = of(0L)
        private fun of(value: Long): Money {
            return Money(BigInteger.valueOf(value))
        }
    }

    fun isPositiveOrZero(): Boolean {
        return this.amount >= BigInteger.ZERO
    }

    fun isNegative(): Boolean {
        return this.amount < BigInteger.ZERO
    }

    fun isPositive(): Boolean {
        return this.amount > BigInteger.ZERO
    }

    fun isGreaterThanOrEqualsTo(money: Money): Boolean {
        return this.amount >= money.amount
    }

    operator fun plus(money: Money): Money {
        return Money(this.amount.add(money.amount))
    }

    operator fun minus(money: Money): Money {
        return Money(this.amount.minus(money.amount))
    }

    operator fun not(): Money {
        return Money(this.amount.negate())
    }
}
