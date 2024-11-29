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
            return if (a > 0) a else -a
        }else if (factorsOfA.isEmpty() && factorsOfB.isNotEmpty()){
            return if (b > 0) b else -b
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
        var result = if (a > 0) a else -a
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

class TestMath(): MathGuru(){
    fun testGCD(){
        println("===============================Testing GCD=============================")
        testGCDPositiveIntegers()
        testGCDNegativeIntegers()
        testGCDZeroValues()
    }

    private fun testGCDPositiveIntegers(){
        println("<======Testing On Positive Integers=======>")
        val answers = listOf(1, 12, 27, 11)
        val firstValues = listOf(2, 12, 54, 77)
        val secondValues = listOf(7, 48, 27, 33)
        testCaseCheckTwoArgs(firstValues, secondValues, answers, ::gcd)
    }

    private fun testGCDNegativeIntegers(){
        println("<======Testing On Negative Integers=======>")
        val answers = listOf(5, 9, 1, 8)
        val firstValues = listOf(-5, 45, -7, 32)
        val secondValues = listOf(5, -18, -46, -8)
        testCaseCheckTwoArgs(firstValues, secondValues, answers, ::gcd)
    }

    private fun testGCDZeroValues(){
        println("<======Testing On Negative Integers=======>")
        val answers = listOf(11, 2, 45, null)
        val firstValues = listOf(0, -2, 45, 0)
        val secondValues = listOf(11, -0, 0, 0)
        testCaseCheckTwoArgs(firstValues, secondValues, answers, ::gcd)
    }

    fun testLCM(){
        println("===============================Testing LCM=============================")
        testLCMPositiveIntegers()
        testLCMNegativeIntegers()
        testLCMZeroValues()
    }

    private fun testLCMNegativeIntegers(){
        println("<======Testing On Negative Integers=======>")
        val answers = listOf(5, 360, 46, 56)
        val firstValues = listOf(-5, -45, 23, -7)
        val secondValues = listOf(5, -8, -46, -8)
        testCaseCheckTwoArgs(firstValues, secondValues, answers, ::lcm)
    }

    private fun testLCMPositiveIntegers(){
        println("<======Testing On Positive Integers=======>")
        val answers = listOf(20, 24, 66, 143)
        val firstValues = listOf(4, 12, 22, 11)
        val secondValues = listOf(5, 8, 33, 13)
        testCaseCheckTwoArgs(firstValues, secondValues, answers, ::lcm)
    }

    private fun testLCMZeroValues(){
        println("<======Testing On Zeroes=======>")
        val answers = listOf(0, null, 0, 0)
        val firstValues = listOf(0, 0, -7, 0)
        val secondValues = listOf(5, 0, 0, -2)
        testCaseCheckTwoArgs(firstValues, secondValues, answers, ::lcm)
    }

    fun testContainsDollarSign(){
        println("==============================Testing Dollar Sign $========================================")
        val tmp = "fdsf"
        val values = listOf("\$kjflds", "lkfds\$", "fds\$fdsfs", "\$\$\$", "", "fdsfs", " ", "fds$tmp", "$tmp")
        val answers = listOf(true, true, true, true, false, false, false, false, false)
        for (i in values.indices){
            val currentCase = values[i]
            val ans = answers[i]
            val retValue = containsDollarSign(currentCase)
            if (retValue != answers[i]){
                println("Function failed on testcase: containsDollarSign($currentCase). expected: $ans, got: $retValue.")
            }
        }
    }

    private fun testCaseCheckTwoArgs(firstValues: List<Int>, secondValues: List<Int>, answers: List<Int?>,
                                     func: (Int, Int) -> Int?){
        var allPassed = true
        for (vals in (firstValues.zip(secondValues)).zip(answers)) {
            val a = vals.first.first
            val b = vals.first.second
            val ans = vals.second
            val retValue = func(a, b)
            if (retValue != ans) {
                println("Function failed on testcase: ${func.toString().split(' ')[1]}($a, $b). expected: $ans, got:$retValue.")
                allPassed = false
            }
        }
        if(allPassed) println("Passed all tests.")
    }

    private fun testCaseCheckSingleArg(values: List<Any>, answers: List<Any>, func: (Any) -> Any){

    }
}

fun main() {
//    val m = MathGuru()
//    println(m.containsDollarSign("abhd$sa"))
//    println(m.isPalindrome("jkhfdsj"))
//    println(m.reverseNum(123))
//    println(m.evenSum(100))
//    println(m.gcd(-2, 0))
//    println(m.lcm(-5, 8 ))
    val t = TestMath()
//    t.testLCM()
//    t.testGCD()
    t.testContainsDollarSign()
}
