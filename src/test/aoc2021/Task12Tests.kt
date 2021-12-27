import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Task12Tests {
    private val testCaveEdges1 = """
        start-A
        start-b
        A-c
        A-b
        b-d
        A-end
        b-end
    """.trimIndent()

    private val testCaveEdges2 = """
        dc-end
        HN-start
        start-kj
        dc-start
        dc-HN
        LN-dc
        HN-end
        kj-sa
        kj-HN
        kj-dc
    """.trimIndent()

    private val testCaveEdges3 = """
        fs-end
        he-DX
        fs-he
        start-DX
        pj-DX
        end-zg
        zg-sl
        zg-pj
        pj-he
        RW-he
        fs-DX
        pj-RW
        zg-RW
        start-pj
        he-WI
        zg-he
        pj-fs
        start-RW
    """.trimIndent()

    @Test
    fun calcNumberOfPathCave1() {
        val numberOfPaths = calcNumberOfPaths(testCaveEdges1.split("\n"))
        assertEquals(10, numberOfPaths)
    }

    @Test
    fun calcNumberOfPathCave2() {
        val numberOfPaths = calcNumberOfPaths(testCaveEdges2.split("\n"))
        assertEquals(19, numberOfPaths)
    }

    @Test
    fun calcNumberOfPathCave3() {
        val numberOfPaths = calcNumberOfPaths(testCaveEdges3.split("\n"))
        assertEquals(226, numberOfPaths)
    }

    @Test
    fun calcNumberOfPathSingleRevisitCave1() {
        val numberOfPaths = calcNumberOfPaths(testCaveEdges1.split("\n"), 1)
        assertEquals(36, numberOfPaths)
    }

    @Test
    fun calcNumberOfPathSingleRevisitCave2() {
        val numberOfPaths = calcNumberOfPaths(testCaveEdges2.split("\n"), 1)
        assertEquals(103, numberOfPaths)
    }

    @Test
    fun calcNumberOfPathSingleRevisitCave3() {
        val numberOfPaths = calcNumberOfPaths(testCaveEdges3.split("\n"), 1)
        assertEquals(3509, numberOfPaths)
    }
}