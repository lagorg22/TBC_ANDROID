import java.lang.Math.min
import kotlin.math.pow

interface Functions{
    fun gcd(a: Int, b: Int): Int?
    fun lcm(a: Int, b: Int): Int?
    fun containsDollarSign(s: String): Boolean
    fun evenSum(n: Int=100): Int
    fun reverseNum(num: Int): Int
    fun isPalindrome(s: String): Boolean
}

open class MathGuru(): Functions{
    override fun gcd(a: Int, b: Int): Int? {
        val factorsOfA = sieve(num = a)
        val factorsOfB = sieve(num = b)

        if(factorsOfA.isEmpty() && factorsOfB.isEmpty()){
            println("GCD of 0 and 0 is undefined")
            return null
        }else if (factorsOfA.isNotEmpty() && factorsOfB.isEmpty()){
            return a
        }else if (factorsOfA.isEmpty() && factorsOfB.isNotEmpty()){
            return b
        }
        var result = 1
        for (num in factorsOfA.keys) {
            if (num in factorsOfB.keys) {
                val freqOfNumInA = factorsOfA[num] ?: 0
                val freqOfNumInB = factorsOfB[num] ?: 0
                val minAmount = min(freqOfNumInA, freqOfNumInB).toDouble()
                result *= num.toDouble().pow(minAmount).toInt()
            }
        }
        return result

    }

    override fun lcm(a: Int, b: Int): Int? {
        val factorsOfA = sieve(num = a)
        val factorsOfB = sieve(num = b)
        if (factorsOfA.isEmpty() && factorsOfB.isEmpty()){
            println("LCM of 0 and 0 is undefined.")
            return null
        }else if(factorsOfA.isEmpty() || factorsOfB.isEmpty()){
            return 0
        }
        var result = a
        for (num in factorsOfB.keys){
            val freqOfNumInA = factorsOfA[num] ?: 0
            val freqOfNumInB = factorsOfB[num] ?: 0
            if(num in factorsOfA.keys){
                if(freqOfNumInB <= freqOfNumInA) continue

                val minAmount = (freqOfNumInB - freqOfNumInA).toDouble()
                result *= num.toDouble().pow(minAmount).toInt()
            }else{
                result *= num.toDouble().pow(freqOfNumInB.toDouble()).toInt()
            }
        }
        return result
    }

    override fun containsDollarSign(s: String): Boolean {
        return '$' in s
    }

    override fun evenSum(n: Int): Int {
        if (n % 2 == 1) return 0 + evenSum(n-1)
        if (n == 0) return 0
        return n + evenSum(n-2)
    }

    override fun reverseNum(num: Int): Int {
        var result = 0
        var n = num
        while (n > 0){
            result = result * 10 + n % 10
            n /= 10
        }
        return result
    }

    override fun isPalindrome(s: String): Boolean {
        var left = 0
        var right = s.length - 1
        while (left <= right) {
            if (s[left] != s[right]) return false
            left++
            right--
        }
        return true
    }

    private fun sieve(num: Int): MutableMap<Int, Int>{
        var n = num
        if (n < 0) n = -n

        val nums = IntArray(n + 1) { 0 }

        for (i in 2..n) {
            if (nums[i] > 0) {
                continue
            } else {
                for (j in i * i..n step i) {
                    if (nums[j] == 0) nums[j] = i
                }
            }
        }

        val factors = mutableMapOf<Int, Int>()

        while (n > 1) {
            if (nums[n] == 0) {
                factors[n] = factors.getOrDefault(n, 0) + 1
                break
            }
            factors[nums[n]] = factors.getOrDefault(nums[n], 0) + 1
            n /= nums[n]
        }
        return factors
    }

}

//class TestMath(): MathGuru(){
//    fun testGCD(){
//
//    }
//}

fun main() {
    val m = MathGuru()
    println(m.containsDollarSign("abhd$sa"))
    println(m.isPalindrome("jkhfdsj"))
    println(m.reverseNum(123))
    println(m.evenSum(100))
    println(m.gcd(150, 0))
    println(m.lcm(5, 8 ))
}
