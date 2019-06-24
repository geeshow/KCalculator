
fun main(args:Array<String>) {

    val example: Test = Test()
    val count: Int = 90000015
    example.example2(count)
    example.example1(count)
    example.example3(count)
    example.example4(count)
}

class Test() {

    fun example1(count:Int) {

        System.gc()
        var startTime: Long = System.currentTimeMillis()
        var sb: StringBuilder = StringBuilder()
        for (i in 1..count) {
            if (i % 100 == 0) {
                sb = StringBuilder()
            }
            sb.append(i)
        }
        var endTime: Long = System.currentTimeMillis()
        println("example1 : " + (endTime - startTime) + " Memory :" + Runtime.getRuntime().totalMemory())
    }

    fun example2(count:Int) {
        System.gc()
        var startTime: Long = System.currentTimeMillis()
        var sb: StringBuilder = StringBuilder()
        for (i in 1..count) {
            if (i % 100 == 0) {
                sb.delete(0, sb.length)
            }
            sb.append(i)
        }
        var endTime: Long = System.currentTimeMillis()
        println("example2 : " + (endTime - startTime) + " Memory :" + Runtime.getRuntime().totalMemory())
    }

    fun example3(count:Int) {
        System.gc()
        var startTime: Long = System.currentTimeMillis()
        var sb: StringBuilder = StringBuilder()
        for (i in 1..count) {
            if (i % 100 == 0) {
                sb.setLength(0)
            }
            sb.append(i)
        }
        var endTime: Long = System.currentTimeMillis()
        println("example3 : " + (endTime - startTime) + " Memory :" + Runtime.getRuntime().totalMemory())
    }


    fun example4(count:Int) {
        System.gc()
        var startTime: Long = System.currentTimeMillis()
        var sb: StringBuilder = StringBuilder()
        for (i in 1..count) {
            if (i % 100 == 0) {
                sb.clear()
            }
            sb.append(i)
        }

        var endTime: Long = System.currentTimeMillis()
        println("example4 : " + (endTime - startTime) + " Memory :" + Runtime.getRuntime().totalMemory())
    }

}