package advent2018.day18

val input = listOf(
    """#.#.|..#|.||.#...|...#.|.......##|##..#..||.||....""",
    """#.......#.....|##..#|..#.##..##|.|.#..|.#...|....#""",
    """.|..........#.|..|.....|..|#.#...##|#|.|#|##|...#|""",
    """#|....#|##..#|.|||.#.|.#...#.##.......#||..#......""",
    """.#|....|.|..#..||...#||...|......###.#.#...##..#..""",
    """..||#...|.#.|||||.....|.......##...#.#....|..#....""",
    """###|..##...|.#|..|.#|#...|.#.|....||.|...#|.|#...#""",
    """|.#.|#.#.|###.|..#..|....#....#|.#..||.||.....#..#""",
    """.#..|.#..#|##..|.....|.#.|.#.#|.......#..|...#..|.""",
    """...|||##...|..#.#|#|..#.#..#|.|.|#..##........##.#""",
    """|#.#..||....|...|#..|....|#|...#.#.#.##|.|.#......""",
    """|..|...|.|#.........|..#|...|.#.|##....|..|..|#.||""",
    """|....#..||..|...#|......|||||.|#....||....|.#|.#..""",
    """......#||.#.#.||..|.......|....||#||#.|.#.|.##||#|""",
    """.##.#....##.#.|....|...###..#...|.#....|.#...|..##""",
    """.##..#|#.#.##.||..|.#|..|.|.##|....|...#||||.|..#.""",
    """|.|..|#|....|.#.#..||.....|.....##....||....|.||..""",
    """#|.|.##...||...........|..#.||.|.#..##..|#||......""",
    """||..||.||..#.##.#...|.#|.....|###....#.....#...|..""",
    """|.#.##|.|...||#.#...||....|.|#..|.#....##........#""",
    """..|....|.#.##|....|||#....#.|.|.##....|##|.|#.....""",
    """.|.....#|.#....|###|..#|||........||#.#..||..|..#.""",
    """#..#...|#|.#...|.||##..#.|..|.|.#.|..#.#.|.....#.#""",
    """|#||.##..#..|||.......#|..#...#..##....#||.#|.....""",
    """.|.#.....|..#...#...##...|.#...|.#|.......|..#...|""",
    """.||.......|..##..##.#|.|..|...#.|.#..#.|....#.|#.|""",
    """....#..#|##|....#..|.#..|||..#....#.#|...||.#..|..""",
    """#......#|.##..|..|....|.##.#|...#..#.|..|..|..|##.""",
    """...|||.#|#.#.|..|||.#.#.#...||...||..##..#.....|..""",
    """..#..|.|#.#..|..##|..#....#.|..|.......|||#.|.|.|#""",
    """.##|..#...#..||..|.........|#.|#.....|...##.|..||#""",
    """#.....||....#.....|#.||......|.#|.#|....||.||.#.#.""",
    """#..|#..|......|.#.#.#.##..||.|.#.|......#|#||#.|.#""",
    """..||..||.....|.#..###.#.|#..|.......|....#||.|..#.""",
    """.#...###|#|#|||...|...#.#|.#|..|...#..#.|#|.#...|.""",
    """...|..#||....##|..#...#....#||#.......|....#.|###.""",
    """..#....|#..#|.....|.#|..#..|#....||......|.|.#.|#.""",
    """..|##....#.|..#..#.|.#..||....#...|.....|..#.....|""",
    """.......#||..#||...|.#.|#...#....|.|.||.#.|...#.##|""",
    """.|.|||.....#............#..#..|..|..#.|.#..|......""",
    """|...|.|####..#.....#..#..|#|#..#..|......|...|.#|#""",
    """|.|...#..#..|....|.....#|#||..#.||..|#|..|||.|.|.|""",
    """..#...|#.......||.#.#.#....#.........##|.|..#.##||""",
    """#|#..|..|#|.##.|.|#......||......|.....#||###.|###""",
    """....#..#.|..|...#|#|..|#..|.#|....||#|.||.|#|.....""",
    """|..#.|..#.|#......#...#|.#|#|.....|#...###....##..""",
    """...##..#..|..#..#.#...|.#..#|...|#.##|.#|..##..#.#""",
    """|.|.|...#.|..##.|.|....|..#..|...|..##|...|..|....""",
    """.#|..|..|..|.|..#...|.||#...#......||.#.#.........""",
    """.#.##..|............|.||.....#||..|##.|..|.....#.|"""
)