package com.example.kotlinpractice
/*
1. დაწყება
2. შეიყვანეთ (მინიჭება) X (String)ცვლადის მნიშვნელობა (დამატება).
3. შეიყვანეთ (მინიჭება) ცვლადის მნიშვნელობა (დამატება) Y (String)
4. X და Y ცვლადებიდან ამოიღეთ მხოლოდ რიხვითი მნიშვნელობები მაგალითად თუ შემოვიდა X "2nnk4b67"
უნდა აიღოთ ->"2467" ანალოგიურად Y "0nnvkb78f9f" -> "789" უნდა გამოთვალეთ Z (Z = X / Y). X ან Y
თუ არ შეიცავს რიცხვით მნიშვნელობას აიღეთ მნიშვნელობა 0
5. აჩვენეთ გაყოფის შედეგი, შეტყობინებით "X და Y განაყოფი არის:"
6. დასვით შეკითხვა "გსურთ პროგრამის ხელახლა დაწყება <Y/N>?" და მიანიჭეთ მიღებული შედეგი ცვლადს ANSWER
7. თუ პასუხი არის „დიახ“, გადადით საფეხურზე 1
8. თუ პასუხი არის „არა“, გადადით მე-9 საფეხურზე
9. დასასრული
*/
fun main() {
    while (true) {
        println("Enter first string: ")
        val s1: String = readln()
        println("Enter second string: ")
        val s2: String = readln()
        val x: Int = getNum(s1)
        val y: Int = getNum(s2)

        try {
            val z: Int = x / y
            println("$x divided by $y is $z")
        } catch (e: ArithmeticException) {
            println("Division by zero not allowed.")
        }

        println("Do you want to continue? <y for yes | n for no>")
        if (!getCommand()) break
    }
}

fun getCommand(): Boolean{
    while (true){
        val command: String? = readlnOrNull()
        if (command == "y") {
            break
        } else if (command == "n") {
            println("Have a nice day.")
            return false
        } else {
            println("You should enter y or n.")
        }
    }

    return true
}

fun getNum(s: String): Int{
    if(s == "") return 0;
    var res: Int = 0
    for(ch in s){
        if(ch in '0'..'9'){
            val num: Int = ch - '0'
            res = res * 10 + num
        }
    }
    return res
}