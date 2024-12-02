import kotlin.math.abs

interface Functions{
    fun gcd(a: Int, b: Int): Int?
    fun lcm(a: Int, b: Int): Int?
    fun containsDollarSign(s: String): Boolean
    fun evenSum(n: Int=100): Int
    fun reverseNum(num: Int): Int
    fun isPalindrome(s: String): Boolean
}

open class MathGuru(): Functions{
    override fun gcd(a: Int, b: Int): Int {
        require(a != 0 || b != 0){
          "GCD of 0 and 0 is undefined."
        }
        return if (b == 0) abs(a) else gcd(b, a % b)
    }

    override fun lcm(a: Int, b: Int): Int {
        require(a != 0 || b != 0){
            "LCM of 0 and 0 is undefined."
        }
        return abs(a * b) / gcd(a, b)
    }

    override fun containsDollarSign(s: String): Boolean {
        return '$' in s
    }

    override fun evenSum(n: Int): Int {
        if (n <= 0) return 0
        if (n % 2 == 1) return 0 + evenSum(n-1)
        return n + evenSum(n-2)
    }

    override fun reverseNum(num: Int): Int {
        require(num >= 0){"Number must be a positive integer"}
        var result = 0
        var n = num
        while (n > 0){
            result = result * 10 + n % 10
            n /= 10
        }
        return result
    }

    override fun isPalindrome(s: String): Boolean {
        return s == s.reversed()
    }
}

class TestMath(): MathGuru(){
    fun testAll(){
        testGCD()
        testLCM()
        testEvenSum()
        testIsPalindrome()
        testReverseNum()
        testContainsDollarSign()
    }
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

    fun testIsPalindrome(){
        println("==============================Testing Palindrome========================================")
        val values = listOf("11111", "dabbbad", "fsfdf", "111222", "", " ")
        val answers = listOf(true, true, false, false, true, true)
        testCaseCheckSingleArg(values, answers, ::isPalindrome)
    }

    fun testContainsDollarSign(){
        println("==============================Testing Dollar Sign $========================================")
        val tmp = "fdsf"
        val values = listOf("\$kjflds", "lkfds\$", "fds\$fdsfs", "\$\$\$", "", "fdsfs", " ", "fds$tmp", "$tmp", "$")
        val answers = listOf(true, true, true, true, false, false, false, false, false, true)
        testCaseCheckSingleArg(values, answers, ::containsDollarSign)
    }

    fun testEvenSum(){
        println("==============================Testing Even Sum========================================")
        val values = listOf(0, 10, 250, 11, 7, -4545)
        val answers = listOf(0, 30, 15750, 30, 12, 0)
        testCaseCheckSingleArg(values, answers, ::evenSum)
    }

    fun testReverseNum(){
        println("==============================Testing Reverse Num========================================")
        val values = listOf(1234, 111, 0, -3, 54545423)
        val answers = listOf(4321, 111, 0, -1, 32454545)
        testCaseCheckSingleArg(values, answers, ::reverseNum)
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

    private fun <T, R> testCaseCheckSingleArg(values: List<T>, answers: List<R>, func: (T) -> R) {
        var allPassed = true
        for (i in values.indices) {
            val currentCase = values[i]
            val ans = answers[i]
            val retValue = func(currentCase)
            if (retValue != ans) {
                allPassed = false
                println("Function failed on testcase: ${func.toString().split(' ')[1]}($currentCase). expected: $ans, got: $retValue.")
            }
        }
        if (allPassed) println("Passed all tests.")
    }

}

fun main() {
    val m = MathGuru()
//    println(m.containsDollarSign("abhd$sa"))
    println(m.isPalindrome("21112"))
//    println(m.reverseNum(123))
//    println(m.evenSum(100))
//    println(m.gcd(-2, 0))
//    println(m.lcm(-5, 8 ))
//    val t = TestMath()
//    t.testLCM()
//    t.testGCD()
//    t.testContainsDollarSign()
//    t.testIsPalindrome()
//    t.testEvenSum()
//    t.testReverseNum()
//    t.testAll()
}
